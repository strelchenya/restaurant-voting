package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.repository.UserRepository;
import com.strelchenya.restaurantvoting.repository.VoteRepository;
import com.strelchenya.restaurantvoting.to.VoteTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.ToUtil.asVote;
import static com.strelchenya.restaurantvoting.util.ToUtil.asVoteTo;
import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service("voteService")
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteTo getByUserIdAndLocalDate(int userId, LocalDate localDate) {
        return voteRepository.getByUserIdAndLocalDate(userId, localDate).orElseThrow(() ->
                new NotFoundException("not found vote by date " + localDate + " and user id " + userId));
    }

    public VoteTo getByIdAndUserId(int id, int userId) {
        return voteRepository.getByIdAndUserId(id, userId).orElseThrow(() ->
                new NotFoundException("not found vote by id " + id + " and user id " + userId));
    }

    public List<VoteTo> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    @Transactional
    public void deleteByUserId(int userId, int id) {
        checkNotFoundWithId(voteRepository.deleteByUserId(userId, id) != 0, id);
    }

    @Transactional
    public VoteTo create(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null!");
        checkNew(voteTo);
        Vote vote = asVote(voteTo);
        vote.setUser(userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("not found user by Id " + userId)));
        vote.setRestaurant(restaurantRepository.findById(voteTo.getRestaurantId()).orElseThrow(() ->
                new NotFoundException("not found restaurant by id " + voteTo.getRestaurantId())));
        log.info("convert VoteTo to Vote: {}", vote);
        return asVoteTo(voteRepository.save(vote));
    }

    @Transactional
    public void update(VoteTo voteTo, int userId) {
        Assert.notNull(voteTo, "vote must not be null!");
        assureIdConsistent(voteTo, voteTo.id());

        LocalDate checkDate = voteRepository.checkDate(voteTo.getId(), userId).orElseThrow(() ->
                new NotFoundException("not found localDate by Id " + voteTo.getId()));

        if (checkDate.equals(voteTo.getLocalDate())) {
            Vote vote = new Vote(voteTo.getId(), voteTo.getLocalDate(), voteTo.getLocalTime());
            vote.setUser(userRepository.findById(userId).orElseThrow(() ->
                    new NotFoundException("not found user by Id " + userId)));
            vote.setRestaurant(restaurantRepository.findById(voteTo.getRestaurantId()).orElseThrow(() ->
                    new NotFoundException("not found restaurant by id " + voteTo.getRestaurantId())));
            log.info("convert VoteTo to Vote: {}", vote);
            voteRepository.save(vote);
        }
    }
}
