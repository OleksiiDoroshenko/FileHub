package io.javaclasses.filehub.web.routes;

import io.javaclasses.filehub.api.fileUploadingProcess.File;
import io.javaclasses.filehub.api.fileUploadingProcess.FileUploading;
import io.javaclasses.filehub.api.fileUploadingProcess.UploadFile;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.RequestId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

/**
 * The {@link Route} that handles upload file client requests.
 */
public class UploadFileRoute extends AuthenticatedRoute {

    private static final Logger logger = LoggerFactory.getLogger(UploadFileRoute.class);
    private final FileDataStorage fileDataStorage;
    private final FileStorage fileStorage;
    private final FolderStorage folderStorage;

    /**
     * Returns instance of {@link UploadFileRoute} with set
     * {@link FileStorage}, {@link FileDataStorage} and {@link FolderStorage}.
     *
     * @param fileStorage
     * @param fileDataStorage
     * @param folderStorage
     */
    public UploadFileRoute(FileStorage fileStorage, FileDataStorage fileDataStorage, FolderStorage folderStorage) {

        this.fileStorage = checkNotNull(fileStorage);
        this.folderStorage = checkNotNull(folderStorage);
        this.fileDataStorage = checkNotNull(fileDataStorage);
    }

    /**
     * Handles a request from the client to upload file.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return server response.
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {

            FileSystemItemId parentId = RequestId.parse(request);
            LoggedInUserRecord record = getLoggedInUser();
            File file = getFile(request);

            FileUploading process = createProcess();
            UploadFile command = createCommand(parentId, record.userId(), file);

            process.handle(command);

            return 200;

        } catch (IOException | ServletException e) {
            return SC_BAD_REQUEST;
        }

    }

    /**
     * Creates new {@link UploadFile} command with set {@link FileSystemItemId}, {@link UserId} and {@link File}.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     * @param file     file to be uploaded.
     * @return created command
     */
    private UploadFile createCommand(FileSystemItemId parentId, UserId ownerId, File file) {

        return new UploadFile(parentId, ownerId, file);
    }

    /**
     * Creates new {@link FileUploading} process with set {@link FileDataStorage}, {@link FileStorage}
     * and {@link FolderStorage}.
     *
     * @return created process.
     */
    private FileUploading createProcess() {

        return new FileUploading(fileDataStorage, fileStorage, folderStorage);
    }

    /**
     * Gets file from {@link Request}.
     *
     * @param request HTTP request.
     * @return file.
     * @throws IOException      if an I/O error occurred during the  request parsing.
     * @throws ServletException if set request is not of type multipart/form-data.
     */
    private File getFile(Request request) throws IOException, ServletException {

        request.attribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement(null, 100000000,
                        100000000, 1024));

        Part uploadedFile = request.raw().getPart("file");
        InputStream in = uploadedFile.getInputStream();

        byte[] data = new byte[in.available()];
        in.read(data);

        File file = createFile(data, uploadedFile);
        uploadedFile.delete();
        return file;
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
        return new File(data, name, mimeType, size);
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
}
