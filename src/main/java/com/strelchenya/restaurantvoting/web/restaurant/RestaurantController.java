package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.service.RestaurantService;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = RestaurantController.RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String RESTAURANTS_REST_URL = "/api/v1/restaurants";

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTo getByIdAndLocalDate(@PathVariable int id,
                                            @NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get restaurant by id {}, date {}", id, localDate);
        return restaurantService.getByIdAndLocalDate(id, localDate);
    }

    @GetMapping("/by")
    public List<RestaurantTo> getAllByDate(@NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get all restaurantTos by date {}", localDate);
        return restaurantService.getAllByDate(localDate);
    }
}
