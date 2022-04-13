package ru.job4j.cars.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class PhotoDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("id");
            File deletingFile = null;
            for (File file : new File(Link.get().getProperty("images.url")).listFiles()) {
                String fileName = file.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                if (name.equals(file.getName()) || name.equals(fileName)) {
                    deletingFile = file;
                    deletingFile.delete();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getHeader("referer"));
    }
}