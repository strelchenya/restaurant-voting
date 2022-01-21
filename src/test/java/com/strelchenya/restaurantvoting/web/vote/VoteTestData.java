package com.strelchenya.restaurantvoting.web.vote;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.to.VoteTo;
import com.strelchenya.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int NOT_FOUND_VOTE = 0;
    public static final int VOTE_ID_1 = 1;
    public static final int VOTE_ID_2 = 2;
    public static final int VOTE_ID_3 = 3;
    public static final int VOTE_ID_4 = 4;
    public static final int VOTE_ID_5 = 5;

    public static final LocalDate date1 = LocalDate.of(2021, 12, 12);
    public static final LocalDate date2 = LocalDate.of(2021, 12, 13);

    public static final Vote vote1 = new Vote(VOTE_ID_1, date1, LocalTime.of(6, 1));
    public static final Vote vote2 = new Vote(VOTE_ID_2, date1, LocalTime.of(8, 0));
    public static final Vote vote3 = new Vote(VOTE_ID_3, date2, LocalTime.of(8, 20));
    public static final Vote vote4 = new Vote(VOTE_ID_4, date2, LocalTime.of(10, 59));
    public static final Vote vote5 = new Vote(VOTE_ID_5, LocalDate.now(), LocalTime.of(8, 8));

    public static final VoteTo VOTE_TO_1 = new VoteTo(VOTE_ID_1, date1, LocalTime.of(6, 1), 1);
    public static final VoteTo VOTE_TO_2 = new VoteTo(VOTE_ID_2, date1, LocalTime.of(8, 0), 2);
    public static final VoteTo VOTE_TO_3 = new VoteTo(VOTE_ID_3, date2, LocalTime.of(8, 20), 3);
    public static final VoteTo VOTE_TO_4 = new VoteTo(VOTE_ID_4, date2, LocalTime.of(10, 59), 2);
    public static final VoteTo VOTE_TO_5 = new VoteTo(VOTE_ID_5, LocalDate.now(), LocalTime.of(8, 8), 3);

    public static final List<VoteTo> voteTosUser = List.of(VOTE_TO_5, VOTE_TO_3, VOTE_TO_1);
    public static final List<VoteTo> voteTosAdmin = List.of(VOTE_TO_4, VOTE_TO_2);

    protected static VoteTo getNew() {
        return new VoteTo(null, LocalDate.now(), LocalTime.now(), 1);
    }
}
