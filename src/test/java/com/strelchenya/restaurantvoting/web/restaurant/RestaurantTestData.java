package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu", "votes");

    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_ID_1, "Just restaurant");
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_ID_2, "Burger Empire");
    public static final Restaurant restaurant_3 = new Restaurant(RESTAURANT_ID_3, "Eatery");

    public static final RestaurantTo restaurantTo_1 = new RestaurantTo(RESTAURANT_ID_1, "Just restaurant",1L);
    public static final RestaurantTo restaurantTo_2 = new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 2L);

    public static final RestaurantTo restaurantToDay_1 = new RestaurantTo(RESTAURANT_ID_1, "Just restaurant",1L);
    public static final RestaurantTo restaurantToDay_2 = new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 1L);
    public static final List<RestaurantTo> restaurantTos = List.of(restaurantToDay_2, restaurantToDay_1);

    public static Restaurant getNew(){
        return new Restaurant(null, "New Restaurant");
    }
}
