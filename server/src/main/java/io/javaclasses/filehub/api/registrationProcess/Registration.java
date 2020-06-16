package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;

/**
 * Provides functionality for registration process in the system.
 */
public class Registration {
    private final UserStorage storage;

    public Registration(UserStorage userStorage) {
        this.storage = userStorage;
    }

    /**
     * Allows user to register.
     *
     * @param register - user credentials.
     * @return registered user.
     */
    public UserId handle(Register register) {
        UserRecord userRecord = new UserRecord(register.getLogin(), register.getPassword());
        return storage.add(userRecord);
    }


}
