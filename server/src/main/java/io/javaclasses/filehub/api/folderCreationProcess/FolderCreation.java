package io.javaclasses.filehub.api.folderCreationProcess;

import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.FolderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * Process in the FileHub application that handles {@link CreateFolder} command.
 */
public class FolderCreation implements SystemProcess<CreateFolder, FileSystemItemId> {

    private static final Logger logger = LoggerFactory.getLogger(FolderCreation.class);
    private static final String NEW_FOLDER_NAME = "New Folder";
    private final FolderStorage folderStorage;

    /**
     * Returns instance of {@link FolderCreation} with set {@link FolderStorage}.
     *
     * @param folderStorage folder storage.
     */
    public FolderCreation(FolderStorage folderStorage) {
        this.folderStorage = checkNotNull(folderStorage);
    }

    /**
     * Handles {@link CreateFolder} command.
     * <p>Creates new {@link FolderRecord} and adds it to the {@link FolderStorage}.</p>
     *
     * @param command command to be processed.
     * @return an identifier of the created folder.
     */
    @Override
    public FileSystemItemId handle(CreateFolder command) {

        checkNotNull(command);

        FileSystemItemId parentId = getParentId(command);
        UserId ownerId = getOwnerId(command);

        checkOnExistence(parentId);
        ownershipVerification(parentId, ownerId);

        FolderRecord folder = createFolder(parentId, ownerId);

        return folderStorage.add(folder);
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
     * Creates new {@link FolderRecord} with set {@link FileSystemItemId} as parent identifier
     * and {@link UserId} as owner identifier.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     * @return created folder.
     */
    private FolderRecord createFolder(FileSystemItemId parentId, UserId ownerId) {

        FileSystemItemId id = createFolderId();

        List<FolderRecord> subfolders = folderStorage.subFolders(parentId);
        FileSystemItemName name = generateFolderName(subfolders);

        return new FolderRecord(id, name, parentId, ownerId);
    }

    /**
     * Creates folder name.
     * <p>
     * All new folders will be created with {@value NEW_FOLDER_NAME} name plus number.
     * This number is calculated based on previous folder name number.
     * for example : "New Folder0", "New Folder1" ... and so on.
     * </p>
     *
     * @param folders folders that are placed in the same parent folder.
     * @return folder name.
     */
    private static FileSystemItemName generateFolderName(List<FolderRecord> folders) {

        List<Integer> numbers = new ArrayList<>();

        List<FolderRecord> foldersWithSimilarNames = folders.stream()
                .filter(folder -> folder.name().value().contains(NEW_FOLDER_NAME)).collect(toList());

        for (FolderRecord folder : foldersWithSimilarNames) {
            String[] name = folder.name().value().split(NEW_FOLDER_NAME);
            try {
                numbers.add(Integer.parseInt(name[name.length - 1]));
            } catch (NumberFormatException e) {
                continue;
            }
        }

        int folderNumber = numbers.stream().max(Integer::compareTo).orElse(0);

        return new FileSystemItemName(NEW_FOLDER_NAME + folderNumber);
    }

    /**
     * Creates new folder id.
     *
     * @return folder id.
     */
    private FileSystemItemId createFolderId() {

        return new FileSystemItemId(folderStorage.generateId());
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

    /**
     * Gets the identifier of the future owner of the folder.
     *
     * @param command create folder command.
     * @return owner identifier.
     */
    private UserId getOwnerId(CreateFolder command) {
        return command.ownerId();
    }

    /**
     * Gets the identifier of the future parent folder.
     *
     * @param command create folder command.
     * @return parent folder identifier.
     */
    private FileSystemItemId getParentId(CreateFolder command) {
        return command.parentId();
    }

}
