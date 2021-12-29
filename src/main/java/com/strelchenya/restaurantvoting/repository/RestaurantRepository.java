package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>{
}
