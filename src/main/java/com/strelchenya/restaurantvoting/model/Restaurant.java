package com.strelchenya.restaurantvoting.model;

import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "title",
        name = "restaurant_unique_title_idx")})
public class Restaurant extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    @NoHtml
    private String title;

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getTitle());
    }

    public Restaurant(Integer id, String title) {
        super(id);
        this.title = title;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
