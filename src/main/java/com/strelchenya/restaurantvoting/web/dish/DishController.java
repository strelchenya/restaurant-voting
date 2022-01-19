package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.repository.DishRepository;
import com.strelchenya.restaurantvoting.to.DishTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.dish.AdminDishController.DISHES_REST_URL;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantController.RESTAURANTS_REST_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@CacheConfig(cacheNames = "menu")
@RequestMapping(value = RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Dish Controller", description = "Controller for getting the menu and dishes of the restaurant.")
public class DishController {
    private final DishRepository dishRepository;

    @Operation(summary = "Get a dish", description = "Get a restaurant dish.")
    @GetMapping(value = DISHES_REST_URL + "/{id}")
    public Dish getById(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return dishRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("not found dish " + id));
    }

    @Cacheable
    @Operation(summary = "Get menu", description = "Get a restaurant menu on a given day.")
    @GetMapping(value = DISHES_REST_URL + "/by")
    public List<Dish> getMenuByDate(@PathVariable int restaurantId,
                                    @NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get dishes for restaurant {} on {}", restaurantId, localDate);
        return dishRepository.getMenuByDate(restaurantId, localDate);
    }

    @Operation(summary = "Get all menu", description = "Get all menus for all restaurants on a given day.")
    @GetMapping(value = "/dishes/menus-by")
    public List<DishTo> getAllMenusByLocalDate(@NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get a menu for each restaurant by date {}", localDate);
        return dishRepository.getAllMenusByLocalDate(localDate);
    }

    @Operation(summary = "Get all restaurant menu", description = "Get all restaurant menus for all time.")
    @GetMapping(value = DISHES_REST_URL)
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return dishRepository.getAll(restaurantId);
    }
}
