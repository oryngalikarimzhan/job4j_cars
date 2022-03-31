package ru.job4j.cars.model;

import javax.persistence.*;

@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "serial_number")
    private long serialNum;
    private int power;
    private int volume;
}
