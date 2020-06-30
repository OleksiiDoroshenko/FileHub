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
 * Precess in the application that handles {@link RegisterUser} command.
 */
@Immutable
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

        checkNotNull(registerUser);

        LoginName loginName = registerUser.loginName();

        logger.debug("Trying to register new user. Login: " + loginName + ".");
        if (storage.contains(loginName)) {

            logger.error("User with the same login already exists. Login: " + loginName + ".");
            throw new UserAlreadyExistsException("User with the same name already exists.");
        }
        String passwordHash = PasswordHasher.getHash(registerUser.password());
        UserRecord record = new UserRecord(storage.generateId(), loginName, passwordHash);

        return storage.add(record);
    }
}
