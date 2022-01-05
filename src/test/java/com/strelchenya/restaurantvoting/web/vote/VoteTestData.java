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

    public static final int VOTE_ID_1 = 1;
    public static final int VOTE_ID_2 = 2;
    public static final int VOTE_ID_3 = 3;
    public static final int VOTE_ID_4 = 4;
    public static final int VOTE_ID_5 = 5;

    public static final LocalDate date_1 = LocalDate.of(2021, 12, 12);
    public static final LocalDate date_2 = LocalDate.of(2021, 12, 13);

    public static final Vote vote_1 = new Vote(VOTE_ID_1, date_1, LocalTime.of(6, 1));
    public static final Vote vote_2 = new Vote(VOTE_ID_2, date_1, LocalTime.of(8, 0));
    public static final Vote vote_3 = new Vote(VOTE_ID_3, date_2, LocalTime.of(8, 20));
    public static final Vote vote_4 = new Vote(VOTE_ID_4, date_2, LocalTime.of(10, 59));
    public static final Vote vote_5 = new Vote(VOTE_ID_5, LocalDate.now(), LocalTime.of(8, 8));

    public static final VoteTo vote_to_1 = new VoteTo(VOTE_ID_1, date_1, LocalTime.of(6, 1), 1);
    public static final VoteTo vote_to_2 = new VoteTo(VOTE_ID_2, date_1, LocalTime.of(8, 0), 2);
    public static final VoteTo vote_to_3 = new VoteTo(VOTE_ID_3, date_2, LocalTime.of(8, 20), 3);
    public static final VoteTo vote_to_4 = new VoteTo(VOTE_ID_4, date_2, LocalTime.of(10, 59), 2);
    public static final VoteTo vote_to_5 = new VoteTo(VOTE_ID_5, LocalDate.now(), LocalTime.of(8, 8), 3);

    public static final List<VoteTo> voteTosUser = List.of(vote_to_1, vote_to_3, vote_to_5);
    public static final List<VoteTo> voteTosAdmin = List.of(vote_to_2, vote_to_4);

    protected static VoteTo getNew() {
        return new VoteTo(null, LocalDate.now(), LocalTime.now(), 1);
    }
}
