package com.strelchenya.restaurantvoting.web.menuitem;

import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import com.strelchenya.restaurantvoting.web.restaurant.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.menuitem.MenuItemTestData.*;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuItemControllerTest extends AbstractControllerTest {
    private static final String RESTAURANTS_REST_URL = RestaurantController.RESTAURANTS_REST_URL + '/';
    private static final String MENU_ITEMS_REST_URL = "/menu-items/";

    @Test
    void getMenuForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_3 + MENU_ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_MATCHER.contentJson(todayMenu));
    }

    @Test
    void getMenuForTodayWithoutDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + MENU_ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMenusForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + MENU_ITEMS_REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(allMenusForToday));
    }
}