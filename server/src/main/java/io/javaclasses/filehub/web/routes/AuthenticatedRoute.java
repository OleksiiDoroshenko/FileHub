package io.javaclasses.filehub.web.routes;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import spark.Route;

/**
 * The abstract base {@link Route} that provides current logged in user.
 */
public abstract class AuthenticatedRoute implements Route {
    /**
     * Checks if {@link CurrentUser} contain logged in user.
     *
     * @return logged in user.
     * @throws UserNotLoggedInException if there is no logged in user in {@link CurrentUser}.
     */
    LoggedInUserRecord getLoggedInUser() {
        if (!CurrentUser.isPresent()) {
            throw new UserNotLoggedInException("User is not logged in the system.");
        }
        return CurrentUser.get();
    }

    boolean isLoggedInUserPresent() {
        return CurrentUser.isPresent();
    }
}
