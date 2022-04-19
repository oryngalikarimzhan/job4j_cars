package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.store.interfaces.IBodyType;

public class BodyTypeStore implements IBodyType {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final BodyTypeStore INST = new BodyTypeStore();
    }

    public static BodyTypeStore instOf() {
        return BodyTypeStore.Holder.INST;
    }

    @Override
    public BodyType findBodyTypeById(int id) {
        return sessionExecutor.tx(session -> session.get(BodyType.class, id));
    }

}
