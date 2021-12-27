package com.strelchenya.restaurantvoting.model;

import com.strelchenya.restaurantvoting.HasId;

public interface HasIdAndEmail extends HasId {
    String getEmail();
}
