package io.javaclasses.filehub.api.getRootFolderView;

import io.javaclasses.filehub.api.SystemView;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.UserNotLoggedInException;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * The {@link SystemView} in the application that handles {@link RootFolderId} query.
 */
public class GetRootFolderId implements SystemView<RootFolderId, FolderId> {

    private final UserStorage userStorage;

    /**
     * Creates instance of {@link GetRootFolderId} class.
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
     * @throws UserNotLoggedInException if {@link UserStorage} does not contain specific {@link UserId}.
     */
    @Override
    public FolderId process(RootFolderId query) {

        checkNotNull(query);

        LoggedInUserRecord loggedInUser = query.loggedInUser();
        Optional<UserRecord> record = userStorage.get(loggedInUser.userId());

        if (!record.isPresent()) {
            throw new UserNotLoggedInException(format("User with %s is not logged in.", loggedInUser.userId()));
        }
        return getRootFolderId(record.get());
    }

    /**
     * Returns user root folder id.
     *
     * @param user user record.
     * @return root folder id.
     */
    private FolderId getRootFolderId(UserRecord user) {
        return user.rootFolderId();
    }
}
