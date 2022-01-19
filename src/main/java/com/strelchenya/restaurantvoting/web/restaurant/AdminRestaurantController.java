package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;
import static com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController.ADMIN_RESTAURANT_REST_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Restaurant Controller", description = "Controller for restaurant management by administrator.")
public class AdminRestaurantController {
    public static final String ADMIN_RESTAURANT_REST_URL = "/api/v1/admin/restaurants";

    private final RestaurantRepository restaurantRepository;

    @Operation(summary = "Get a restaurant", description = "Get a restaurant with all the menu votes of all time.")
    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable int id) {
        log.info("get restaurant by id {}", id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id));
    }

    @Operation(summary = "Get all restaurants", description = "Get all restaurants sorted by name.")
    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAllByOrderByTitleAsc();
    }

    @Operation(summary = "Creation of a restaurant", description = "Creation of a new restaurant by the administrator.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        checkNew(restaurant);
        log.info("create {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANT_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Restaurant update", description = "Restaurant update by administrator.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        assureIdConsistent(restaurant, id);
        log.info("update {} with id {}", restaurant, id);
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @Operation(summary = "Deleting a restaurant", description = "Removal by the restaurant administrator.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantRepository.deleteExisted(id);
    }
}
