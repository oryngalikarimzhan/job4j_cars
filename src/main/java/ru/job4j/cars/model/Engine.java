package ru.job4j.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int mileage;
    private int volume;
    @Column(name = "fuel_type")
    private String fuelType;

    public static Engine of(String name, int mileage, int volume, String fuelType) {
        Engine engine = new Engine();
        engine.name = name;
        engine.mileage = mileage;
        engine.volume = volume;
        engine.fuelType = fuelType;
        return engine;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMileage() {
        return mileage;
    }

    public int getVolume() {
        return volume;
    }

    public String getFuelType() {
        return fuelType;
    }
}
