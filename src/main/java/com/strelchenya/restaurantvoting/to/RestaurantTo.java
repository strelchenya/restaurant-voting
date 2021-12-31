package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo extends BaseTo {
    private static final long serialVersionUID = 1L;

    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    private String title;

    @Positive
    private long voices;

    public RestaurantTo(int id, String title, long voices) {
        super(id);
        this.title = title;
        this.voices = voices;
    }
}
