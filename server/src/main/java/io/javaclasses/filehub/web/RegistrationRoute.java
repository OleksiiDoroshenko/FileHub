package io.javaclasses.filehub.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import jdk.nashorn.internal.ir.annotations.Immutable;
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
@Immutable
public class RegistrationRoute implements Route {

    private final UserRecordStorage userStorage;
    private final Logger logger = LoggerFactory.getLogger(RegistrationRoute.class);
    private Gson parser;

    public RegistrationRoute(UserRecordStorage userStorage) {
        this.userStorage = checkNotNull(userStorage);
        parser = createJsonParser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        checkNotNull(request);
        checkNotNull(response);

        logger.debug("POST /register method was called with " + request.body() + ".");
        try {

            logger.debug("trying to register user" + request.body() + " .");

            RegisterUser registerUser = parser.fromJson(request.body(), RegisterUser.class);
            UserId userId = new Registration(userStorage).handle(registerUser);

            logger.debug("User registration completed successfully. User's " + userId + ".");
            return SC_ACCEPTED;

        } catch (InvalidUserCredentialsException e) {

            return createErrorResponse(response, e, SC_BAD_REQUEST);
        } catch (UserAlreadyExistsException e) {

            return createErrorResponse(response, e, SC_UNAUTHORIZED);
        }
    }

    private Object createErrorResponse(Response response, Exception exception, int responseStatus) {

        logger.error("Error: " + exception.getMessage());

        response.type("application/json");
        response.status(responseStatus);
        return exception.getMessage();
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
