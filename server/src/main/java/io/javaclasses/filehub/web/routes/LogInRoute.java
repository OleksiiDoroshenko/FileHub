package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import io.javaclasses.filehub.api.logInProcess.LogInUser;
import io.javaclasses.filehub.api.logInProcess.LoggingIn;
import io.javaclasses.filehub.api.logInProcess.UserNotRegisteredException;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import io.javaclasses.filehub.web.deserializers.LogInUserDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * The {@link Route} that handles user log in request.
 */
public class LogInRoute implements Route {

    private final static Logger logger = LoggerFactory.getLogger(LogInRoute.class);
    private final UserStorage userStorage;
    private final LoggedInUsersStorage loggedInUsersStorage;
    private final Gson parser = createJsonParser();

    /**
     * Returns instance of {@link LogInRoute} class.
     *
     * @param userStorage - user storage.
     */
    public LogInRoute(UserStorage userStorage, LoggedInUsersStorage loggedInUsersStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.loggedInUsersStorage = checkNotNull(loggedInUsersStorage);
    }

    /**
     * Handles new user log in request.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return server response body.
     */
    @Override
    public Object handle(Request request, Response response) {

        checkNotNull(request);
        checkNotNull(response);

        if (logger.isDebugEnabled()) {
            logger.debug("POST /login method was called.");
        }

        response.type("application/json");
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("trying to log in user" + request.body() + " .");
            }

            LogInUser logInUser = parseCommand(request);
            Token tokenValue = processCommand(logInUser);

            if (logger.isDebugEnabled()) {
                logger.debug("User logging in completed successfully. User's token" + tokenValue.value() + ".");
            }

            response.status(SC_ACCEPTED);

            return parser.toJson(tokenValue);

        } catch (UserNotRegisteredException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_CONFLICT);
            return e.getMessage();
        } catch (InvalidUserCredentialsException | JsonParseException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_BAD_REQUEST);
            return e.getMessage();
        } catch (Exception e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_INTERNAL_SERVER_ERROR);
            return "Internal server error.";
        }
    }

    private Token processCommand(LogInUser logInUser) {
        return new LoggingIn(userStorage, loggedInUsersStorage).handle(logInUser);
    }

    private LogInUser parseCommand(Request request) {
        return parser.fromJson(request.body(), LogInUser.class);
    }

    /**
     * Creates json parser for {@link LogInUser}.
     *
     * @return created json parser.
     */
    private Gson createJsonParser() {
        return new GsonBuilder()
                .registerTypeAdapter(LogInUser.class, new LogInUserDeserializer())
                .create();
    }

}
