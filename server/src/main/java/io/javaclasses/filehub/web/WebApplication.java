package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Represents FileHub web application context. Configure server settings and maps request routes.
 */
public class WebApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);
    private static final int PORT = 8080;
    private static UserRecordStorage userStorage;

    public static void main(String[] args) {
        configureServer();
        registerRoutes();
    }

    private static void registerRoutes() {
        post("/register", new RegistrationRoute(userStorage));
    }

    private static void configureServer() {
        port(PORT);
        logger.info("Start web server with " + PORT + ".");
        userStorage = new UserRecordStorage();
        staticFiles.location("/webclient");
    }
}
