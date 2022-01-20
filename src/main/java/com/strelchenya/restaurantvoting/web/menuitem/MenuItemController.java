package com.strelchenya.restaurantvoting.web.menuitem;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Cacheable
    @Operation(summary = "Get today's menu", description = "Get today's restaurant menu.")
    @GetMapping(value = MENU_ITEMS_REST_URL)
    public List<MenuItem> getTodayMenu(@PathVariable int restaurantId) {
        log.info("get menu items for restaurant {} on {}", restaurantId, LocalDate.now());
        return menuItemRepository.getMenuByDate(restaurantId, LocalDate.now());
    }

    @Operation(summary = "Get all of today's menu", description = "Get all menus for today for each restaurant.")
    @GetMapping(value = "/menu-items")
    public List<MenuItemTo> getAllTodayMenu() {
        log.info("get a today's {} menu for each restaurant", LocalDate.now());
        return menuItemRepository.getAllMenusByLocalDate(LocalDate.now());
    }
}
