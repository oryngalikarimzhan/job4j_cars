package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Engine;

public interface IEngine {
    Engine save(Engine engine);
    Engine findEngineById(int id);
}
