package com.strelchenya.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemTo extends BaseTo {
    String title;
    int price;
    LocalDate localDate;
    Integer restaurantId;

    public MenuItemTo(Integer id, String title, int price, LocalDate localDate, Integer restaurantId) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
        this.restaurantId = restaurantId;
    }
}
