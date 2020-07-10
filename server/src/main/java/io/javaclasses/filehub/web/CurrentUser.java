package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;

/**
 * Provides current logged in user in the FilHub application.
 * <p>Thread safe.</p>
 */
public class CurrentUser {

    private static ThreadLocal<LoggedInUserRecord> user = new ThreadLocal<>();

    /**
     * Sets current logged in user.
     *
     * @param userRecord user to be set as logged in.
     */
    public static void set(LoggedInUserRecord userRecord) {
        user.set(userRecord);
    }

    /**
     * @return current logged in user.
     */
    public static LoggedInUserRecord get() {
        return user.get();
    }

    /**
     * Clears current logged in user.
     */
    public static void clear() {
        user.remove();
    }

    /**
     * Checks if there is logged in user.
     *
     * @return true if logged in user is present / false if it is not.
     */
    public static boolean isPresent() {
        return user.get() != null;
    }
}
