package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.checkNew;
import static com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController.ADMIN_RESTAURANT_REST_URL;

@Slf4j
@RestController
@RequestMapping(value = ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
//@CacheConfig(cacheNames = "dishes")
public class AdminDishController {
    public static final String DISHES_REST_URL = "/{restaurantId}/dishes";

    private final DishService dishService;

    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = DISHES_REST_URL + "/{dishId}")
    public Dish get(@PathVariable int dishId, @PathVariable int restaurantId) {
        log.info("get {}", dishId);
        return dishService.get(dishId, restaurantId);
    }

//    @Cacheable
    @GetMapping(value = DISHES_REST_URL)
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }

//    @CacheEvict(allEntries = true)
    @PostMapping(value = DISHES_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @RequestBody Dish dish) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);

        Dish created = dishService.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANT_REST_URL + DISHES_REST_URL + "/{dishId}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = DISHES_REST_URL + "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Dish dish, @PathVariable int dishId, @PathVariable int restaurantId) {
        log.info("update dish {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, dishId);
        dishService.update(dish, restaurantId);
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping(value = DISHES_REST_URL + "/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("delete dish {} for restaurant {}", dishId, restaurantId);
        dishService.delete(dishId, restaurantId);
    }
}
