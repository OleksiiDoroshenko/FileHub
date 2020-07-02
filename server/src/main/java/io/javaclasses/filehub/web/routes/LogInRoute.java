package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import io.javaclasses.filehub.api.logInProcess.LogInUser;
import io.javaclasses.filehub.api.logInProcess.LoggingIn;
import io.javaclasses.filehub.api.logInProcess.UserNotRegisteredException;
import io.javaclasses.filehub.storage.tokenStorage.TokenStorage;
import io.javaclasses.filehub.storage.tokenStorage.TokenValue;
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
import static javax.servlet.http.HttpServletResponse.*;

/**
 * The {@link Route} that handles user log in request.
 */
public class LogInRoute implements Route {

    private final UserStorage userStorage;
    private final TokenStorage tokenStorage;
    private final Logger logger = LoggerFactory.getLogger(RegistrationRoute.class);
    private Gson parser;

    /**
     * Returns instance of {@link LogInRoute} class.
     *
     * @param userStorage - user storage.
     */
    public LogInRoute(UserStorage userStorage, TokenStorage tokenStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.tokenStorage = checkNotNull(tokenStorage);
        parser = createJsonParser();
    }

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

            LogInUser logInUser = parser.fromJson(request.body(), LogInUser.class);
            TokenValue tokenValue = new LoggingIn(userStorage, tokenStorage).handle(logInUser);

            if (logger.isDebugEnabled()) {
                logger.debug("User logging in completed successfully. User's token" + tokenValue.value() + ".");
            }

            response.status(SC_ACCEPTED);

            return new Gson().toJson(tokenValue);

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
