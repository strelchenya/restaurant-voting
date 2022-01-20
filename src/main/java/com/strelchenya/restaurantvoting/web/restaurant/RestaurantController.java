package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Restaurant Controller", description = "Getting a restaurant by a user with menu votes.")
public class RestaurantController {
    public static final String RESTAURANTS_REST_URL = "/api/v1/restaurants-votes";

    private final RestaurantRepository restaurantRepository;

    @Operation(summary = "Get a restaurant", description = "Get a restaurant with all the menu votes of all time.")
    @GetMapping("/{id}")
    public RestaurantTo getById(@PathVariable int id) {
        log.info("get restaurant by id {}", id);
        return restaurantRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id));
    }

    @Operation(
            summary = "Get a restaurant with votes",
            description = "Get a restaurant with the number of votes for a menu for a given day."
    )
    @GetMapping("/{id}" + "/by")
    public RestaurantTo getByIdAndLocalDate(@PathVariable int id,
                                            @NotNull @RequestParam(value = "date") LocalDate localDate) {
        log.info("get restaurant by id {}, date {}", id, localDate);
        return restaurantRepository.getByIdAndLocalDate(id, localDate)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id + " date: " + localDate));
    }

    @Operation(
            summary = "Get all restaurants",
            description = "Get all restaurants with the number of votes on a given day."
    )
    @GetMapping("/by")
    public List<RestaurantTo> getAllByDate(@NotNull @RequestParam(value = "date") LocalDate localDate) {
        log.info("get all restaurantTos by date {}", localDate);
        return restaurantRepository.getAllByDate(localDate);
    }
}
