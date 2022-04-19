package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.Category;
import ru.job4j.cars.store.interfaces.ICategory;

import java.util.Collection;

public class CategoryStore implements ICategory {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final CategoryStore INST = new CategoryStore();
    }

    public static CategoryStore instOf() {
        return CategoryStore.Holder.INST;
    }
    @Override
    public Category findCategoryById(int id) {
        return sessionExecutor.tx(session -> session.get(Category.class, id));
    }


    @Override
    public Collection<Category> findAllCategories() {
        return sessionExecutor.tx(
                session -> session.createQuery("from Category order by id asc")
                        .list()
        );
    }
}
