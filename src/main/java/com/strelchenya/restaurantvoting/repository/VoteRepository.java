package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Vote;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

}