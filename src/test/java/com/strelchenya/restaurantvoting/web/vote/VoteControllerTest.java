package com.strelchenya.restaurantvoting.web.vote;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.to.VoteTo;
import com.strelchenya.restaurantvoting.util.JsonUtil;
import com.strelchenya.restaurantvoting.web.AbstractControllerTest;
import com.strelchenya.restaurantvoting.web.GlobalExceptionHandler;
import com.strelchenya.restaurantvoting.web.MatcherFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.strelchenya.restaurantvoting.service.VoteService.END_TIME_CHANGE_VOTE;
import static com.strelchenya.restaurantvoting.web.TestUtil.userHttpBasic;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.NOT_FOUND_RESTAURANT;
import static com.strelchenya.restaurantvoting.web.restaurant.RestaurantTestData.RESTAURANT_ID_1;
import static com.strelchenya.restaurantvoting.web.user.UserTestData.*;
import static com.strelchenya.restaurantvoting.web.vote.VoteTestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String VOTE_URL = VoteController.VOTE_URL + '/';

    @Test
    void getByUserIdAndLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + "/by-date")
                .param("local-date", "2021-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(
                        new VoteTo(VOTE_ID_1, date1, LocalTime.of(6, 1), RESTAURANT_ID_1)));
    }

    @Test
    void getByUserIdAndInvalidLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + "/by-date")
                .param("local-date", "2021-122")
                .with(userHttpBasic(user)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getByUserIdAndNotFoundLocalDate() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + "/by-date")
                .param("local-date", "1999-12-12")
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getByIdAndUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + VOTE_ID_1)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(
                        new VoteTo(VOTE_ID_1, date1, LocalTime.of(6, 1), RESTAURANT_ID_1)));
    }

    @Test
    void getByNotFoundIdAndUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + NOT_FOUND_VOTE)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getAllUserVoteTos() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTosUser));
    }

    @Test
    void getAllAdminVoteTos() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTosAdmin));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        VoteTo newVoteTo = getNew();
        perform(MockMvcRequestBuilders.post(VOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user))
                .content(JsonUtil.writeValue(newVoteTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_VOTE)));
    }

    @Test
    void create() throws Exception {
        VoteTo newVoteTo = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(VOTE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newVoteTo)))
                .andExpect(status().isCreated())
                .andDo(print());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newVoteTo.setId(newId);
        MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "localDate", "localTime")
                .assertMatch(created, newVoteTo);
        MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class, "localDate", "localTime")
                .assertMatch(voteService.getByIdAndUserId(newId, ADMIN_ID), newVoteTo);
    }

    @Test
    void update() throws Exception {
        VoteTo update = new VoteTo(VOTE_ID_5, LocalDate.now(), LocalTime.now(), RESTAURANT_ID_1);
        if (LocalTime.now().isBefore(END_TIME_CHANGE_VOTE)) {
            perform(MockMvcRequestBuilders.put(VOTE_URL + VOTE_ID_5)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(update))
                    .with(userHttpBasic(user)))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } else {
            perform(MockMvcRequestBuilders.put(VOTE_URL + VOTE_ID_5)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(update))
                    .with(userHttpBasic(user)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    void updateInvalid() throws Exception {
        VoteTo invalid = new VoteTo(NOT_FOUND_VOTE, LocalDate.now(), LocalTime.now(), 3);
        perform(MockMvcRequestBuilders.put(VOTE_URL + VOTE_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalidWithNull() throws Exception {
        VoteTo invalid = new VoteTo(VOTE_ID_1, null, null, 3);
        perform(MockMvcRequestBuilders.put(VOTE_URL + VOTE_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateNotFoundRestaurant() throws Exception {
        VoteTo invalid = new VoteTo(VOTE_ID_5, LocalDate.now(), LocalTime.now(), NOT_FOUND_RESTAURANT);
        perform(MockMvcRequestBuilders.put(VOTE_URL + VOTE_ID_5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteByUserId() throws Exception {
        if (LocalTime.now().isBefore(END_TIME_CHANGE_VOTE)) {
            perform(MockMvcRequestBuilders.delete(VOTE_URL + VOTE_ID_5)
                    .with(userHttpBasic(user)))
                    .andExpect(status().isNoContent())
                    .andDo(print());

            assertThrows(NotFoundException.class, () -> voteService.getByIdAndUserId(VOTE_ID_5, USER_ID));
        } else {
            perform(MockMvcRequestBuilders.delete(VOTE_URL + VOTE_ID_5)
                    .with(userHttpBasic(user)))
                    .andExpect(status().isUnprocessableEntity())
                    .andDo(print());
        }
    }

    @Test
    void deleteNotToday() throws Exception {
        perform(MockMvcRequestBuilders.delete(VOTE_URL + VOTE_ID_1)
                .with(userHttpBasic(user)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}