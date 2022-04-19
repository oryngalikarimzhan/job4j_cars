package ru.job4j.cars.store.implementations;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.store.interfaces.ICar;

public class CarStore implements ICar {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final CarStore INST = new CarStore();
    }

    public static CarStore instOf() {
        return CarStore.Holder.INST;
    }
    @Override
    public Car save(Car car) {
        int id = car.getId();
        Car rsl = null;
        if (id == 0) {
            rsl = create(car);
        } else if (findCarById(id) != null) {
            rsl = update(car);
        }
        return rsl;
    }

    private Car create(Car car) {
        return (Car) sessionExecutor.tx(session -> {
            session.save(car);
            return session.createQuery("from Car order by id desc").setMaxResults(1).getSingleResult();
        });
    }

    private Car update(Car car) {
        return sessionExecutor.tx(session -> {
            session.update(car);
            return car;
        });
    }

    @Override
    public Car findCarById(int id) {
        return (Car) sessionExecutor.tx(session ->
                session.createCriteria(Car.class)
                        .setFetchMode("drivers", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Car findBySN(String serialNumber) {
        return (Car) sessionExecutor.tx(session ->
                session.createCriteria(Car.class)
                        .setFetchMode("drivers", FetchMode.EAGER)
                        .add(Restrictions.eq("serialNumber", serialNumber))
                        .uniqueResult()
        );
    }
}
