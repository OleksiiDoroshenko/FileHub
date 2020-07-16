package io.javaclasses.filehub.api.fileUploadingProcess;

import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.folderCreationProcess.AccessDeniedException;
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
public class FileUploading implements SystemProcess<UploadFile, FileId> {

    private static final Logger logger = LoggerFactory.getLogger(FileUploading.class);
    private final FileContentStorage fileContentStorage;
    private final FileStorage fileStorage;
    private final FolderStorage folderStorage;

    /**
     * Creates instance of the {@link FileUploading} process with set parameters.
     *
     * @param folderStorage      folder storage.
     * @param fileStorage        filesStorage.
     * @param fileContentStorage storage with file data.
     */
    public FileUploading(FolderStorage folderStorage, FileStorage fileStorage, FileContentStorage fileContentStorage) {

        this.folderStorage = checkNotNull(folderStorage);
        this.fileStorage = checkNotNull(fileStorage);
        this.fileContentStorage = checkNotNull(fileContentStorage);

    }

    /**
     * Handles {@link UploadFile} command.
     * <p>Creates new {@link FileRecord} and adds it to the {@link FileStorage},
     * Creates new {@link FileContentRecord} and adds it to the {@link FileContentStorage}.</p>
     *
     * @param command command to be processed.
     * @return an identifier of the uploaded file.
     */
    @Override
    public FileId handle(UploadFile command) {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to handle \"UploadFile\" command.");
        }

        FolderId parentId = getParentId(command);
        UserId ownerId = getOwnerId(command);
        File uploadedFile = getFile(command);

        checkOnExistence(parentId);
        verifyOwnership(parentId, ownerId);

        FileRecord file = createFileRecord(parentId, ownerId, uploadedFile);
        FileContentRecord fileData = createFileDataRecord(file.id(), uploadedFile.data());


        FileId id = addToStorage(file, fileData);

        if (logger.isDebugEnabled()) {
            logger.debug("Handling \"UploadFile\" command was completed successfully.");
        }
        return id;
    }

    /**
     * Adds set {@link FileRecord} to the {@link FileStorage} and {@link FileContentRecord} to the {@link FileContentStorage}.
     * <p>If {@link FileStorage} already contains {@link FileRecord} with the same {@link FileSystemItemName} this
     * record will be rewritten with new record but with the same identifier.
     * {@link FileContentRecord} also will be rewritten.</p>
     *
     * @param file     file information.
     * @param fileData file data.
     * @return an identifier under which records were saved.
     */
    private FileId addToStorage(FileRecord file, FileContentRecord fileData) {

        Optional<FileRecord> record = fileStorage.get(file.name());

        if (!record.isPresent()) {

            fileStorage.add(file);
            fileContentStorage.add(fileData);

            return file.id();
        }

        FileId id = record.get().id();

        rewriteFileInStorage(id, file);
        rewriteFileDataInStorage(id, fileData);

        return id;
    }

    /**
     * Rewrites old {@link FileContentRecord} in the {@link FileContentStorage}.
     *
     * <p>Saves previous {@link FileId} with new data.</p>
     *
     * @param id     identifier of the {@link FileContentRecord} that should be rewritten.
     * @param record record where the data will be taken from.
     */
    private void rewriteFileDataInStorage(FileId id, FileContentRecord record) {

        FileContent data = record.data();

        FileContentRecord newRecord = new FileContentRecord(id, data);
        fileContentStorage.add(newRecord);
    }

    /**
     * Rewrites old {@link FileRecord} in the {@link FileStorage}.
     *
     * <p>Saves previous {@link FileId} with new data.</p>
     *
     * @param id     identifier of the {@link FileRecord} that should be rewritten.
     * @param record record where the data will be taken from.
     */
    private void rewriteFileInStorage(FileId id, FileRecord record) {

        FileSystemItemName name = record.name();
        FolderId parentId = record.parentId();
        FileSize size = record.size();
        FileMimeType mimeType = record.mimeType();
        UserId ownerId = record.ownerId();

        FileRecord newRecord = new FileRecord(id, name, parentId, size, mimeType, ownerId);
        fileStorage.add(newRecord);
    }

    /**
     * Creates new instance of {@link FileContentRecord} with set {@link FileId} and data.
     *
     * @param id   file identifier.
     * @param data representation of the file in the byte array form.
     * @return created record.
     */
    private FileContentRecord createFileDataRecord(FileId id, FileContent data) {

        return new FileContentRecord(id, data);
    }

    /**
     * Creates new instance of {@link FileRecord} with set parameters.
     *
     * @param parentId     parent folder identifier.
     * @param ownerId      owner identifier.
     * @param uploadedFile file information.
     * @return created record.
     */
    private FileRecord createFileRecord(FolderId parentId, UserId ownerId, File uploadedFile) {

        FileId id = createFileRecordId();
        FileSystemItemName name = uploadedFile.name();
        FileSize size = uploadedFile.size();
        FileMimeType mimeType = uploadedFile.mimeType();

        return new FileRecord(id, name, parentId, size, mimeType, ownerId);
    }

    /**
     * Creates new instance of {@link FileId} for {@link FileRecord}.
     *
     * @return created identifier.
     */
    private FileId createFileRecordId() {

        return new FileId(fileStorage.generateId());
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
    private FolderId getParentId(UploadFile command) {

        return command.parentId();
    }

    /**
     * Checks if provided user is owner of the folder with set {@link FolderId}.
     *
     * @param id      folder identifier.
     * @param ownerId owner identifier.
     */
    private void verifyOwnership(FolderId id, UserId ownerId) {

        FolderRecord record = folderStorage.get(id).get();

        if (!record.ownerId().equals(ownerId)) {

            throw new AccessDeniedException(format("User with %s is not owner of the folder with %s.",
                    id, ownerId));
        }
    }

    /**
     * Checks if folder with set identifier exists in the {@link FolderStorage}.
     *
     * @param id folder identifier.
     */
    private void checkOnExistence(FolderId id) {

        Optional<FolderRecord> record = folderStorage.get(id);

        if (!record.isPresent()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Folder with {} was not found.", id);
            }
            throw new FolderNotFoundException(format("Folder with %s was not found.", id));
        }
    }
}
