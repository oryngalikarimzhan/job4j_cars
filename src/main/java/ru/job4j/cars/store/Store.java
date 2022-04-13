package ru.job4j.cars.store;

import ru.job4j.cars.model.*;

import java.util.Collection;

public interface Store {
    Post save(Post post);
    Post findPostById(int id);
    Collection<Post> findUserPostsById(int id);
    Collection<Post> findAllPosts();
    Collection<Post> findAllActivePosts();
    boolean deletePost(int id);

    Car save(Car car);
    Car findCarById(int id);
    Car findBySN(String serialNumber);

    Brand findBrandById(int id);
    Collection<Brand> findAllBrands();

    Model findModelById(int id);
    Collection<Model> findModelsByBrandId(int id);

    BodyType findBodyTypeById(int id);

    Driver save(Driver driver);
    Driver findDriverById(int id);
    Driver findDriverByPassportId(String passportId);

    Engine save(Engine engine);
    Engine findEngineById(int id);

    User save(User user);
    User findByEmail(String email);
    boolean deleteUser(String email);
}
