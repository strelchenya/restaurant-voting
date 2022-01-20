package com.strelchenya.restaurantvoting.web.menuitem;

import com.strelchenya.restaurantvoting.model.MenuItem;
import com.strelchenya.restaurantvoting.util.JsonUtil;
import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.menuitem.MenuItemTestData.*;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.NOT_FOUND_RESTAURANT;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.admin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuItemControllerTest extends AbstractControllerTest {

    private static final String ADMIN_RESTAURANT_REST_URL = AdminRestaurantController.ADMIN_RESTAURANT_REST_URL + '/';
    private static final String MENU_ITEMS_REST_URL = "/menu-items/";

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + MENU_ITEM_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(MENU_ITEM_1));
    }

    @Test
    void getByInvalidId() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + NOT_FOUND_MENU_ITEM)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(menuItemsRestaurant));
    }

    @Test
    void getAllForNotFoundRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + NOT_FOUND_RESTAURANT + MENU_ITEMS_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(menu));
    }

    @Test
    void getMenuByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + "/by")
                .param("date", "")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getMenuByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + "/by")
                .param("date", "2000-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMenusByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + MENU_ITEMS_REST_URL + "/by")
                .param("date", "2021-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(allMenusForDay));
    }

    @Test
    void getAllMenusByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + MENU_ITEMS_REST_URL + "/by")
                .param("date", "")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllMenusByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + MENU_ITEMS_REST_URL + "/by")
                .param("date", "2000-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMenusBySpaceDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + MENU_ITEMS_REST_URL + "/by")
                .param("date", " ")
                .with(userHttpBasic(admin)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + MENU_ITEM_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void deleteNotFoundMenuItem() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + NOT_FOUND_MENU_ITEM)
                .with(userHttpBasic(admin)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void create() throws Exception {
        MenuItem newMenuItem = getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newMenuItem)))
                .andExpect(status().isCreated())
                .andDo(print());

        MenuItem created = MENU_ITEM_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenuItem.setId(newId);
        MENU_ITEM_MATCHER.assertMatch(created, newMenuItem);
        MENU_ITEM_MATCHER.assertMatch(menuItemRepository.getById(newId).get(), newMenuItem);
    }

    @Test
    void createNotFoundRestaurant() throws Exception {
        MenuItem newMenuItem = getNew();
        perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + NOT_FOUND_RESTAURANT + MENU_ITEMS_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newMenuItem)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void createNewMenuItemWithId() throws Exception {
        MenuItem newMenuItem = getNew();
        newMenuItem.setId(MENU_ITEM_ID_1);
        perform(MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newMenuItem)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void update() throws Exception {
        MenuItem updatedMenuItem = menuItemRepository.getById(MENU_ITEM_ID_1).get();
        updatedMenuItem.setPrice(999);
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + MENU_ITEM_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMenuItem))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateNotFound() throws Exception {
        MenuItem updatedMenuItem = menuItemRepository.getById(MENU_ITEM_ID_1).get();
        updatedMenuItem.setPrice(999);
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL + NOT_FOUND_MENU_ITEM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMenuItem))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}