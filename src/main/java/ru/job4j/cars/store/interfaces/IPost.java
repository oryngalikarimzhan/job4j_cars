package ru.job4j.cars.store.interfaces;

import ru.job4j.cars.model.Post;

import java.util.Collection;

public interface IPost {
    Post save(Post post);
    Post findPostById(int id);
    Collection<Post> findUserPostsById(int id);
    Collection<Post> findAllPosts();
    Collection<Post> findAllActivePosts();
    boolean deletePost(int id);
}
