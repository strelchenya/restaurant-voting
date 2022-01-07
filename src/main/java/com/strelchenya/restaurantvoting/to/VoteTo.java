package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo implements HasId {

    @NotNull
    LocalDate localDate;

    @NotNull
    LocalTime localTime;

    @Range(min = 0)
    @NotNull
    Integer restaurantId;

    public VoteTo(Integer id, LocalDate localDate, LocalTime localTime, Integer restaurantId) {
        super(id);
        this.localDate = localDate;
        this.localTime = localTime;
        this.restaurantId = restaurantId;
    }
}
