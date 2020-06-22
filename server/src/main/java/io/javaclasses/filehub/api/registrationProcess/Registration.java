package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.Process;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides functionality for registration process in the system.
 */
public class Registration implements Process<Register, UserId> {
    private final UserStorage storage;
    private static final Logger logger = LoggerFactory.getLogger(Registration.class);

    /**
     * Returns instance of {@link Registration} class.
     *
     * @param userStorage - user storage.
     */
    public Registration(UserStorage userStorage) {
        checkNotNull(userStorage);
        this.storage = userStorage;
    }

    /**
     * Allows user to register.
     *
     * @param register - user credentials.
     * @return registered user.
     */
    @Override
    public UserId handle(Register register) {
        logger.debug("Trying to register new user.");
        UserRecord userRecord = new UserRecord(register.userCredentials());
        if (storage.contains(userRecord)) {

            logger.error("User with the same login already exists. Login: " + register.userCredentials().login() + ".");
            throw new UserAlreadyExistsException("User with the same name already exists.");
        }
        return storage.add(userRecord);
    }
}
