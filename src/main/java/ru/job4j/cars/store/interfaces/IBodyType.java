package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.BodyType;

public interface IBodyType {
    BodyType findBodyTypeById(int id);
}
