package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.util.JsonUtil;
import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import com.strelchenya.restaurantvoting.web.restaurant.AdminRestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.dish.DishTestData.*;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.ADMIN_ID;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.admin;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String ADMIN_RESTAURANT_REST_URL = AdminRestaurantController.ADMIN_RESTAURANT_REST_URL + '/';
    private static final String DISHES_REST_URL = "/dishes/";

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + DISH_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish_1));
    }

    @Test
    void getMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + "/by")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(menu));
    }

    @Test
    void getAllForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dishesRestaurant));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + DISH_ID_1)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThrows(NotFoundException.class, () -> dishService.getById(DISH_ID_1, ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(
                MockMvcRequestBuilders.post(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(userHttpBasic(admin))
                        .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated())
                .andDo(print());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishService.getById(newId, RESTAURANT_ID_1), newDish);
    }

    @Test
    void update() throws Exception {
        Dish updatedDish = dishService.getById(DISH_ID_1, RESTAURANT_ID_1);
        updatedDish.setPrice(999);
        perform(MockMvcRequestBuilders.put(ADMIN_RESTAURANT_REST_URL + RESTAURANT_ID_1 + DISHES_REST_URL + DISH_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDish))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}