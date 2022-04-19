package ru.job4j.cars.store.implementations;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.store.interfaces.IDriver;

public class DriverStore implements IDriver {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final DriverStore INST = new DriverStore();
    }

    public static DriverStore instOf() {
        return DriverStore.Holder.INST;
    }

    @Override
    public Driver save(Driver driver) {
        return sessionExecutor.tx(session -> {
            session.save(driver);
            return driver;
        });
    }

    @Override
    public Driver findDriverById(int id) {
        return (Driver) sessionExecutor.tx(session ->
                session.createCriteria(Driver.class)
                        .setFetchMode("cars", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Driver findDriverByPassportId(String passportId) {
        return (Driver) sessionExecutor.tx(session ->
                session.createCriteria(Driver.class)
                        .setFetchMode("cars", FetchMode.EAGER)
                        .add(Restrictions.eq("passportId", passportId))
                        .uniqueResult()
        );
    }
}
