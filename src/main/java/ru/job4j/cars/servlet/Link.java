package ru.job4j.cars.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Link {
    public static Properties get() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Link.class.getClassLoader()
                                .getResourceAsStream("link.properties")
                )
        )) {
            cfg.load(io);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cfg;
    }
}