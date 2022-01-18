package com.strelchenya.restaurantvoting.web.dish;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.to.DishTo;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date1;
import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date2;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(DishTo.class);

    public static final int NOT_FOUND_DISH = 0;
    public static final int DISH_ID_1 = 1;

    public static final Dish dish1 = new Dish(DISH_ID_1, "Just Lunch", 211, date1);
    public static final Dish dish2 = new Dish(2, "Steak frites", 244, date1);
    public static final Dish dish3 = new Dish(3, "Chicken confit", 135, date1);
    public static final Dish dish4 = new Dish(4, "French onion soup", 1251, date1);
    public static final Dish dish5 = new Dish(5, "Bouillabaisse", 742, date1);
    public static final Dish dish6 = new Dish(6, "Salmon en papillote", 3425, date1);
    public static final Dish dish7 = new Dish(7, "Quiche Lorraine", 1125, date1);
    public static final Dish dish8 = new Dish(8, "Croque monsieur", 980, date1);
    public static final Dish dish9 = new Dish(9, "Boeuf bourguignon", 4423, date1);
    public static final Dish dish10 = new Dish(10, "Cassoulet", 332, date2);
    public static final Dish dish11 = new Dish(11, "Coca-Cola", 75, date2);
    public static final Dish dish12 = new Dish(12, "Lamb shank navarin", 121, date2);
    public static final Dish dish13 = new Dish(13, "Hazelnut dacquoise", 99, date2);
    public static final Dish dish14 = new Dish(14, "Frangipane tart", 1292, date2);
    public static final Dish dish15 = new Dish(15, "Souffle", 519, date2);
    public static final Dish dish16 = new Dish(16, "Paris-brest", 1200, date2);
    public static final Dish dish17 = new Dish(17, "Cheesy tuna pasta", 119, date2);
    public static final Dish dish18 = new Dish(18, "Tarte tatin", 113, date2);

    public static final DishTo dishTo1 = new DishTo(DISH_ID_1, "Just Lunch", 211, date1, 1);
    public static final DishTo dishTo2 = new DishTo(2, "Steak frites", 244, date1, 1);
    public static final DishTo dishTo3 = new DishTo(3, "Chicken confit", 135, date1, 1);
    public static final DishTo dishTo4 = new DishTo(4, "French onion soup", 1251, date1, 2);
    public static final DishTo dishTo5 = new DishTo(5, "Bouillabaisse", 742, date1, 2);
    public static final DishTo dishTo6 = new DishTo(6, "Salmon en papillote", 3425, date1, 2);
    public static final DishTo dishTo7 = new DishTo(7, "Quiche Lorraine", 1125, date1, 3);
    public static final DishTo dishTo8 = new DishTo(8, "Croque monsieur", 980, date1, 3);
    public static final DishTo dishTo9 = new DishTo(9, "Boeuf bourguignon", 4423, date1, 3);

    public static final List<Dish> menu = List.of(dish3, dish1, dish2);
    public static final List<Dish> dishesRestaurant = List.of(dish10, dish11, dish12, dish1,
            dish2, dish3);

    public static final List<DishTo> allMenusForDay = List.of(dishTo1, dishTo2, dishTo3, dishTo4, dishTo5,
            dishTo6, dishTo7, dishTo8, dishTo9);

    protected static Dish getNew() {
        return new Dish(null, "newDish", 9999, LocalDate.now());
    }
}
