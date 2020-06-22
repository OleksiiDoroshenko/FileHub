package io.javaclasses.filehub.web;

import io.javaclasses.filehub.api.registrationProcess.Register;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

/**
 * Contains main method for starting the server.
 */
public class WebApp {

    private static UserStorage userStorage;
    private static RegisterCommandDeserializer serializer;
    private static final Logger logger = LoggerFactory.getLogger(WebApp.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {

        port(PORT);
        logger.info("Start web server with " + PORT + ".");
        userStorage = new UserStorage();
        serializer = new RegisterCommandDeserializer();

        staticFiles.location("/webclient");

        post("/register", (req, res) -> {
            logger.debug("POST /register method was called with " + req.body() + ".");
            try {
                logger.debug("trying to register user" + req.body() + " .");
                Register json = serializer.deserialize(req.body());
                UserId userId = new Registration(userStorage).handle(json);
                logger.debug("User registration completed successfully. User's " + userId + ".");
                return 200;
            } catch (InvalidUserDataException | UserAlreadyExistsException e) {

                logger.error("Error: " + e.getMessage());
                res.status(400);
                res.body(e.getMessage());
                return res;
            }
        });


    }
}
