package ru.job4j.cars.servlet;

import ru.job4j.cars.model.*;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String serialNumber = req.getParameter("serial-number");
        String regNumber = req.getParameter("reg-number");
        Engine engine = PsqlStore.instOf().save(
                Engine.of(
                        req.getParameter("engine"),
                        Integer.parseInt(req.getParameter("mileage")),
                        Integer.parseInt(req.getParameter("volume")),
                        req.getParameter("fuel")
                )
        );
        Car car = PsqlStore.instOf().findBySN(serialNumber);
        if (car == null) {
            car = PsqlStore.instOf().save(
                    Car.of(
                            serialNumber,
                            regNumber,
                            PsqlStore.instOf().findBrandById(Integer.parseInt(req.getParameter("brand"))),
                            PsqlStore.instOf().findModelById(Integer.parseInt(req.getParameter("model"))),
                            PsqlStore.instOf().findBodyTypeById(Integer.parseInt(req.getParameter("body-type"))),
                            engine,
                            req.getParameter("year")
                    )
            );
        } else {
            car.setEngine(engine);
            car.setRegistrationNumber(regNumber);
        }
        PsqlStore.instOf().save(
            Post.of(
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("price")),
                    req.getParameter("phone"),
                    (User) req.getSession().getAttribute("user"),
                    car
            )
        );
        resp.sendRedirect(req.getContextPath() + "/user.do");
    }

}
