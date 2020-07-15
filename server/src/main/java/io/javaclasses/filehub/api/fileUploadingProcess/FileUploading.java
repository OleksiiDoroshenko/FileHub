package io.javaclasses.filehub.api.fileUploadingProcess;

import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.folderCreationProcess.UserNotOwnerException;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.FolderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Process in the FilHub application that handles {@link UploadFile} command.
 */
public class FileUploading implements SystemProcess<UploadFile, FileSystemItemId> {

    private static final Logger logger = LoggerFactory.getLogger(FileUploading.class);
    private final FileDataStorage fileDataStorage;
    private final FileStorage fileStorage;
    private final FolderStorage folderStorage;

    /**
     * Returns instance of the {@link FileUploading} process with set {@link FileDataStorage}, {@link FileStorage} and
     * {@link FolderStorage} parameters.
     *
     * @param fileDataStorage storage with file data.
     * @param fileStorage     filesStorage.
     * @param folderStorage   folder storage.
     */
    public FileUploading(FileDataStorage fileDataStorage, FileStorage fileStorage, FolderStorage folderStorage) {

        this.fileDataStorage = checkNotNull(fileDataStorage);
        this.fileStorage = checkNotNull(fileStorage);
        this.folderStorage = checkNotNull(folderStorage);

    }

    /**
     * Handles {@link UploadFile} command.
     * <p>Creates new {@link FileRecord} and adds it to the {@link FileStorage},
     * Creates new {@link FileDataRecord} and adds it to the {@link FileDataStorage}.</p>
     *
     * @param command command to be processed.
     * @return an identifier of the uploaded file.
     */
    @Override
    public FileSystemItemId handle(UploadFile command) {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to handle \"UploadFile\" command.");
        }

        FileSystemItemId parentId = getParentId(command);
        UserId ownerId = getOwnerId(command);
        File uploadedFile = getFile(command);

        checkOnExistence(parentId);
        ownershipVerification(parentId, ownerId);

        FileRecord file = createFileRecord(parentId, ownerId, uploadedFile);
        FileDataRecord fileData = createFileDataRecord(file.id(), uploadedFile.data());

        fileDataStorage.add(fileData);
        fileStorage.add(file);

        if (logger.isDebugEnabled()) {
            logger.debug("Handling \"UploadFile\" command was completed successfully.");
        }
        return file.id();
    }

    /**
     * Creates new instance of {@link FileDataRecord} with set {@link FileSystemItemId} and data.
     *
     * @param id   file identifier.
     * @param data representation of the file in the byte array form.
     * @return created record.
     */
    private FileDataRecord createFileDataRecord(FileSystemItemId id, byte[] data) {

        return new FileDataRecord(id, data);
    }

    /**
     * Creates new instance of {@link FileRecord} with set parameters.
     *
     * @param parentId     parent folder identifier.
     * @param ownerId      owner identifier.
     * @param uploadedFile file information.
     * @return created record.
     */
    private FileRecord createFileRecord(FileSystemItemId parentId, UserId ownerId, File uploadedFile) {

        FileSystemItemId id = createFileRecordId();
        FileSystemItemName name = uploadedFile.name();
        FileSize size = uploadedFile.size();
        FileMimeType mimeType = uploadedFile.mimeType();

        return new FileRecord(id, name, parentId, size, mimeType, ownerId);
    }

    /**
     * Creates new instance of {@link FileSystemItemId} for {@link FileRecord}.
     *
     * @return created identifier.
     */
    private FileSystemItemId createFileRecordId() {

        return new FileSystemItemId(fileStorage.generateId());
    }

    /**
     * Gets file from set {@link UploadFile} command.
     *
     * @param command command for getting file.
     * @return file.
     */
    private File getFile(UploadFile command) {

        return command.file();
    }

    /**
     * Gets file from set {@link UploadFile} command.
     *
     * @param command command for getting file.
     * @return file.
     */
    private UserId getOwnerId(UploadFile command) {

        return command.ownerId();
    }

    /**
     * Gets file from set {@link UploadFile} command.
     *
     * @param command command for getting file.
     * @return file.
     */
    private FileSystemItemId getParentId(UploadFile command) {

        return command.parentId();
    }

    /**
     * Checks if provided user is owner of the folder with set {@link FileSystemItemId}.
     *
     * @param id      folder identifier.
     * @param ownerId owner identifier.
     */
    private void ownershipVerification(FileSystemItemId id, UserId ownerId) {

        FolderRecord record = folderStorage.get(id).get();

        if (!record.ownerId().equals(ownerId)) {

            throw new UserNotOwnerException(format("User with %s is not owner of the folder with %s.",
                    id, ownerId));
        }
    }

    /**
     * Checks if folder with set identifier exists in the {@link FolderStorage}.
     *
     * @param id folder identifier.
     */
    private void checkOnExistence(FileSystemItemId id) {

        Optional<FolderRecord> record = folderStorage.get(id);

        if (!record.isPresent()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Folder with {} was not found.", id);
            }
            throw new FolderNotFoundException(format("Folder with %s was not found.", id));
        }
    }
}
