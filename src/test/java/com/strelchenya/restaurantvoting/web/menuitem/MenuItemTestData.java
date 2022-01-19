package com.strelchenya.restaurantvoting.web.menuitem;

import com.strelchenya.restaurantvoting.model.MenuItem;
import com.strelchenya.restaurantvoting.to.MenuItemTo;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date1;
import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.date2;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final MatcherFactory.Matcher<MenuItemTo> MENU_TEM_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(MenuItemTo.class);

    public static final int NOT_FOUND_MENU_ITEM = 0;
    public static final int MENU_ITEM_ID_1 = 1;

    public static final MenuItem MENU_ITEM_1 = new MenuItem(MENU_ITEM_ID_1, "Just Lunch", 211, date1);
    public static final MenuItem MENU_ITEM_2 = new MenuItem(2, "Steak frites", 244, date1);
    public static final MenuItem MENU_ITEM_3 = new MenuItem(3, "Chicken confit", 135, date1);
    public static final MenuItem MENU_ITEM_4 = new MenuItem(4, "French onion soup", 1251, date1);
    public static final MenuItem MENU_ITEM_5 = new MenuItem(5, "Bouillabaisse", 742, date1);
    public static final MenuItem MENU_ITEM_6 = new MenuItem(6, "Salmon en papillote", 3425, date1);
    public static final MenuItem MENU_ITEM_7 = new MenuItem(7, "Quiche Lorraine", 1125, date1);
    public static final MenuItem MENU_ITEM_8 = new MenuItem(8, "Croque monsieur", 980, date1);
    public static final MenuItem MENU_ITEM_9 = new MenuItem(9, "Boeuf bourguignon", 4423, date1);
    public static final MenuItem MENU_ITEM_10 = new MenuItem(10, "Cassoulet", 332, date2);
    public static final MenuItem MENU_ITEM_11 = new MenuItem(11, "Coca-Cola", 75, date2);
    public static final MenuItem MENU_ITEM_12 = new MenuItem(12, "Lamb shank navarin", 121, date2);
    public static final MenuItem MENU_ITEM_13 = new MenuItem(13, "Hazelnut dacquoise", 99, date2);
    public static final MenuItem MENU_ITEM_14 = new MenuItem(14, "Frangipane tart", 1292, date2);
    public static final MenuItem MENU_ITEM_15 = new MenuItem(15, "Souffle", 519, date2);
    public static final MenuItem MENU_ITEM_16 = new MenuItem(16, "Paris-brest", 1200, date2);
    public static final MenuItem MENU_ITEM_17 = new MenuItem(17, "Cheesy tuna pasta", 119, date2);
    public static final MenuItem MENU_ITEM_18 = new MenuItem(18, "Tarte tatin", 113, date2);

    public static final MenuItemTo MENU_ITEM_TO_1 = new MenuItemTo(MENU_ITEM_ID_1, "Just Lunch", 211, date1, 1);
    public static final MenuItemTo MENU_ITEM_TO_2 = new MenuItemTo(2, "Steak frites", 244, date1, 1);
    public static final MenuItemTo MENU_ITEM_TO_3 = new MenuItemTo(3, "Chicken confit", 135, date1, 1);
    public static final MenuItemTo MENU_ITEM_TO_4 = new MenuItemTo(4, "French onion soup", 1251, date1, 2);
    public static final MenuItemTo MENU_ITEM_TO_5 = new MenuItemTo(5, "Bouillabaisse", 742, date1, 2);
    public static final MenuItemTo MENU_ITEM_TO_6 = new MenuItemTo(6, "Salmon en papillote", 3425, date1, 2);
    public static final MenuItemTo MENU_ITEM_TO_7 = new MenuItemTo(7, "Quiche Lorraine", 1125, date1, 3);
    public static final MenuItemTo MENU_ITEM_TO_8 = new MenuItemTo(8, "Croque monsieur", 980, date1, 3);
    public static final MenuItemTo MENU_ITEM_TO_9 = new MenuItemTo(9, "Boeuf bourguignon", 4423, date1, 3);

    public static final List<MenuItem> menu = List.of(MENU_ITEM_3, MENU_ITEM_1, MENU_ITEM_2);
    public static final List<MenuItem> menuItemsRestaurant = List.of(MENU_ITEM_10, MENU_ITEM_11, MENU_ITEM_12, MENU_ITEM_1,
            MENU_ITEM_2, MENU_ITEM_3);

    public static final List<MenuItemTo> allMenusForDay = List.of(MENU_ITEM_TO_1, MENU_ITEM_TO_2, MENU_ITEM_TO_3, MENU_ITEM_TO_4, MENU_ITEM_TO_5,
            MENU_ITEM_TO_6, MENU_ITEM_TO_7, MENU_ITEM_TO_8, MENU_ITEM_TO_9);

    protected static MenuItem getNew() {
        return new MenuItem(null, "newMenuItem", 9999, LocalDate.now());
    }
}
