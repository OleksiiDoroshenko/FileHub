package io.javaclasses.filehub.api.getRootFolderView;

import io.javaclasses.filehub.api.SystemView;
import io.javaclasses.filehub.api.logInProcess.UserNotRegisteredException;
import io.javaclasses.filehub.storage.folderStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * The {@link SystemView} in the application that handles {@link RootFolderId} query.
 */
public class GetRootFolderId implements SystemView<RootFolderId, FolderId> {

    private final UserStorage userStorage;

    /**
     * Returns instance of {@link GetRootFolderId} class.
     *
     * @param storage user storage.
     */
    public GetRootFolderId(UserStorage storage) {
        this.userStorage = checkNotNull(storage);
    }

    /**
     * Handles {@link RootFolderId} query.
     *
     * @param query command to be processed.
     * @return root folder id.
     * @throws UserNotRegisteredException if {@link UserStorage} does not contains specific {@link UserId}.
     */
    @Override
    public FolderId handle(RootFolderId query) {

        checkNotNull(query);

        LoggedInUserRecord loggedInUser = query.loggedInUser();
        Optional<UserRecord> record = userStorage.get(loggedInUser.userId());

        if (!record.isPresent()) {
            throw new UserNotRegisteredException(format("User with %s is not registered.", loggedInUser.userId()));
        }
        return record.get().rootFolder();
    }
}
