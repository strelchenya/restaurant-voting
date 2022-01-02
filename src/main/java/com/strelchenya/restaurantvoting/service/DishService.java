package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.repository.DishRepository;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish get(int dishId, int restaurantId) {
        return checkNotFoundWithId(dishRepository.findById(dishId)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null), dishId);
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public void delete(int dishId, int restaurantId) {
        checkNotFoundWithId(dishRepository.delete(dishId, restaurantId) != 0, dishId);
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null!");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null!");
        dish.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        return checkNotFoundWithId(dishRepository.save(dish), dish.id());
    }

    public List<Dish> getByDate(int restaurantId, LocalDate date) {
        return dishRepository.getByDate(restaurantId, date);
    }
}
