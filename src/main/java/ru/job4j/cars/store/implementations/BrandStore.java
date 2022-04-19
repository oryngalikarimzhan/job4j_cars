package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.Brand;
import ru.job4j.cars.store.interfaces.IBrand;

import java.util.Collection;

public class BrandStore implements IBrand {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final BrandStore INST = new BrandStore();
    }

    public static BrandStore instOf() {
        return BrandStore.Holder.INST;
    }
    @Override
    public Brand findBrandById(int id) {
        return (Brand) sessionExecutor.tx(session ->
                session.createQuery("from Brand where id = :id")
                        .setParameter("id", id)
                        .uniqueResult());
    }


    @Override
    public Collection<Brand> findBrandsByCategoryId(int id) {
        return sessionExecutor.tx(session ->
                session.createQuery("select b from Brand b where b.id in ("
                                + "select distinct m.brand.id from Model m where category_id = :id)")
                        .setParameter("id", id)
                        .list()
        );
    }
}
