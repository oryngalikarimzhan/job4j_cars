package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Car;

public interface ICar {
    Car save(Car car);
    Car findCarById(int id);
    Car findBySN(String serialNumber);
}
