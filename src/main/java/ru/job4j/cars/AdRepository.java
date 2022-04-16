package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Post;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public static AdRepository instOf() {
        return new AdRepository();
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

    public Collection<Post> findUserPostsById(int id) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                                + "from ru.job4j.cars.model.Post p "
                                + "join fetch p.user "
                                + "join fetch p.car c "
                                + "left join fetch c.brand "
                                + "left join fetch c.model "
                                + "left join fetch c.bodyType "
                                + "left join fetch c.engine "
                                + "where user_id = :id "
                                + "order by p.id asc"
                ).setParameter("id", id)
                        .list()
        );
    }

    public List<Post> findLastDayPosts() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "join fetch p.image "
                        + "join fetch p.user "
                        + "join fetch p.car c "
                        + "join fetch c.engine "
                        + "join fetch c.bodyType "
                        + "join fetch c.model "
                        + "join fetch c.brand "
                        + "where day(current_timestamp - created) <= 1")
                .list());
    }

    public List<Post> findPostsWithImage() {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "join fetch p.image "
                        + "join fetch p.user "
                        + "join fetch p.car c "
                        + "join fetch c.engine "
                        + "join fetch c.bodyType "
                        + "join fetch c.model "
                        + "join fetch c.brand "
                        + "where p.image.size != 0")
                .list());
    }

    public List<Post> findPostsByBrand(String brandName) {
        return this.tx(
                session -> session.createQuery(
                        "select distinct p "
                        + "from Post p "
                        + "join fetch p.image "
                        + "join fetch p.user "
                        + "join fetch p.car c "
                        + "join fetch c.engine "
                        + "join fetch c.bodyType "
                        + "join fetch c.model "
                        + "join fetch c.brand "
                        + "where c.brand.name = :brandName")
                .setParameter("brandName", brandName)
                .list());
    }
}
