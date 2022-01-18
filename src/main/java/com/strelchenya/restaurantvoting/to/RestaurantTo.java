package com.strelchenya.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends BaseTo {

    String title;

    Long voices;

    public RestaurantTo(int id, String title, Long voices) {
        super(id);
        this.title = title;
        this.voices = voices;
    }
}
