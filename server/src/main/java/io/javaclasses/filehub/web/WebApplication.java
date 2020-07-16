package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileContentStorage;
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
    private UserStorage userStorage;
    private FolderStorage folderStorage;
    private FileStorage fileStorage;
    private LoggedInUsersStorage loggedInUsersStorage;
    private FileContentStorage fileContentStorage;

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

    private void initStorage() {
        userStorage = new UserStorage();
        loggedInUsersStorage = new LoggedInUsersStorage();
        folderStorage = new FolderStorage();
        fileStorage = new FileStorage();
        fileContentStorage = new FileContentStorage();
    }

    private void registerRoutes() {
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
                new FileUploadingRoute(fileStorage, fileContentStorage, folderStorage));


        after((request, response) -> CurrentUser.clear());
    }
}
