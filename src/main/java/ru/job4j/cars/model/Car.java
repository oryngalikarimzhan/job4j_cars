package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "body_type_id")
    private BodyType bodyType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Engine engine;

    @Column(name = "assembly_year")
    private String assemblyYear;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner", joinColumns = {
            @JoinColumn(name = "driver_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "car_id", nullable = false, updatable = false)})
    private Set<Driver> drivers = new HashSet<>();

    public static Car of(
            String serialNumber,
            String registrationNumber,
            Brand brand,
            Model model,
            BodyType bodyType,
            Engine engine,
            String assemblyYear
            ) {
        Car car = new Car();
        car.serialNumber = serialNumber;
        car.registrationNumber = registrationNumber;
        car.assemblyYear = assemblyYear;
        car.brand = brand;
        car.model = model;
        car.bodyType = bodyType;
        car.engine = engine;
        return car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Brand getBrand() {
        return brand;
    }

    public Model getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getAssemblyYear() {
        return assemblyYear;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(serialNumber, car.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }
}
