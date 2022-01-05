package com.strelchenya.restaurantvoting.util;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.to.VoteTo;

public class ToUtil {

    public static Vote asVote(VoteTo voteTo) {
        return new Vote(voteTo.getLocalDate(), voteTo.getLocalTime(), null, null);
    }

    public static VoteTo asVoteTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getLocalDate(), vote.getLocalTime(), vote.getRestaurant().id());
    }
}
