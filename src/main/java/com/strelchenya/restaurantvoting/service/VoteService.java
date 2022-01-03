package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.repository.UserRepository;
import com.strelchenya.restaurantvoting.repository.VoteRepository;
import com.strelchenya.restaurantvoting.to.VoteTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteTo getByUserIdAndLocalDate(int userId, LocalDate localDate) {
        return voteRepository.getByUserIdAndLocalDate(userId, localDate).orElseThrow(() ->
                new NotFoundException("not found vote by date " + localDate + "and user id " + userId));
    }

    public VoteTo getByIdAndUserId(int id, int userId) {
        return voteRepository.getByIdAndUserId(id, userId).orElseThrow(() ->
                new NotFoundException("not found vote by id " + id + "and user id " + userId));
    }

    public List<VoteTo> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    @Transactional
    public void deleteByUserId(int userId, int id) {
        checkNotFoundWithId(voteRepository.deleteByUserId(userId, id) != 0, id);
    }

    @Transactional
    public Vote create(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null!");
        checkNew(vote);
        vote.setUser(userRepository.getById(userId));
        return voteRepository.save(vote);
    }

    @Transactional
    public void update(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null!");
        assureIdConsistent(vote, vote.id());
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("not found user by Id " + userId));
        restaurantRepository.findById(vote.getRestaurant().id()).orElseThrow(() ->
                new NotFoundException("not found restaurant by id " + vote.getRestaurant().id()));
        voteRepository.save(vote);
    }
}
