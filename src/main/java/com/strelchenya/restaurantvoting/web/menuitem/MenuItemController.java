package com.strelchenya.restaurantvoting.web.menuitem;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.MenuItem;
import com.strelchenya.restaurantvoting.repository.MenuItemRepository;
import com.strelchenya.restaurantvoting.to.MenuItemTo;
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

import static com.strelchenya.restaurantvoting.web.menuitem.AdminMenuItemController.MENU_ITEMS_REST_URL;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantController.RESTAURANTS_REST_URL;

@Slf4j
@RequiredArgsConstructor
@RestController
@CacheConfig(cacheNames = "menu")
@RequestMapping(value = RESTAURANTS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User MenuItem Controller", description = "Controller for getting the menu and menu items of the restaurant.")
public class MenuItemController {
    private final MenuItemRepository menuItemRepository;

    @Operation(summary = "Get a menu item", description = "Get a restaurant menu item.")
    @GetMapping(value = MENU_ITEMS_REST_URL + "/{id}")
    public MenuItem getById(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get menu item {} for restaurant {}", id, restaurantId);
        return menuItemRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("not found menu item " + id));
    }

    @Cacheable
    @Operation(summary = "Get menu", description = "Get a restaurant menu on a given day.")
    @GetMapping(value = MENU_ITEMS_REST_URL + "/by")
    public List<MenuItem> getMenuByDate(@PathVariable int restaurantId,
                                        @NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get menu items for restaurant {} on {}", restaurantId, localDate);
        return menuItemRepository.getMenuByDate(restaurantId, localDate);
    }

    @Operation(summary = "Get all menu", description = "Get all menus for all restaurants on a given day.")
    @GetMapping(value = "/menu-items/menus-by")
    public List<MenuItemTo> getAllMenusByLocalDate(@NotNull @RequestParam(value = "local-date") LocalDate localDate) {
        log.info("get a menu for each restaurant by date {}", localDate);
        return menuItemRepository.getAllMenusByLocalDate(localDate);
    }

    @Operation(summary = "Get all restaurant menu", description = "Get all restaurant menus for all time.")
    @GetMapping(value = MENU_ITEMS_REST_URL)
    public List<MenuItem> getAll(@PathVariable int restaurantId) {
        log.info("get all menu items for restaurant {}", restaurantId);
        return menuItemRepository.getAll(restaurantId);
    }
}
