package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;

    private int price;

    private boolean sold;

    private String phone;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public static Post of(String description, int price, String phone, User user, Car car) {
        Post post = new Post();
        post.description = description;
        post.price = price;
        post.phone = phone;
        post.user = user;
        post.car = car;
        post.sold = false;
        post.created = new Date(System.currentTimeMillis());
        return post;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isSold() {
        return sold;
    }

    public Date getCreated() {
        return created;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public void setStatus(boolean isSold) {
        this.sold = isSold;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
