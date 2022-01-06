package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date_1;
import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date_2;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID_1 = 1;

    public static final Dish dish_1 = new Dish(DISH_ID_1, "Just Lunch", 211, date_1);
    public static final Dish dish_2 = new Dish(2, "Steak frites", 244, date_1);
    public static final Dish dish_3 = new Dish(3, "Chicken confit", 135, date_1);
    public static final Dish dish_4 = new Dish(4, "French onion soup", 1251, date_1);
    public static final Dish dish_5 = new Dish(5, "Bouillabaisse", 742, date_1);
    public static final Dish dish_6 = new Dish(6, "Salmon en papillote", 3425, date_1);
    public static final Dish dish_7 = new Dish(7, "Quiche Lorraine", 1125, date_1);
    public static final Dish dish_8 = new Dish(8, "Croque monsieur", 980, date_1);
    public static final Dish dish_9 = new Dish(9, "Boeuf bourguignon", 4423, date_1);
    public static final Dish dish_10 = new Dish(10, "Cassoulet", 332, date_2);
    public static final Dish dish_11 = new Dish(11, "Coca-Cola", 75, date_2);
    public static final Dish dish_12 = new Dish(12, "Lamb shank navarin", 121, date_2);
    public static final Dish dish_13 = new Dish(13, "Hazelnut dacquoise", 99, date_2);
    public static final Dish dish_14 = new Dish(14, "Frangipane tart", 1292, date_2);
    public static final Dish dish_15 = new Dish(15, "Souffle", 519, date_2);
    public static final Dish dish_16 = new Dish(16, "Paris-brest", 1200, date_2);
    public static final Dish dish_17 = new Dish(17, "Cheesy tuna pasta", 119, date_2);
    public static final Dish dish_18 = new Dish(18, "Tarte tatin", 113, date_2);

    public static final List<Dish> menu = List.of(dish_3, dish_1, dish_2);
    public static final List<Dish> dishesRestaurant = List.of(dish_10, dish_11, dish_12, dish_1,
            dish_2, dish_3);

    protected static Dish getNew(){
        return new Dish(null, "newDish", 9999, LocalDate.now());
    }
}
