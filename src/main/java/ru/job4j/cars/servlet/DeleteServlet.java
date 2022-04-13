package ru.job4j.cars.servlet;

import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        PsqlStore.instOf().deletePost(Integer.valueOf(id));
        req.getRequestDispatcher("/photoDelete.do").include(req, resp);
        resp.sendRedirect(req.getHeader("referer"));

    }
}
