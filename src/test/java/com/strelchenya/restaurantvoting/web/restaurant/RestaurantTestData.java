package com.strelchenya.restaurantvoting.web.restaurant;

import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

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
}
