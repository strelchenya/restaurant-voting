package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;

@Slf4j
@RestController
@RequestMapping(value = AdminRestaurantController.ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class AdminRestaurantController {
    public static final String ADMIN_RESTAURANT_REST_URL = "/api/admin/restaurants";

    private final RestaurantService restaurantRepository;

    public AdminRestaurantController(RestaurantService restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return restaurantRepository.get(id);
    }

    @CacheEvict(allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Restaurant created = restaurantRepository.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANT_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @CacheEvict(allEntries = true)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        restaurantRepository.update(restaurant);
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantRepository.delete(id);
    }
}
