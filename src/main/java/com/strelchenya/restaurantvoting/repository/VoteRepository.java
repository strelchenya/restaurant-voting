package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.to.VoteTo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, r.id) " +
            "FROM Vote v LEFT OUTER JOIN Restaurant r ON v.restaurant.id=r.id " +
            "WHERE  v.user.id=:userId " +
            "GROUP BY v.id " +
            "ORDER BY v.localDate, v.localTime DESC")
    List<VoteTo> getAll(@Param("userId") int userId);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, r.id) " +
            "FROM Vote v LEFT OUTER JOIN Restaurant r ON v.restaurant.id=r.id " +
            "WHERE v.user.id=:userId and v.localDate=:localDate")
    Optional<VoteTo> getByUserIdAndLocalDate(@Param("userId") int userId, @Param("localDate") LocalDate localDate);

    @Query("SELECT new com.strelchenya.restaurantvoting.to.VoteTo(v.id, v.localDate, v.localTime, r.id) " +
            "FROM Vote v LEFT OUTER JOIN Restaurant r ON v.restaurant.id=r.id " +
            "WHERE v.id=:id and v.user.id=:userId")
    Optional<VoteTo> getByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id and v.user.id=:userId and v.localDate=CURRENT_DATE")
    int deleteByUserId(@Param("userId") int userId, @Param("id") int id);
}