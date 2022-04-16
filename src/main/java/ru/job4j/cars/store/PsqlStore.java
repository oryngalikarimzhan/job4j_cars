package ru.job4j.cars.store;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import ru.job4j.cars.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class PsqlStore implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new ru.job4j.cars.store.PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Post's database methods
     * */

    @Override
    public Post save(Post post) {
        int id = post.getId();
        Post rsl = null;
        if (id == 0) {
            rsl = create(post);
        } else {
            rsl = update(post);
        }
        return rsl;
    }

    private Post create(Post post) {
        return this.tx(session -> {
            Car carInDb = findBySN(post.getCar().getSerialNumber());
            User userInDb = findByEmail(post.getUser().getEmail());
            if (carInDb != null || userInDb != null) {
                session.merge(post);
            } else {
                session.persist(post);
            }
            return (Post) session.createQuery("from Post order by id desc")
                    .setMaxResults(1).uniqueResult();
        });
    }

    private Post update(Post post) {
        return this.tx(session -> {
            session.update(post);
            return session.get(Post.class, post.getId());
        });
    }

    @Override
    public Post findPostById(int id) {
        return (Post) this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "join fetch p.user "
                        + "left join fetch p.image "
                        + "join fetch p.car c "
                        + "join fetch c.engine "
                        + "join fetch c.bodyType "
                        + "join fetch c.brand "
                        + "join fetch c.model "
                        + "where p.id = :id")
                .setParameter("id", id)
                .getSingleResult()
        );
    }

    @Override
    public Collection<Post> findUserPostsById(int id) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from ru.job4j.cars.model.Post p "
                        + "join fetch p.user "
                        + "left join fetch p.image "
                        + "join fetch p.car c "
                        + "join fetch c.brand "
                        + "left join fetch c.model "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.engine "
                        + "where user_id = :id "
                        + "order by p.id asc"
                ).setParameter("id", id)
                .list()
        );
    }

    @Override
    public Collection<Post> findAllPosts() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.image "
                        + "left join fetch p.car c "
                        + "left join fetch c.brand "
                        + "left join fetch c.model "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.engine "
                        + "order by p.id asc")
                .list()
        );
    }

    @Override
    public Collection<Post> findAllActivePosts() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "left join fetch p.user "
                        + "left join fetch p.image "
                        + "left join fetch p.car c "
                        + "left join fetch c.brand "
                        + "left join fetch c.model "
                        + "left join fetch c.bodyType "
                        + "left join fetch c.engine "
                        + "where sold = false "
                        + "order by p.id asc")
                .list()
        );
    }

    @Override
    public boolean deletePost(int id) {
        return this.tx(session -> {
            session.delete(findPostById(id));
            return true;
        });
    }


    /**
     * Car's database methods
     * */

    @Override
    public Car save(Car car) {
        int id = car.getId();
        Car rsl = null;
        if (id == 0) {
            rsl = create(car);
        } else if (findCarById(id) != null) {
            rsl = update(car);
        }
        return rsl;
    }

    private Car create(Car car) {
        return this.tx(session -> {
            session.save(car);
            return car;
        });
    }

    private Car update(Car car) {
        return this.tx(session -> {
            session.update(car);
            return car;
        });
    }

    @Override
    public Car findCarById(int id) {
        return (Car) this.tx(session ->
                session.createCriteria(Car.class)
                        .setFetchMode("drivers", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Car findBySN(String serialNumber) {
        return (Car) this.tx(session ->
            session.createCriteria(Car.class)
                    .setFetchMode("drivers", FetchMode.EAGER)
                    .add(Restrictions.eq("serialNumber", serialNumber))
                    .uniqueResult()
        );
    }

    /**
     * Brand's database methods
     * */

    @Override
    public Brand findBrandById(int id) {
        return this.tx(session -> session.get(Brand.class, id));
    }

    @Override
    public Collection<Brand> findAllBrands() {
        return this.tx(
                session -> session.createQuery("from Brand order by id asc")
                        .list()
        );
    }


    /**
     * Model's database methods
     * */

    @Override
    public Model findModelById(int id) {
        return (Model) this.tx(session ->
                session.createCriteria(Model.class)
                        .setFetchMode("bodyTypes", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Collection<Model> findModelsByBrandId(int id) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct m "
                                + "from Model m "
                                + "join fetch m.brand "
                                + "join fetch m.bodyTypes "
                                + "where brand_id = :id "
                                + "order by m.id asc"
                ).setParameter("id", id)
                        .list()
        );
    }

    /**
     * BodyType's database methods
     * */

    @Override
    public BodyType findBodyTypeById(int id) {
        return this.tx(session -> session.get(BodyType.class, id));
    }

    /**
     * Driver's database methods
     * */

    @Override
    public Driver save(Driver driver) {
        return this.tx(session -> {
            session.save(driver);
            return driver;
        });
    }

    @Override
    public Driver findDriverById(int id) {
        return (Driver) this.tx(session ->
                session.createCriteria(Driver.class)
                        .setFetchMode("cars", FetchMode.EAGER)
                        .add(Restrictions.eq("id", id))
                        .uniqueResult()
        );
    }

    @Override
    public Driver findDriverByPassportId(String passportId) {
        return (Driver) this.tx(session ->
                session.createCriteria(Driver.class)
                        .setFetchMode("cars", FetchMode.EAGER)
                        .add(Restrictions.eq("passportId", passportId))
                        .uniqueResult()
        );
    }

    /**
     * Engine's database methods
     * */

    @Override
    public Engine save(Engine engine) {
        int id = engine.getId();
        Engine rsl = null;
        if (id == 0) {
            rsl = create(engine);
        } else if (findEngineById(id) != null) {
            rsl = update(engine);
        }
        return rsl;
    }

    private Engine create(Engine engine) {
        return this.tx(session -> {
            session.save(engine);
            return engine;
        });
    }

    private Engine update(Engine engine) {
        return this.tx(session -> {
            session.update(engine);
            return engine;
        });
    }

    @Override
    public Engine findEngineById(int id) {
        return this.tx(session -> session.get(Engine.class, id));
    }

    /**
     * User's database methods
     * */

    @Override
    public User save(User user) {
        return this.tx(session -> {
            if (findByEmail(user.getEmail()) == null) {
                session.save(user);
            } else {
                session.update(user);
            }
            return user;
        });
    }

    @Override
    public User findByEmail(String email) {
        return (User) this.tx(session -> session.createQuery("from User where email=:email")
                .setParameter("email", email).uniqueResult()
        );
    }

    @Override
    public boolean deleteUser(String email) {
        return this.tx(session -> {
            User user = findByEmail(email);
            for (Post post : findUserPostsById(user.getId())) {
                session.delete(post);
            }
            session.delete(user);
            return true;
        });
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

