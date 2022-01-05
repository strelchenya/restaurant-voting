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

    @Range(min = 1)
    @NotNull
    Integer restaurantId;

    @ConstructorProperties({"id", "localDate", "localTime", "restaurantId"})
    public VoteTo(Integer id, LocalDate localDate, LocalTime localTime, Integer restaurantId) {
        super(id);
        this.localDate = localDate;
        this.localTime = localTime;
        this.restaurantId = restaurantId;
    }
}
