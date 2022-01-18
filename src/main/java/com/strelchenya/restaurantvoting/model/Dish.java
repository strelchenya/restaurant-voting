package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.strelchenya.restaurantvoting.util.validation.NoHtml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class Dish extends BaseEntity {

    @Column(name = "title", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    @NoHtml
    private String title;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "local_date", columnDefinition = "date default now()", nullable = false)
    @NotNull
    private LocalDate localDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference(value = "restaurantDish")
    private Restaurant restaurant;

    public Dish(Integer id, String title, int price, LocalDate localDate) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
    }

    public Dish(Integer id, String title, int price, LocalDate localDate, Restaurant restaurant) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", localDate=" + localDate +
                '}';
    }
}
