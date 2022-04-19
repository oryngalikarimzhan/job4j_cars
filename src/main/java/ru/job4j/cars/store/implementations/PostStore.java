package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.interfaces.IPost;

import java.util.Collection;

public class PostStore implements IPost  {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final PostStore INST = new PostStore();
    }

    public static PostStore instOf() {
        return PostStore.Holder.INST;
    }

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
        return sessionExecutor.tx(session -> {
            Car carInDb = CarStore.instOf().findBySN(post.getCar().getSerialNumber());
            User userInDb = UserStore.instOf().findByEmail(post.getUser().getEmail());
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
        return sessionExecutor.tx(session -> {
            session.update(post);
            return findPostById(post.getId());
        });
    }

    @Override
    public Post findPostById(int id) {
        return (Post) sessionExecutor.tx(
                session -> session.createQuery(
                                "select distinct p "
                                        + "from Post p "
                                        + "left join fetch p.image "
                                        + "join fetch p.car c "
                                        + "left join fetch c.drivers "
                                        + "where p.id = :id")
                        .setParameter("id", id)
                        .uniqueResult()
        );
    }

    @Override
    public Collection<Post> findUserPostsById(int id) {
        return sessionExecutor.tx(
                session -> session.createQuery(
                        "select distinct p "
                                + "from Post p "
                                + "left join fetch p.image "
                                + "join fetch p.car c "
                                + "left join fetch c.drivers "
                                + "where user_id = :id "
                                + "order by p.id asc"
                ).setParameter("id", id)
                .list()
        );
    }

    @Override
    public Collection<Post> findAllPosts() {
        return sessionExecutor.tx(
                session -> session.createQuery(
                                "select distinct p "
                                + "from Post p "
                                + "left join fetch p.image "
                                + "join fetch p.car c "
                                + "left join fetch c.drivers "
                                + "order by p.id asc")
                .list()
        );
    }

    @Override
    public Collection<Post> findAllActivePosts() {
        return sessionExecutor.tx(
                session -> session.createQuery(
                        "select distinct p "
                                + "from Post p "
                                + "left join fetch p.image "
                                + "join fetch p.car c "
                                + "left join fetch c.drivers "
                                + "where sold = false "
                                + "order by p.id asc")
                .list()
        );
    }

    @Override
    public boolean deletePost(int id) {
        return sessionExecutor.tx(session -> {
            session.delete(findPostById(id));
            return true;
        });
    }
}
