package com.strelchenya.restaurantvoting.repository.user;

import com.strelchenya.restaurantvoting.model.User;
import com.strelchenya.restaurantvoting.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}