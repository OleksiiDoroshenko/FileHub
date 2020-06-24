package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Precess in the application that handles {@link RegisterUser} command.
 */
public class Registration implements SystemProcess<RegisterUser, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final UserRecordStorage storage;


    /**
     * Returns instance of {@link Registration} class.
     *
     * @param userStorage - user storage.
     */
    public Registration(UserRecordStorage userStorage) {
        checkNotNull(userStorage);
        this.storage = userStorage;
    }

    /**
     * Handles {@link RegisterUser} command.
     *
     * <p>
     * Throws {@link UserAlreadyExistsException}
     * if user with provided in parameters command login already exists in {@link UserRecordStorage}.
     * </p>
     *
     * @param registerUser - user credentials.
     * @return registered user.
     */
    @Override
    public UserId handle(RegisterUser registerUser) {
        logger.debug("Trying to register new user.");
        UserRecord userRecord = new UserRecord(registerUser.userCredentials());
        if (storage.containsUser(registerUser.userCredentials())) {

            logger.error("User with the same login already exists. Login: " + registerUser.userCredentials().login() + ".");
            throw new UserAlreadyExistsException("User with the same name already exists.");
        }
        return storage.add(userRecord);
    }
}
