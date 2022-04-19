package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.Engine;
import ru.job4j.cars.store.interfaces.IEngine;

public class EngineStore implements IEngine {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final EngineStore INST = new EngineStore();
    }

    public static EngineStore instOf() {
        return EngineStore.Holder.INST;
    }
    @Override
    public Engine save(Engine engine) {
        int id = engine.getId();
        Engine rsl = null;
        if (id == 0) {
            rsl = create(engine);
        } else if (findEngineById(id) != null) {
            rsl = update(engine);
        }
        return rsl;
    }

    private Engine create(Engine engine) {
        return sessionExecutor.tx(session -> {
            session.save(engine);
            return engine;
        });
    }

    private Engine update(Engine engine) {
        return sessionExecutor.tx(session -> {
            session.update(engine);
            return engine;
        });
    }

    @Override
    public Engine findEngineById(int id) {
        return sessionExecutor.tx(session -> session.get(Engine.class, id));
    }

}
