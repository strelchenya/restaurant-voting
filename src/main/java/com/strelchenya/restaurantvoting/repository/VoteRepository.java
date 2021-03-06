package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.to.VoteTo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, v.restaurant.id) " +
            "FROM Vote v " +
            "WHERE v.user.id=:userId " +
            "ORDER BY v.localDate DESC")
    List<VoteTo> getAll(int userId);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, v.restaurant.id) " +
            "FROM Vote v " +
            "WHERE v.user.id=:userId and v.localDate=:localDate")
    Optional<VoteTo> getByUserIdAndLocalDate(int userId, LocalDate localDate);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, v.restaurant.id) " +
            "FROM Vote v " +
            "WHERE v.id=:id and v.user.id=:userId")
    Optional<VoteTo> getByIdAndUserId(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v " +
            "WHERE v.id=:id AND v.user.id=:userId AND v.localDate=CURRENT_DATE")
    int deleteByUserId(int userId, int id);
}