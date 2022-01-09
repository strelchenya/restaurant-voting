package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.model.User;
import com.strelchenya.restaurantvoting.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.checkNew;
import static com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController.ADMIN_RESTAURANT_REST_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    public static final String DISHES_REST_URL = "/{restaurantId}/dishes";

    private final DishService dishService;

    @GetMapping(value = DISHES_REST_URL + "/{id}")
    public ResponseEntity<Dish> getById(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish {} for restaurant {}", id, restaurantId);
        return ResponseEntity.ok(dishService.getById(id, restaurantId));
    }

    @GetMapping(value = DISHES_REST_URL + "/menu-by")
    public List<Dish> getMenuByDate(@PathVariable int restaurantId,
                                    @NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get dishes for restaurant {} on {}", restaurantId, localDate);
        return dishService.getMenuByDate(restaurantId, localDate);
    }

    @GetMapping(value = DISHES_REST_URL)
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant {}", restaurantId);
        return dishService.getAll(restaurantId);
    }

    @PostMapping(value = DISHES_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);

        Dish created = dishService.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANT_REST_URL + DISHES_REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = DISHES_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update dish {}, dish id {} for restaurant {}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        dishService.update(dish, id, restaurantId);
    }

    @DeleteMapping(value = DISHES_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish {} for restaurant {}", id, restaurantId);
        dishService.delete(id, restaurantId);
    }
}
