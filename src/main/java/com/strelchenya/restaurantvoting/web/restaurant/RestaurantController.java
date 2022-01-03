package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.service.RestaurantService;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestaurantController.RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {
    public static final String RESTAURANTS_REST_URL = "/api/profile/restaurants";

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTo getById(@PathVariable int id) {
        log.info("get restaurant by id {}", id);
        return restaurantService.getById(id);
    }

    @Cacheable
    @GetMapping("/by")
    public List<RestaurantTo> getAllByDate(@RequestParam LocalDate localDate) {
        log.info("get all restaurantTos by date {}", localDate);
        return restaurantService.getAllByDate(localDate);
    }
}
