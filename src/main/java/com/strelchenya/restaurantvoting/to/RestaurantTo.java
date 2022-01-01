package com.strelchenya.restaurantvoting.to;

import com.strelchenya.restaurantvoting.HasId;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo implements HasId, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    private String title;

    @Positive
    private long voices;

    @ConstructorProperties({"id", "title", "voices"})
    public RestaurantTo(int id, String title, long voices) {
        super(id);
        this.title = title;
        this.voices = voices;
    }
}
