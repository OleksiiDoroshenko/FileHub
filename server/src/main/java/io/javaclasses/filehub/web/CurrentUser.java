package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;

public class CurrentUser {

    private static ThreadLocal<LoggedInUserRecord> user = new ThreadLocal<>();

    public static void set(LoggedInUserRecord userRecord) {
        user.set(userRecord);
    }

    public static LoggedInUserRecord get() {
        return user.get();
    }
}
