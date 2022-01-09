package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Dish;
import com.strelchenya.restaurantvoting.to.DishTo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    Optional<Dish> getByIdAndRestaurant_Id(int id, int restaurantId);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId AND d.localDate = :localDate ORDER BY d.title ASC")
    List<Dish> getMenuByDate(int restaurantId, LocalDate localDate);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.localDate DESC")
    List<Dish> getAll(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.DishTo(d.id, d.title, d.price, d.localDate, r.id) " +
            "FROM Dish d LEFT OUTER JOIN Restaurant r ON d.restaurant.id=r.id " +
            "WHERE d.localDate=:localDate " +
            "GROUP BY d.id " +
            "ORDER BY r.id ASC")
    List<DishTo> getAllMenusByLocalDate(LocalDate localDate);
}
