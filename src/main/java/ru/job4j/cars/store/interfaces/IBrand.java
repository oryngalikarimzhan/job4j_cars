package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Brand;

import java.util.Collection;

public interface IBrand {
    Brand findBrandById(int id);
    Collection<Brand> findBrandsByCategoryId(int id);
}
