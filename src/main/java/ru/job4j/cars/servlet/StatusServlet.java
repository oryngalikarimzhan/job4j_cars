package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.store.implementations.PostStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("sold").equals("false")) {
            Post post = PostStore.instOf().findPostById(Integer.parseInt(req.getParameter("id")));
            post.setStatus(true);
            PostStore.instOf().save(post);
        } else {
            Post post = PostStore.instOf().findPostById(Integer.parseInt(req.getParameter("id")));
            post.setStatus(false);
            PostStore.instOf().save(post);
        }
        resp.sendRedirect(req.getHeader("referer"));
    }
}
