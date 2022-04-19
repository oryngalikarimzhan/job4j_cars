package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Category;

import java.util.Collection;

public interface ICategory {

    Category findCategoryById(int id);
    Collection<Category> findAllCategories();
}
