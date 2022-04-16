package ru.job4j.cars.servlet;

import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        List<String> images = PsqlStore.instOf().findPostById(id).getImage();
        req.setAttribute("images", images);
        req.getRequestDispatcher("/photoDelete.do").include(req, resp);
        PsqlStore.instOf().deletePost(Integer.valueOf(id));
        resp.sendRedirect(req.getHeader("referer"));
    }
}
