package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.dish.AdminDishController.DISHES_REST_URL;
import static com.strelchenya.restaurantvoting.web.restaurant.UserRestaurantController.RESTAURANTS_REST_URL;

@Slf4j
@RestController
@RequestMapping(value = RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserDishController {
    private final DishService dishRepository;

    public UserDishController(DishService dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping(value = DISHES_REST_URL + "/{dishId}")
    public Dish get(@PathVariable int dishId, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", dishId, restaurantId);
        return dishRepository.get(dishId, restaurantId);
    }

//    @Cacheable(cacheNames = "dishes")
    @GetMapping(value = DISHES_REST_URL + "/by")
    public List<Dish> getAllByDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("get dishes for restaurant {} on {}", restaurantId, date);
        return dishRepository.getByDate(restaurantId, date);
    }
}
