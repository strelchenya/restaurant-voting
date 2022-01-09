package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;

@RequiredArgsConstructor
@Service("restaurantService")
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "restaurants")
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Cacheable
    public RestaurantTo getById(int id) {
        return restaurantRepository.get(id)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id));
    }

    @Cacheable
    public List<RestaurantTo> getAllByDate(LocalDate localDate) {
        return restaurantRepository.getAllByDate(localDate);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }
}
