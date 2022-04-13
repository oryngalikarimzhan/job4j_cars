package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Post> posts = (ArrayList) PsqlStore.instOf().findAllActivePosts();
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}