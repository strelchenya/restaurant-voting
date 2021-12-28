package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "local_date"},
        name = "vote_unique_user_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", localDate=" + localDate +
                '}';
    }
}
