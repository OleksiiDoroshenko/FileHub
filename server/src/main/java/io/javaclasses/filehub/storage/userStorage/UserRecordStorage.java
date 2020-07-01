package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.LoginName;

import java.util.*;

/**
 * Storage for saving {@link UserRecord}.
 */
public class UserRecordStorage extends InMemoryRecordStorage<UserRecord, UserId> {

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

    /**
     * Generates {@link UserId}.
     *
     * @return user id.
     */
    public synchronized UserId generateId() {

        String id = UUID.randomUUID().toString();
        return new UserId(id);
    }

}
