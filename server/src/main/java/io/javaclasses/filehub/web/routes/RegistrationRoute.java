package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import io.javaclasses.filehub.web.deserializers.RegisterUserDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.eclipse.jetty.server.Response.*;

/**
 * The {@link Route} that handles user registration request.
 */
public class RegistrationRoute implements Route {

    private final UserRecordStorage userStorage;
    private final Logger logger = LoggerFactory.getLogger(RegistrationRoute.class);
    private Gson parser;

    /**
     * Returns instance of {@link RegistrationRoute} class.
     *
     * @param userStorage - user storage.
     */
    public RegistrationRoute(UserRecordStorage userStorage) {
        this.userStorage = checkNotNull(userStorage);
        parser = createJsonParser();
    }

    /**
     * Handles new user registration request.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return server response body.
     * @throws Exception if something went wrong.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        checkNotNull(request);
        checkNotNull(response);

        if (logger.isDebugEnabled()) {
            logger.debug("POST /register method was called with " + request.body() + ".");
        }

        response.type("application/json");
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("trying to register user" + request.body() + " .");
            }

            RegisterUser registerUser = parser.fromJson(request.body(), RegisterUser.class);
            UserId userId = new Registration(userStorage).handle(registerUser);

            if (logger.isDebugEnabled()) {
                logger.debug("User registration completed successfully. User's " + userId + ".");
            }

            response.status(SC_ACCEPTED);
            return "Success";


        } catch (UserAlreadyExistsException e) {

            response.status(SC_UNAUTHORIZED);
            return e.getMessage();
        } catch (Exception e) {

            response.status(SC_BAD_REQUEST);
            return e.getMessage();
        }
    }

    /**
     * Creates json parser for {@link RegisterUser}.
     *
     * @return created json parser.
     */
    private Gson createJsonParser() {
        return new GsonBuilder()
                .registerTypeAdapter(RegisterUser.class, new RegisterUserDeserializer())
                .create();
    }
}
