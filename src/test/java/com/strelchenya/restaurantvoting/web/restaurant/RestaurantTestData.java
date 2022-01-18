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

    public static final int NOT_FOUND_RESTAURANT = 0;
    public static final int RESTAURANT_ID_1 = 1;
    public static final int RESTAURANT_ID_2 = 2;
    public static final int RESTAURANT_ID_3 = 3;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID_1, "Just restaurant");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID_2, "Burger Empire");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID_3, "Eatery");

    public static final RestaurantTo restaurantTo1 = new RestaurantTo(RESTAURANT_ID_1, "Just restaurant",1L);
    public static final RestaurantTo restaurantTo2 = new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 2L);

    public static final RestaurantTo restaurantToDay1 = new RestaurantTo(RESTAURANT_ID_1, "Just restaurant",1L);
    public static final RestaurantTo restaurantToDay2 = new RestaurantTo(RESTAURANT_ID_2, "Burger Empire", 1L);
    public static final List<RestaurantTo> restaurantTos = List.of(restaurantToDay2, restaurantToDay1);

    public static Restaurant getNew(){
        return new Restaurant(null, "New Restaurant");
    }
}
