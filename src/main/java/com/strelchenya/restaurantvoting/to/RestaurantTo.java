package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends BaseTo {

    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    String title;

    @Range(min = 0)
    @NotNull
    Long voices;

    public RestaurantTo(int id, String title, Long voices) {
        super(id);
        this.title = title;
        this.voices = voices;
    }
}
