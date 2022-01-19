package com.strelchenya.restaurantvoting.service;

import com.strelchenya.restaurantvoting.error.NotFoundException;
import com.strelchenya.restaurantvoting.model.Restaurant;
import com.strelchenya.restaurantvoting.repository.RestaurantRepository;
import com.strelchenya.restaurantvoting.to.RestaurantTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.strelchenya.restaurantvoting.util.validation.ValidationUtil.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantTo getByIdAndLocalDate(int id, LocalDate localDate) {
        return restaurantRepository.getByIdAndLocalDate(id, localDate)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id + " date: " + localDate));
    }

    public RestaurantTo getById(int id) {
        return restaurantRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("not found restaurant by id " + id));
    }

    public List<RestaurantTo> getAllByDate(LocalDate localDate) {
        return restaurantRepository.getAllByDate(localDate);
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(restaurant, "restaurant must not be null!");
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.id());
    }

    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteExisted(id);
    }
}
