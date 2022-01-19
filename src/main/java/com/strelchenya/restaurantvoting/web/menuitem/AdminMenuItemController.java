package com.strelchenya.restaurantvoting.web.menuitem;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.MenuItem;
import com.strelchenya.restaurantvoting.repository.MenuItemRepository;
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

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;
import static com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController.ADMIN_RESTAURANT_REST_URL;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ADMIN_RESTAURANT_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin MenuItem Controller", description = "Controller for editing menu items by admin.")
public class AdminMenuItemController {
    public static final String MENU_ITEMS_REST_URL = "/{restaurantId}/menu-items";

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Operation(summary = "Creating a menuItem", description = "Creating a menuItem for a restaurant.")
    @PostMapping(value = MENU_ITEMS_REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItem menuItem, @PathVariable int restaurantId) {
        Assert.notNull(menuItem, "menuItem must not be null!");
        log.info("create menuItem {} for restaurant {}", menuItem, restaurantId);
        checkNew(menuItem);

        menuItem.setRestaurant(restaurantRepository.findById(restaurantId).orElse(null));
        MenuItem created = menuItemRepository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(ADMIN_RESTAURANT_REST_URL + MENU_ITEMS_REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "MenuItem update", description = "Renovation of the restaurant menu items.")
    @PutMapping(value = MENU_ITEMS_REST_URL + "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int id, @PathVariable int restaurantId) {
        Assert.notNull(menuItem, "menuItem must not be null!");
        log.info("update menuItem {}, menuItem id {} for restaurant {}", menuItem, id, restaurantId);
        assureIdConsistent(menuItem, id);
        menuItem.setRestaurant(restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("not found restaurant " + restaurantId)));
        checkNotFoundWithId(menuItemRepository.save(menuItem), id);
    }

    @Operation(summary = "Deleting a menu item", description = "Deleting a menu item in a specific restaurant.")
    @DeleteMapping(value = MENU_ITEMS_REST_URL + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete menu item {} for restaurant {}", id, restaurantId);
        checkNotFoundWithId(menuItemRepository.delete(id, restaurantId) != 0, id);
    }
}
