package com.strelchenya.restaurantvoting.web.vote;

import com.strelchenya.restaurantvoting.service.VoteService;
import com.strelchenya.restaurantvoting.to.VoteTo;
import com.strelchenya.restaurantvoting.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.web.vote.VoteController.VOTE_URL;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class VoteController {
    public static final String VOTE_URL = "/api/v1/profile/votes";

    private final VoteService voteService;

    @GetMapping("/by-date")
    public ResponseEntity<VoteTo> getByUserIdAndLocalDate(@AuthenticationPrincipal AuthUser authUser,
                                          @RequestParam("local-date") @Nullable LocalDate localDate) {
        log.info("user {}, localDate {}", authUser.id(), localDate);
        return ResponseEntity.ok(voteService.getByUserIdAndLocalDate(authUser.id(), localDate));
    }

    @GetMapping("/{id}")
    public VoteTo getByIdAndUserId(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("vote {}, userId {}", id, authUser.id());
        return voteService.getByIdAndUserId(id, authUser.id());
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("userId {}", authUser.id());
        return voteService.getAll(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VoteTo> create(@Valid @RequestBody VoteTo voteTo,
                                         @AuthenticationPrincipal AuthUser authUser) {
        log.info("save {} for userId {}", voteTo, authUser.id());
        VoteTo created = voteService.create(voteTo, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} for userId {}", voteTo, authUser.id());
        voteService.update(voteTo, authUser.id());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserId(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("delete vote {} for userId {}", id, authUser.id());
        voteService.deleteByUserId(authUser.id(), id);
    }
}
