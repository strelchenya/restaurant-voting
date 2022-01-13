package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.service.DishService;
import com.strelchenya.restaurantvoting.to.DishTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {
    private final DishService dishService;

    @GetMapping(value = DISHES_REST_URL + "/{id}")
    public Dish getById(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return dishService.getById(id, restaurantId);
    }

    @GetMapping(value = DISHES_REST_URL + "/by")
    public List<Dish> getMenuByDate(@PathVariable int restaurantId,
                                    @NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get dishes for restaurant {} on {}", restaurantId, localDate);
        return dishService.getMenuByDate(restaurantId, localDate);
    }

    @GetMapping(value = "/dishes/menus-by")
    public List<DishTo> getAllMenusByLocalDate(@NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get a menu for each restaurant by date {}", localDate);
        return dishService.getAllMenusByLocalDate(localDate);
    }

    @GetMapping(value = DISHES_REST_URL)
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }
}
