package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends BaseTo {

    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    String title;

    @Range(min = 10, max = 10000)
    @NotNull
    Integer price;

    @NotNull
    LocalDate localDate;

    @Range(min = 0)
    @NotNull
    Integer restaurantId;

    public DishTo(Integer id, String title, Integer price, LocalDate localDate, Integer restaurantId) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
        this.restaurantId = restaurantId;
    }
}
