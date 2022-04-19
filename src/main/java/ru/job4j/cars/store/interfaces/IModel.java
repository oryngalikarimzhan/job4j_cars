package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Model;

import java.util.Collection;

public interface IModel {
    Model findModelById(int id);
    Collection<Model> findModelsByBrandIdAndCategoryId(int brandId, int categoryId);
}
