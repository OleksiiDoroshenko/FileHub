package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.getRootFolderView.GetRootFolderId;
import io.javaclasses.filehub.api.getRootFolderView.RootFolderId;
import io.javaclasses.filehub.api.logInProcess.UserNotRegisteredException;
import io.javaclasses.filehub.storage.folderStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * The {@link Route} that handles get root folder requests.
 */
public class GetRootFolderRoute implements Route {

    private final static Logger logger = LoggerFactory.getLogger(GetRootFolderRoute.class);
    private final UserStorage userStorage;

    /**
     * Returns instance of {@link GetRootFolderRoute} class.
     *
     * @param userStorage user storage.
     */
    public GetRootFolderRoute(UserStorage userStorage) {
        this.userStorage = checkNotNull(userStorage);
    }

    /**
     * Handles new user get root folder request.
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

        if (logger.isInfoEnabled()) {
            logger.info(format("Request: %s with body: %s.",
                    request.pathInfo(), request.body()));
        }

        response.type("application/json");
        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Trying to get user root folder id.");
            }

            LoggedInUserRecord loggedInUserRecord = getLoggedInUser();
            RootFolderId query = createQuery(loggedInUserRecord);
            GetRootFolderId view = createView();

            FolderId id = view.handle(query);

            if (logger.isDebugEnabled()) {
                logger.debug(format("Getting user root folder was completed successfully. Root folder id: %s.",
                        id.value()));
            }

            response.status(SC_OK);
            return new Gson().toJson(id);

        } catch (UserNotRegisteredException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_UNAUTHORIZED);
            return e.getMessage();

        } catch (NullPointerException e) {

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
            return "Internal Server Error.";
        }

    }

    /**
     * @return logged in user.
     */
    private LoggedInUserRecord getLoggedInUser() {
        if (!CurrentUser.isPresent()) {
            throw new UserNotLoggedInException("User is not logged in the system.");
        }
        return CurrentUser.get();
    }

    /**
     * Crates new instance of {@link GetRootFolderId} class.
     *
     * @return created instance.
     */
    private GetRootFolderId createView() {
        return new GetRootFolderId(userStorage);
    }

    /**
     * Crates new instance of {@link RootFolderId} class.
     *
     * @param loggedInUserRecord logged in user.
     * @return created instance.
     */
    private RootFolderId createQuery(LoggedInUserRecord loggedInUserRecord) {
        return new RootFolderId(loggedInUserRecord);
    }
}
