package io.javaclasses.filehub.web.routes;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.folderCreationProcess.CreateFolder;
import io.javaclasses.filehub.api.folderCreationProcess.FolderCreation;
import io.javaclasses.filehub.api.folderCreationProcess.UserNotOwnerException;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.FolderNotFoundException;
import io.javaclasses.filehub.web.RequestId;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
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
     * Returns instance of {@link CreateFolderRoute} with set {@link FolderStorage}.
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

        if (logger.isInfoEnabled()) {
            logger.info("Trying to handle {} with body:{}.", request.pathInfo(), request.body());
        }

        response.type("application/json");

        try {
            FileSystemItemId parentFolderId = RequestId.parse(request);
            LoggedInUserRecord userRecord = getLoggedInUser();

            FolderCreation process = createProcess();
            CreateFolder command = createCommand(parentFolderId, userRecord.userId());

            FileSystemItemId folderId = process.handle(command);

            return makeSuccessResponse(response, folderId);

        } catch (FolderNotFoundException e) {

            return makeErrorResponse(response, e, SC_NOT_FOUND);

        } catch (UserNotLoggedInException e) {

            return makeErrorResponse(response, e, SC_UNAUTHORIZED);

        } catch (UserNotOwnerException e) {

            return makeErrorResponse(response, e, SC_CONFLICT);

        } catch (Exception e) {

            return makeErrorResponse(response, e, SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Makes server response with status '200' and created folder identifier as response body.
     *
     * @param response HTTP response.
     * @param folderId created folder identifier.
     * @return response body.
     */
    private String makeSuccessResponse(Response response, FileSystemItemId folderId) {

        if (logger.isInfoEnabled()) {
            logger.info("New folder was created successfully. New folder {}.", folderId);
        }

        response.status(SC_OK);
        return new Gson().toJson(folderId);
    }

    /**
     * Makes server error response with set error status and exception error message as body.
     *
     * @param response       HTTP response.
     * @param exception      exception that appeared in the application.
     * @param responseStatus response HTTP status.
     * @return response body.
     */
    private String makeErrorResponse(Response response, Exception exception, int responseStatus) {

        if (logger.isInfoEnabled()) {
            logger.info("New folder creation failed with exception:\n {} \nServer response status {}."
                    , exception.getMessage(), responseStatus);
        }

        response.status(responseStatus);

        return exception.getMessage();
    }

    /**
     * Creates new {@link CreateFolder} command with set {@link FileSystemItemId} and {@link UserId}.
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

}
