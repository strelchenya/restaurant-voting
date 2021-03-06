package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.MenuItem;
import com.strelchenya.restaurantvoting.to.MenuItemTo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT d FROM MenuItem d WHERE d.id=:id")
    Optional<MenuItem> getById(int id);

    @Query("SELECT d FROM MenuItem d " +
            "WHERE d.restaurant.id=:restaurantId AND d.localDate = :localDate " +
            "ORDER BY d.title ASC, d.price ASC")
    List<MenuItem> getMenuByDate(int restaurantId, LocalDate localDate);

    @Query("SELECT d FROM MenuItem d " +
            "WHERE d.restaurant.id=:restaurantId " +
            "ORDER BY d.localDate DESC, d.title ASC ")
    List<MenuItem> getAll(int restaurantId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.MenuItemTo(d.id, d.title, d.price, d.localDate, d.restaurant.id) " +
            "FROM MenuItem d " +
            "WHERE d.localDate=:localDate " +
            "GROUP BY d.title " +
            "ORDER BY d.restaurant.id ASC, d.title ASC")
    List<MenuItemTo> getAllMenusByLocalDate(LocalDate localDate);
}
