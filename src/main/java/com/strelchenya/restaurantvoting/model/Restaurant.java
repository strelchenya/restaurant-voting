package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "title",
        name = "restaurants_unique_title_idx")})
public class Restaurant extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(max = 255)
    @NotBlank
    private String title;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Set<Dish> menu;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Set<Vote> votes;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
