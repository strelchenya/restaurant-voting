package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "title",
        name = "restaurant_unique_title_idx")})
public class Restaurant extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    private String title;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantDish")
    private List<Dish> menu;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "restaurantVote")
    private List<Vote> votes;

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
                ", menu=" + menu +
                ", votes=" + votes +
                '}';
    }
}
