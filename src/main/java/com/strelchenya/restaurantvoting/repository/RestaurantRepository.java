package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    List<Restaurant> findAllByOrderByTitleAsc();

    @Query("SELECT new com.strelchenya.restaurantvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE v.localDate=:localDate " +
            "GROUP BY r.id, r.title " +
            "ORDER BY count(v.id) DESC, r.title ASC")
    List<RestaurantTo> getAllByDate(LocalDate localDate);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE r.id=:id AND v.localDate=:localDate " +
            "GROUP BY r.title")
    Optional<RestaurantTo> getByIdAndLocalDate(int id, LocalDate localDate);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.RestaurantTo(r.id, r.title, count(v.id)) " +
            "FROM Restaurant r LEFT OUTER JOIN Vote v ON r.id = v.restaurant.id " +
            "WHERE r.id=:id " +
            "GROUP BY r.title")
    Optional<RestaurantTo> getById(int id);
}
