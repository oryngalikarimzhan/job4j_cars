package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.User;

public interface IUser {
    User save(User user);
    User findByEmail(String email);
    boolean deleteUser(String email);
}
