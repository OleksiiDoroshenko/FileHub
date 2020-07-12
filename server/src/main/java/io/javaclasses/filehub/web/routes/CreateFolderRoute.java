package io.javaclasses.filehub.web.routes;

import io.javaclasses.filehub.api.folderCreationProcess.CreateFolder;
import io.javaclasses.filehub.api.folderCreationProcess.FolderCreation;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.NotFoundException;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * The {@link Route} that handles create new folder client requests.
 */
public class CreateFolderRoute extends AuthenticatedRoute {

    private static final Logger logger = LoggerFactory.getLogger(CreateFolderRoute.class);
    private final FolderStorage folderStorage;

    /**
     * Returns instance of {@link CreateFolderRoute} class.
     *
     * @param folderStorage folder storage.
     */
    public CreateFolderRoute(FolderStorage folderStorage) {

        this.folderStorage = checkNotNull(folderStorage);
    }

    /**
     * Handles a request from the client to create new folder.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return server response.
     */
    @Override
    public Object handle(Request request, Response response) {

        response.type("application/json");

        try {
            FileSystemItemId parentFolderId = getFolderId(request);
            LoggedInUserRecord userRecord = getLoggedInUser();

            FolderCreation process = createProcess();
            CreateFolder command = createCommand(parentFolderId, userRecord.userId());

            process.handle(command);
            response.status(SC_OK);
            return "Folder was created.";

        } catch (NotFoundException e) {

            return makeErrorResponse(response, e, SC_NOT_FOUND);

        } catch (UserNotLoggedInException e) {

            return makeErrorResponse(response, e, SC_UNAUTHORIZED);

        } catch (Exception e) {

            return makeErrorResponse(response, e, SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Makes server error response.
     *
     * @param response    HTTP response.
     * @param exception   exception that appeared in the application.
     * @param errorStatus response HTTP status.
     * @return response body.
     */
    private String makeErrorResponse(Response response, Exception exception, int errorStatus) {

        if (logger.isDebugEnabled()) {
            logger.debug(format("Error %s occurred. With message: %s.", exception.getClass(), exception.getMessage()));
        }

        response.status(errorStatus);

        return exception.getMessage();
    }

    /**
     * Creates new {@link CreateFolder} command.
     *
     * @param parentId an identifier of the future parent folder.
     * @param ownerId  an identifier of the future owner.
     * @return created command.
     */
    private CreateFolder createCommand(FileSystemItemId parentId, UserId ownerId) {

        return new CreateFolder(parentId, ownerId);
    }

    /**
     * Creates new {@link FolderCreation} process.
     *
     * @return created process.
     */
    private FolderCreation createProcess() {

        return new FolderCreation(folderStorage);
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

        String id = request.params(":id");

        if (logger.isDebugEnabled()) {
            logger.debug(format("Folder id was parsed successfully. Id: %s", id));
        }
        return new FileSystemItemId(id);
    }
}
