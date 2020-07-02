package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.storage.InMemoryRecordStorage;

import java.util.*;

/**
 * Storage for saving {@link UserRecord}.
 */
public class UserStorage extends InMemoryRecordStorage<UserRecord, UserId> {

    /**
     * Returns {@link Optional<UserRecord>} by {@link LoginName}.
     *
     * @param loginName user login name.
     * @return {@link Optional<UserRecord>}.
     */
    public Optional<UserRecord> get(LoginName loginName) {
        return all().stream().filter(item ->
                item.loginName().value().equals(loginName.value())).findAny();
    }

}
