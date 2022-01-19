package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "title",
        name = "restaurant_unique_title_idx")})
public class Restaurant extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    @NoHtml
    private String title;

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MenuItem> menu;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Vote> votes;

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getTitle());
    }

    public Restaurant(Integer id, String title) {
        super(id);
        this.title = title;
    }

    public Restaurant(Integer id, String title, List<MenuItem> menu, List<Vote> votes) {
        super(id);
        this.title = title;
        this.menu = menu;
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
