package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.folderStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.routes.GetRootFolderRoute;
import io.javaclasses.filehub.web.routes.LogInRoute;
import io.javaclasses.filehub.web.routes.RegistrationRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

/**
 * Represents FileHub web application context.
 * Configure server settings and maps request routes.
 */
public class WebApplication {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);
    private static final int PORT = 8080;
    private static UserStorage userStorage;
    private static FolderStorage folderStorage;
    private static LoggedInUsersStorage loggedInUsersStorage;

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
        userStorage = new UserStorage();
        loggedInUsersStorage = new LoggedInUsersStorage();
        folderStorage = new FolderStorage();
    }

    private static void registerRoutes() {
        Filter filter = new AuthenticationFilter(loggedInUsersStorage);

        post("/register", new RegistrationRoute(userStorage, folderStorage));
        post("/login", new LogInRoute(userStorage, loggedInUsersStorage));

        before("/folder/root", filter);
        get("/folder/root", new GetRootFolderRoute(userStorage));
    }
}
