package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Represents FileHub web application context.
 * Configure server settings and maps request routes.
 */
public class WebApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);
    private static final int PORT = 8080;
    private static UserRecordStorage userStorage;

    public static void main(String[] args) {
        new WebApplication().start();
    }

    private void start() {

        port(PORT);
        staticFiles.location("/webclient");

        if (logger.isInfoEnabled()) {
            logger.info("Start web server with " + PORT + ".");
        }

        initStorage();
        registerRoutes();
    }

    private static void initStorage() {
        userStorage = new UserRecordStorage();
    }

    private static void registerRoutes() {
        post("/register", new RegistrationRoute(userStorage));
    }
}
