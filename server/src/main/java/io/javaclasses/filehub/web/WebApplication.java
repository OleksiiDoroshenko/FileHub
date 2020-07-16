package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileDataStorage;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileStorage;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.routes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;

import static spark.Spark.after;
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
    private static FileStorage fileStorage;
    private static LoggedInUsersStorage loggedInUsersStorage;
    private static FileDataStorage fileDataStorage;

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
        fileStorage = new FileStorage();
        fileDataStorage = new FileDataStorage();
    }

    private static void registerRoutes() {
        Filter filter = new AuthenticationFilter(loggedInUsersStorage);
        Filter multiPartFilter = new MultiPartAuthenticationFilter(loggedInUsersStorage);

        post("/register", new RegistrationRoute(userStorage, folderStorage));
        post("/login", new LogInRoute(userStorage, loggedInUsersStorage));

        before("/folder/root", filter);
        get("/folder/root", new GetRootFolderRoute(userStorage));

        before("/folder/:id/content", filter);
        get("/folder/:id/content", new GetFolderContentRoute(folderStorage, fileStorage));

        before("/folder/:id/folder", filter);
        post("/folder/:id/folder", new CreateFolderRoute(folderStorage));

        before("/folder/:id/file", multiPartFilter);
        post("/folder/:id/file", "multipart/form-data",
                new FileUploadingRoute(fileStorage, fileDataStorage, folderStorage));


        after((request, response) -> CurrentUser.clear());
    }
}
