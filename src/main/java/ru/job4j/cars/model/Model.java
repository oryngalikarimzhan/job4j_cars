package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "models_body_types", joinColumns = {
            @JoinColumn(name = "model_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "body_type_id", nullable = false, updatable = false)})
    private Set<BodyType> bodyTypes = new HashSet<>();

    public Brand getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public Set<BodyType> getBodyTypes() {
        return bodyTypes;
    }
}
