package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.folderStorage.FolderId;
import io.javaclasses.filehub.storage.folderStorage.FolderStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Process in the application that handles {@link RegisterUser} command.
 */
public class Registration implements SystemProcess<RegisterUser, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final UserStorage userStorage;
    private final FolderStorage folderStorage;


    /**
     * Returns instance of {@link Registration} class.
     *
     * @param userStorage   user storage.
     * @param folderStorage folder storage.
     */
    public Registration(UserStorage userStorage, FolderStorage folderStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.folderStorage = checkNotNull(folderStorage);
    }

    /**
     * Handles {@link RegisterUser} command.
     *
     * @param registerUser user credentials.
     * @return registered user.
     * @throws UserAlreadyExistsException if user with provided in parameters
     *                                    command already exists in {@link UserStorage}.
     */
    @Override
    public UserId handle(RegisterUser registerUser) {
        if (logger.isDebugEnabled()) {
            logger.debug("Trying to register new user. Login: " + registerUser.loginName().value() + ".");
        }

        checkNotNull(registerUser);

        LoginName loginName = registerUser.loginName();

        if (userStorage.get(loginName).isPresent()) {

            if (logger.isErrorEnabled()) {
                logger.error("User with the same login already exists. Login: " + loginName.value() + ".");
            }

            throw new UserAlreadyExistsException("User with the same name already exists.");
        }
        String passwordHash = createPasswordHash(registerUser.password());
        UserId id = createUserId();
        FolderId rootFolderId = createFolderId();
        UserRecord record = createUserRecord(loginName, passwordHash, id, rootFolderId);

        return userStorage.add(record);
    }

    /**
     * Creates new user record.
     *
     * @param loginName    user login name.
     * @param passwordHash user password hash.
     * @param id           user identifier.
     * @param rootFolderId user root folder id.
     * @return user record.
     */
    private UserRecord createUserRecord(LoginName loginName, String passwordHash, UserId id, FolderId rootFolderId) {
        return new UserRecord(id, loginName, passwordHash, rootFolderId);
    }

    /**
     * Creates new folder id.
     *
     * @return folder id.
     */
    private FolderId createFolderId() {
        return new FolderId(folderStorage.generateId());
    }

    /**
     * Creates new user id.
     *
     * @return user id.
     */
    private UserId createUserId() {
        return new UserId(userStorage.generateId());
    }

    /**
     * Hashes user password using {@link PasswordHasher}.
     *
     * @param password user password.
     * @return password hash.
     */
    private String createPasswordHash(Password password) {
        return PasswordHasher.getHash(password);
    }
}
