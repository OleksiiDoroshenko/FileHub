package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Filter;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static spark.Spark.halt;

/**
 * The {@link Filter} that checks if request contains user {@link Token}.
 */
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final LoggedInUsersStorage loggedInUsersStorage;
    private final ThreadLocal<LoggedInUserRecord> loggedInUser;

    /**
     * Returns instance of {@link AuthenticationFilter} class.
     *
     * @param loggedInUsersStorage logged in user storage.
     * @param loggedInUser         logged in user.
     */
    public AuthenticationFilter(LoggedInUsersStorage loggedInUsersStorage,
                                ThreadLocal<LoggedInUserRecord> loggedInUser) {

        this.loggedInUsersStorage = checkNotNull(loggedInUsersStorage);
        this.loggedInUser = checkNotNull(loggedInUser);
    }

    /**
     * Filters handled request by checking if request contains user {@link Token} in its header.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @throws Exception if something went wrong.
     */
    @Override
    public void handle(Request request, Response response) throws Exception {

        try {
            String token = request.headers("token");

            if (logger.isDebugEnabled()) {
                logger.debug(format("Trying to filter request: %s with token: %s.", request.pathInfo(), token));
            }

            Optional<LoggedInUserRecord> userRecord = getLoggedInUserRecord(token);

            if (!userRecord.isPresent()) {

                if (logger.isErrorEnabled()) {
                    logger.error(format("Users token has been expired or storage does not not contain id. Token: %s",
                            token));
                }

                halt(SC_UNAUTHORIZED, "Your token has been expired, please log in .");
            }

            if (logger.isDebugEnabled()) {
                logger.debug(format("Filtering completed successfully. Logged in user id: %s",
                        userRecord.get().userId()));
            }

            loggedInUser.set(userRecord.get());

        } catch (NullPointerException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            halt(SC_UNAUTHORIZED, "Please log in.");
        }

    }

    /**
     * Returns logged in user with passed in parameters token.
     *
     * @param token user token.
     * @return logged in user.
     */
    private Optional<LoggedInUserRecord> getLoggedInUserRecord(String token) {
        return loggedInUsersStorage.get(new Token(token));
    }
}
