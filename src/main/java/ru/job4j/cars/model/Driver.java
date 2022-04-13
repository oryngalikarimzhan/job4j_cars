package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "passport_id", unique = true)
    private String passportId;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "drivers")
    private Set<Car> cars = new HashSet<>();

    public static Driver of(String name, String passportId) {
        Driver driver = new Driver();
        driver.name = name;
        driver.passportId = passportId;
        return driver;
    }

    public Set<Car> getCars() {
        return cars;
    }
}
