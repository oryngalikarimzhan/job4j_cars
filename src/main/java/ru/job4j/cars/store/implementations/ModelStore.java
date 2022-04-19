package ru.job4j.cars.store.implementations;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import ru.job4j.cars.model.Model;
import ru.job4j.cars.store.interfaces.IModel;

import java.util.Collection;

public class ModelStore implements IModel {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final ModelStore INST = new ModelStore();
    }

    public static ModelStore instOf() {
        return ModelStore.Holder.INST;
    }

    @Override
    public Model findModelById(int id) {
        return (Model) sessionExecutor.tx(session ->
                session.createCriteria(Model.class)
                        .setFetchMode("bodyTypes", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Collection<Model> findModelsByBrandIdAndCategoryId(int brandId, int categoryId) {
        return sessionExecutor.tx(
                session -> session.createQuery(
                                "select distinct m "
                                        + "from Model m "
                                        + "join fetch m.bodyTypes "
                                        + "where brand_id = :brandId "
                                        + "and category_id = :categoryId "
                                        + "order by m.id asc"
                        ).setParameter("brandId", brandId)
                        .setParameter("categoryId", categoryId)
                        .list()
        );
    }
}
