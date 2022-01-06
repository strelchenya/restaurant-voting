package com.strelchenya.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    @Column(name = "local_date", nullable = false)
    @NotNull
    private LocalDate localDate;

    @org.hibernate.annotations.Generated(GenerationTime.NEVER)
    @Column(name = "local_time", nullable = false)
    @NotNull
    private LocalTime localTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    @JsonBackReference(value = "userVote")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = CASCADE)
    @NotNull
    @JsonBackReference(value = "restaurantVote")
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate localDate, LocalTime localTime) {
        super(id);
        this.localDate = localDate;
        this.localTime = localTime;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                '}';
    }
}
