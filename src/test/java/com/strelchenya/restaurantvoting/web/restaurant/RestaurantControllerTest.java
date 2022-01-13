package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.to.RestaurantTo;
import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.*;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String RESTAURANTS_REST_URL = RestaurantController.RESTAURANTS_REST_URL + '/';

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_2)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 2L)));
    }

    @Test
    void getNotFoundById() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + NOT_FOUND_RESTAURANT)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByIdAndLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_2 + "/by")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 1L)));
    }

    @Test
    void getNotFoundByIdAndLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + RESTAURANT_ID_2 + "/by")
                .param("local-date", "2000-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getAllByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTos));
    }

    @Test
    void getAllByInvalidDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("local-date", "2021")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllByNotFoundDate() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANTS_REST_URL + "/by")
                .param("local-date", "1999-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}