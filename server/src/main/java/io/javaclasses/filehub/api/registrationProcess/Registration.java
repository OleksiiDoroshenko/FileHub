package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
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
     * Creates instance of {@link Registration} class.
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
        FolderId rootFileSystemItemId = createFolderId();
        UserRecord record = createUserRecord(loginName, passwordHash, id, rootFileSystemItemId);
        createAndAddUserRootFolder(record);

        return userStorage.add(record);
    }

    /**
     * Creates and adds to the {@link FolderStorage} new record for user root folder.
     *
     * @param user owner of the root folder.
     */
    private void createAndAddUserRootFolder(UserRecord user) {
        FolderId id = user.rootFolderId();
        FileSystemItemName name = new FileSystemItemName("root");
        UserId ownerId = user.id();
        FolderRecord rootFolder = new FolderRecord(id, name, null, ownerId);

        folderStorage.add(rootFolder);
    }

    /**
     * Creates new user record.
     *
     * @param loginName            user login name.
     * @param passwordHash         user password hash.
     * @param id                   user identifier.
     * @param rootFileSystemItemId user root folder id.
     * @return user record.
     */
    private UserRecord createUserRecord(LoginName loginName, String passwordHash, UserId id,
                                        FolderId rootFileSystemItemId) {
        return new UserRecord(id, loginName, passwordHash, rootFileSystemItemId);
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
