package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    Optional<Dish> getByIdAndRestaurant_Id(int id, int restaurantId);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId AND d.localDate = :localDate ORDER BY d.title DESC")
    List<Dish> getMenuByDate(@Param("restaurantId") int restaurantId, @Param("localDate") LocalDate localDate);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.localDate DESC")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);
}
