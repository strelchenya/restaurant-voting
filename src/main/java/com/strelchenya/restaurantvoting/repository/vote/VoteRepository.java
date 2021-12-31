package com.strelchenya.restaurantvoting.repository.vote;

import com.strelchenya.restaurantvoting.model.Vote;
import com.strelchenya.restaurantvoting.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

}