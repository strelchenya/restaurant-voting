package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(Optional.of(restaurantRepository.getById(id)).orElse(null), id);
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        return restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    public List<RestaurantTo> getAllByDishesDate(LocalDate date) {
        return restaurantRepository.getAllByDate(date);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }
}
