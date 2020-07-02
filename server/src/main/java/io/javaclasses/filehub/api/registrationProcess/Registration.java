package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Process in the application that handles {@link RegisterUser} command.
 */
@Immutable
public class Registration implements SystemProcess<RegisterUser, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final UserRecordStorage storage;


    /**
     * Returns instance of {@link Registration} class.
     *
     * @param userStorage user storage.
     */
    public Registration(UserRecordStorage userStorage) {
        this.storage = checkNotNull(userStorage);
    }

    /**
     * Handles {@link RegisterUser} command.
     *
     * @param registerUser user credentials.
     * @return registered user.
     * @throws UserAlreadyExistsException if user with provided in parameters
     *                                    command already exists in {@link UserRecordStorage}.
     */
    @Override
    public UserId handle(RegisterUser registerUser) {
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to register new user. Login: " + registerUser.loginName().value() + ".");
        }

        checkNotNull(registerUser);

        LoginName loginName = registerUser.loginName();

        if (storage.get(loginName).isPresent()) {

            if (logger.isErrorEnabled()) {
                logger.error("User with the same login already exists. Login: " + loginName.value() + ".");
            }

            throw new UserAlreadyExistsException("User with the same name already exists.");
        }
        String passwordHash = PasswordHasher.getHash(registerUser.password());
        UserId id = new UserId(storage.generateId());
        UserRecord record = new UserRecord(id, loginName, passwordHash);

        return storage.add(record);
    }
}
