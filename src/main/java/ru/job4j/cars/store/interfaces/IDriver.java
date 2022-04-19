package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Driver;

public interface IDriver {
    Driver save(Driver driver);
    Driver findDriverById(int id);
    Driver findDriverByPassportId(String passportId);
}
