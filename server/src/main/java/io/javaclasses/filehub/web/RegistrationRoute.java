package io.javaclasses.filehub.web;

import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegistrationRoute implements Route {

    private final UserStorage userStorage;
    private final Logger logger = LoggerFactory.getLogger(RegistrationRoute.class);
    private UserCredentialsDeserializer serializer = new UserCredentialsDeserializer();

    public RegistrationRoute(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        logger.debug("POST /register method was called with " + request.body() + ".");
        try {
            logger.debug("trying to register user" + request.body() + " .");
            UserCredentials userCredentials = serializer.deserialize(request.body());
            RegisterUser registerUser = new RegisterUser(userCredentials);
            UserId userId = new Registration(userStorage).handle(registerUser);
            logger.debug("User registration completed successfully. User's " + userId + ".");
            return 200;
        } catch (InvalidUserCredentialsException | UserAlreadyExistsException e) {

            logger.error("Error: " + e.getMessage());
            response.status(400);
            response.body(e.getMessage());
            return response;
        }
    }
}
