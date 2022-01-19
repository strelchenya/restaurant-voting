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
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "local_date"},
        name = "menu_item_unique_title_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuItem extends BaseEntity {

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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public MenuItem(Integer id, String title, int price, LocalDate localDate) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
    }

    public MenuItem(Integer id, String title, int price, LocalDate localDate, Restaurant restaurant) {
        super(id);
        this.title = title;
        this.price = price;
        this.localDate = localDate;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", localDate=" + localDate +
                '}';
    }
}
