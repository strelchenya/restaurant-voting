package com.strelchenya.restaurantvoting.web.vote;

import com.strelchenya.restaurantvoting.service.VoteService;
import com.strelchenya.restaurantvoting.to.VoteTo;
import com.strelchenya.restaurantvoting.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;
import static com.strelchenya.restaurantvoting.web.vote.VoteController.VOTE_URL;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Tag(name = "Voting Controller", description = "Controller for voting for the restaurant menu by authenticated users.")
public class VoteController {
    public static final String VOTE_URL = "/api/v1/profile/votes";

    private final VoteService voteService;

    @Operation(summary = "Get user vote by date", description = "Get authenticated user's vote by voting date.")
    @GetMapping("/by-date")
    public VoteTo getByUserIdAndLocalDate(@AuthenticationPrincipal AuthUser authUser,
                                          @RequestParam("local-date") @Nullable LocalDate localDate) {
        log.info("user {}, localDate {}", authUser.id(), localDate);
        return voteService.getByUserIdAndLocalDate(authUser.id(), localDate);
    }

    @Operation(summary = "Get vote", description = "Get authenticated user's vote by vote id.")
    @GetMapping("/{id}")
    public VoteTo getByIdAndUserId(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("vote {}, userId {}", id, authUser.id());
        return voteService.getByIdAndUserId(id, authUser.id());
    }

    @Operation(summary = "Get all votes", description = "Get all votes of an authenticated user.")
    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("userId {}", authUser.id());
        return voteService.getAll(authUser.id());
    }

    @Operation(summary = "Create voice", description = "Creating a new voice throughout the day.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@Valid @RequestBody VoteTo voteTo,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Assert.notNull(voteTo, "vote must not be null!");
        checkNew(voteTo);
        log.info("save {} for userId {}", voteTo, authUser.id());
        VoteTo created = voteService.create(voteTo, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Update voice", description = "Update voice up to 11 a.m.")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable int id,
                       @AuthenticationPrincipal AuthUser authUser) {
        Assert.notNull(voteTo, "vote must not be null!");
        assureIdConsistent(voteTo, id);
        log.info("update {} with id {} for userId {}", voteTo, id, authUser.id());
        voteService.update(voteTo, id, authUser.id());
    }

    @Operation(summary = "Delete voice", description = "Removes voice up to 11 a.m.")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserId(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete vote {} for userId {}", id, authUser.id());
        checkNotFoundWithId(voteService.deleteByUserId(authUser.id(), id) != 0, id);
    }
}
