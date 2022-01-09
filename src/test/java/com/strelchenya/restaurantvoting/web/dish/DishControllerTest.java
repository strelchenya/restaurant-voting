package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import com.strelchenya.restaurantvoting.web.restaurant.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.dish.DishTestData.*;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    private static final String RESTAURANTS_REST_URL = RestaurantController.RESTAURANTS_REST_URL + '/';
    private static final String DISHES_REST_URL = "/dishes/";

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + DISH_ID_1)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish_1));
    }

    @Test
    void getByInvalidId() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + NOT_FOUND_DISH)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + "/by")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(menu));
    }

    @Test
    void getMenuByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + "/by")
                .param("local-date", "")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getMenuByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + "/by")
                .param("local-date", "2000-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMenusByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + DISHES_REST_URL + "/menus-by")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TO_MATCHER.contentJson(allMenusForDay));
    }

    @Test
    void getAllMenusByEmptyDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + DISHES_REST_URL + "/menus-by")
                .param("local-date", "")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllMenusByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + DISHES_REST_URL + "/menus-by")
                .param("local-date", "2000-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllMenusBySpaceDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + DISHES_REST_URL + "/menus-by")
                .param("local-date", " ")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}