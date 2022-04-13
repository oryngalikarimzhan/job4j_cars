package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Transient
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Model> models = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Model> getModels() {
        return models;
    }
}
