package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "title"},
        name = "dish_unique_restaurant_title_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Dish extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 255)
    @NotBlank
    @NoHtml
    private String title;

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 10000)
    @NotNull
    private Integer price;

    @Column(name = "local_date", columnDefinition = "timestamp default now()", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String title, Integer price, LocalDate localDate) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "price = " + price + ", " +
                "date = " + localDate + ")";
    }
}
