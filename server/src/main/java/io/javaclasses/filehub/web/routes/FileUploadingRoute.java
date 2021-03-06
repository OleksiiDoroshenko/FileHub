package io.javaclasses.filehub.web.routes;

import io.javaclasses.filehub.api.fileUploadingProcess.File;
import io.javaclasses.filehub.api.fileUploadingProcess.FileContent;
import io.javaclasses.filehub.api.fileUploadingProcess.FileUploading;
import io.javaclasses.filehub.api.fileUploadingProcess.UploadFile;
import io.javaclasses.filehub.api.folderCreationProcess.AccessDeniedException;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.FolderNotFoundException;
import io.javaclasses.filehub.web.RequestIdParser;
import io.javaclasses.filehub.web.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * The {@link Route} that handles upload file client requests.
 */
public class FileUploadingRoute extends AuthenticatedRoute {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadingRoute.class);
    private static final String REQUEST_PART = "file";
    private final FileContentStorage fileContentStorage;
    private final FileStorage fileStorage;
    private final FolderStorage folderStorage;

    /**
     * Creates instance of {@link FileUploadingRoute} with set parameters.
     *
     * @param fileStorage        file storage.
     * @param fileContentStorage file data storage.
     * @param folderStorage      folder storage.
     */
    public FileUploadingRoute(FileStorage fileStorage, FileContentStorage fileContentStorage, FolderStorage folderStorage) {

        this.fileStorage = checkNotNull(fileStorage);
        this.folderStorage = checkNotNull(folderStorage);
        this.fileContentStorage = checkNotNull(fileContentStorage);
    }

    /**
     * Handles a request from the client to upload file.
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

            File file = getFile(request);
            FolderId parentId = RequestIdParser.parseFolderId(request);
            LoggedInUserRecord record = getLoggedInUser();

            FileUploading process = createProcess();
            UploadFile command = createCommand(parentId, record.userId(), file);

            process.handle(command);

            if (logger.isInfoEnabled()) {
                logger.info("Uploading file was completed successfully.");
            }

            response.status(SC_OK);
            return "Success";

        } catch (FolderNotFoundException e) {

            return makeErrorResponse(response, e, SC_NOT_FOUND);

        } catch (UserNotLoggedInException e) {

            return makeErrorResponse(response, e, SC_UNAUTHORIZED);

        } catch (AccessDeniedException e) {

            return makeErrorResponse(response, e, SC_CONFLICT);

        } catch (IOException | ServletException | NullPointerException e) {

            return makeErrorResponse(response, e, SC_BAD_REQUEST);

        } catch (Exception e) {

            return makeErrorResponse(response, e, SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates new {@link UploadFile} command with set parameters.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     * @param file     file to be uploaded.
     * @return created command
     */
    private UploadFile createCommand(FolderId parentId, UserId ownerId, File file) {

        return new UploadFile(parentId, ownerId, file);
    }

    /**
     * Creates new {@link FileUploading} process.
     *
     * @return created process.
     */
    private FileUploading createProcess() {

        return new FileUploading(folderStorage, fileStorage, fileContentStorage);
    }

    /**
     * Gets {@link File} from {@link Request}.
     *
     * @param request HTTP request.
     * @return file.
     * @throws IOException      if an I/O error occurred during the  request parsing.
     * @throws ServletException if set request is not of type multipart/form-data.
     */
    private File getFile(Request request) throws IOException, ServletException {

        Part uploadedFile = request.raw().getPart(REQUEST_PART);
        InputStream in = uploadedFile.getInputStream();

        byte[] data = new byte[in.available()];
        in.read(data);

        return createFile(data, uploadedFile);
    }

    /**
     * Creates new instance of {@link File} with set parameters.
     *
     * @param data         representation of the file in the byte array form.
     * @param uploadedFile file that was uploaded.
     * @return created file.
     */
    private File createFile(byte[] data, Part uploadedFile) {

        FileSystemItemName name = new FileSystemItemName(uploadedFile.getSubmittedFileName());
        FileMimeType mimeType = new FileMimeType(uploadedFile.getContentType());
        FileSize size = new FileSize(uploadedFile.getSize());
        FileContent content = new FileContent(data);

        return new File(content, name, mimeType, size);
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
            logger.info("Uploading file failed with exception:\n {} \nServer response status {}."
                    , exception.getMessage(), responseStatus);
        }

        response.status(responseStatus);

        return exception.getMessage();
    }
}
