package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.repository.restaurant.RestaurantRepositoryImpl;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserRestaurantController.RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController {
    public static final String RESTAURANTS_REST_URL = "/api/restaurants";

    private final RestaurantRepositoryImpl restaurantRepository;

    public UserRestaurantController(RestaurantRepositoryImpl restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable
    @GetMapping
    public List<RestaurantTo> getAllNow() {
        log.info("get all restaurantTos today");
        return restaurantRepository.getAllByDishesDate(LocalDate.now());
    }

    @Cacheable
    @GetMapping("/by")
    public List<RestaurantTo> getByDate(@RequestParam LocalDate date) {
        log.info("get all restaurantTos by date {}", date);
        return restaurantRepository.getAllByDishesDate(date);
    }
}