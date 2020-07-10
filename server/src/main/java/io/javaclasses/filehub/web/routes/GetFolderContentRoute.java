package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.getFolderContentView.FolderContent;
import io.javaclasses.filehub.api.getFolderContentView.FolderContentDTO;
import io.javaclasses.filehub.api.getFolderContentView.GetFolderContent;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileStorage;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.web.CurrentUser;
import io.javaclasses.filehub.web.FileSystemItemNotFoundException;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * The {@link Route} that handles get folder content client requests.
 */
public class GetFolderContentRoute extends AuthenticatedRoute {

    private static final Logger logger = LoggerFactory.getLogger(GetFolderContentRoute.class);
    private final FolderStorage folderStorage;
    private final FileStorage fileStorage;

    /**
     * Returns instance of {@link GetFolderContentRoute} class.
     *
     * @param folderStorage folder storage.
     * @param fileStorage   file storage.
     */
    public GetFolderContentRoute(FolderStorage folderStorage, FileStorage fileStorage) {
        this.folderStorage = checkNotNull(folderStorage);
        this.fileStorage = checkNotNull(fileStorage);
    }

    /**
     * Handles client get folder content request.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return server response body.
     */
    @Override
    public Object handle(Request request, Response response) {

        checkNotNull(request);
        checkNotNull(response);

        try {

            if (logger.isDebugEnabled()) {
                logger.debug("Trying to get folder content.");
            }

            if (!isLoggedInUserPresent()) {

                if (logger.isDebugEnabled()) {
                    logger.debug("Logged in user was not found.");
                }

                throw new UserNotLoggedInException("Logged in user was not found.");
            }
            FileSystemItemId folderId = getFolderId(request);
            checkId(folderId);

            LoggedInUserRecord loggedInUser = getLoggedInUser();

            GetFolderContent view = createView();
            FolderContent query = createQuery(folderId, loggedInUser);

            FolderContentDTO content = view.handle(query);

            if (logger.isDebugEnabled()) {
                logger.debug("Getting folder content was completed successfully.");
            }

            response.status(SC_OK);
            return new Gson().toJson(content);

        } catch (UserNotLoggedInException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_UNAUTHORIZED);
            return e.getMessage();

        } catch (FileSystemItemNotFoundException e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_NOT_FOUND);
            return e.getMessage();

        } catch (Exception e) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Error %s occurred. With message: %s.", e.getClass(), e.getMessage()));
            }

            response.status(SC_INTERNAL_SERVER_ERROR);
            return "Internal Server Error.";
        }
    }

    /**
     * Checks if FileHub application storage has folder with required identifier.
     *
     * @param folderId folder identifier.
     * @throws FileSystemItemNotFoundException if storage does not contain folder with required identifier.
     */
    private void checkId(FileSystemItemId folderId) {
        Optional<FolderRecord> record = folderStorage.get(folderId);

        if (!record.isPresent()) {
            throw new FileSystemItemNotFoundException(format("Folder with this id %s is not found.", folderId.value()));
        }

    }

    /**
     * Creates new instance {@link FolderContent} query.
     *
     * @param folderId     folder identifier.
     * @param loggedInUser logged in user.
     * @return created query.
     */
    private FolderContent createQuery(FileSystemItemId folderId, LoggedInUserRecord loggedInUser) {
        return new FolderContent(folderId, loggedInUser);
    }

    /**
     * Creates new instance of {@link GetFolderContent} class.
     *
     * @return created view.
     */
    private GetFolderContent createView() {
        return new GetFolderContent(folderStorage, fileStorage);
    }

    /**
     * Parses folder identifier from client request.
     *
     * @param request client HTTP request.
     * @return folder identifier.
     */
    private FileSystemItemId getFolderId(Request request) {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to parse folder id.");
        }

        String id = request.pathInfo().split("/")[2];

        if (logger.isDebugEnabled()) {
            logger.debug(format("Folder id was parsed successfully. Id: %s", id));
        }
        return new FileSystemItemId(id);
    }
}
