package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.repository.DishRepository;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.to.DishTo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@RequiredArgsConstructor
@Service("dishService")
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "menu")
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public Dish getById(int id, int restaurantId) {
        return dishRepository.getByIdAndRestaurant_Id(id, restaurantId)
                .orElseThrow(() -> new NotFoundException("not found dish " + id + " by restaurant " + restaurantId));
    }

    @Cacheable
    public List<Dish> getMenuByDate(int restaurantId, LocalDate localDate) {
        return dishRepository.getMenuByDate(restaurantId, localDate);
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(dishRepository.delete(id, restaurantId) != 0, id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null!");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public Dish update(Dish dish, int id, int restaurantId) {
        Assert.notNull(dish, "dish must not be null!");
        dish.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("not found restaurant " + restaurantId)));
        return checkNotFoundWithId(dishRepository.save(dish), id);
    }

    public List<DishTo> getAllMenusByLocalDate(LocalDate localDate) {
        return dishRepository.getAllMenusByLocalDate(localDate);
    }
}
