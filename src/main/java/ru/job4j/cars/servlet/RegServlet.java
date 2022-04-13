package ru.job4j.cars.servlet;

import ru.job4j.cars.model.User;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        User user = PsqlStore.instOf().findByEmail(email);
        if (user == null) {
            PsqlStore.instOf().save(
                   User.of(req.getParameter("name"), email, req.getParameter("password"))
            );
            req.setAttribute("message", "Вы успешно зарегистрированы");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Пользователь с таким email адресом уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
