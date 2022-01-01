package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo implements HasId {

    LocalDate localDate;

    Integer restaurantId;

    @ConstructorProperties({"id", "localDate", "restaurantId"})
    public VoteTo(Integer id, LocalDate localDate, Integer restaurantId) {
        super(id);
        this.localDate = localDate;
        this.restaurantId = restaurantId;
    }
}
