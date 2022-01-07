package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.HasId;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo implements HasId {

    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    private String title;

    @NotNull
    private Long voices;

    public RestaurantTo(int id, String title, Long voices) {
        super(id);
        this.title = title;
        this.voices = voices;
    }
}
