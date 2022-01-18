package com.strelchenya.restaurantvoting.util;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.to.VoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteToUtil {

    public static Vote asVote(VoteTo voteTo) {
        return new Vote(voteTo.getId(), voteTo.getLocalDate(), voteTo.getLocalTime(), null, null);
    }

    public static VoteTo asVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getLocalDate(), vote.getLocalTime(), vote.getRestaurant().id());
    }
}
