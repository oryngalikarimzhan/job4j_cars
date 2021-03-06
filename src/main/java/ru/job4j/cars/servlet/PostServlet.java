package ru.job4j.cars.servlet;

import ru.job4j.cars.model.*;
import ru.job4j.cars.store.implementations.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 3,
        maxFileSize = 1024 * 1024 * 15,
        maxRequestSize = 1024 * 1024 * 100
)
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
        Engine engine = EngineStore.instOf().save(
                Engine.of(
                        req.getParameter("engine"),
                        Integer.parseInt(req.getParameter("mileage")),
                        Integer.parseInt(req.getParameter("volume")),
                        req.getParameter("fuel-type")
                )
        );
        Car car = CarStore.instOf().findBySN(serialNumber);
        if (car == null) {
            car = CarStore.instOf().save(
                    Car.of(
                            serialNumber,
                            regNumber,
                            CategoryStore.instOf().findCategoryById(Integer.parseInt(req.getParameter("category"))),
                            BrandStore.instOf().findBrandById(Integer.parseInt(req.getParameter("brand"))),
                            ModelStore.instOf().findModelById(Integer.parseInt(req.getParameter("model"))),
                            BodyTypeStore.instOf().findBodyTypeById(Integer.parseInt(req.getParameter("body-type"))),
                            engine,
                            req.getParameter("year")
                    )
            );
        } else {
            car.setEngine(engine);
            car.setRegistrationNumber(regNumber);
        }
        Post post = PostStore.instOf().save(
            Post.of(
                    req.getParameter("description"),
                    Integer.parseInt(req.getParameter("price")),
                    req.getParameter("phone"),
                    (User) req.getSession().getAttribute("user"),
                    car
            )
        );
        int postId = post.getId();
        int i = 1;
        List<String> images = new ArrayList<>();
        for (Part part : req.getParts()) {
            String contentType = part.getContentType();
            if (contentType != null && contentType.contains("image")) {
                String initialImageName = part.getSubmittedFileName();
                String fileType = initialImageName.substring(initialImageName.lastIndexOf(".") + 1);
                String newImageName = postId + "-" + i++ + "." + fileType;
                part.write(Link.get().getProperty("images.url") + newImageName);
                images.add(newImageName);
            }
        }
        if (!images.isEmpty()) {
            images.forEach(image -> post.addImage(image));
            PostStore.instOf().save(post);
        }
        resp.sendRedirect(req.getContextPath() + "/user.do");
    }
}
