package com.strelchenya.restaurantvoting.repository;

import com.strelchenya.restaurantvoting.model.Dish;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish>{
}
