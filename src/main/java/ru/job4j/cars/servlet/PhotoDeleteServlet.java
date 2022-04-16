package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Post;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PhotoDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("img");
            String postId = req.getParameter("postId");
            List<String> images = (List<String>) req.getAttribute("images");

            if (postId != null) {
                Post post = PsqlStore.instOf().findPostById(Integer.parseInt(postId));
                post.deleteImage(name);
                PsqlStore.instOf().save(post);
            }
            if (name != null) {
                deleteFile(name);
            }
            if (images != null) {
                for (String img : images) {
                    deleteFile(img);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getHeader("referer"));
    }

    private void deleteFile(String name) {
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
    }
}

