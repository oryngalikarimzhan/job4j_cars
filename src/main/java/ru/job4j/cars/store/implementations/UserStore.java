package ru.job4j.cars.store.implementations;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.interfaces.IUser;

public class UserStore implements IUser {

    private final DbSessionExecutor sessionExecutor = DbSessionExecutor.instOf();
    private static final class Holder {
        private static final UserStore INST = new UserStore();
    }

    public static UserStore instOf() {
        return UserStore.Holder.INST;
    }

    @Override
    public User save(User user) {
        return sessionExecutor.tx(session -> {
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
        return (User) sessionExecutor.tx(session -> session.createQuery("from User where email = :email")
                .setParameter("email", email).uniqueResult()
        );
    }

    @Override
    public boolean deleteUser(String email) {
        return sessionExecutor.tx(session -> {
            User user = findByEmail(email);
            for (Post post : PostStore.instOf().findUserPostsById(user.getId())) {
                session.delete(post);
            }
            session.delete(user);
            return true;
        });
    }
}
