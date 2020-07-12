package io.javaclasses.filehub.api.folderCreationProcess;

import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

/**
 * Process in the FileHub application that handles {@link CreateFolder} command.
 */
public class FolderCreation implements SystemProcess<CreateFolder, FileSystemItemId> {

    private static final String NEW_FOLDER_NAME = "New Folder";
    private final FolderStorage folderStorage;

    /**
     * Returns instance of {@link FolderCreation} class.
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

        FolderRecord folder = createFolder(parentId, ownerId);

        return folderStorage.add(folder);
    }

    /**
     * Creates new {@link FolderRecord}.
     *
     * @param parentId parent folder identifier.
     * @param ownerId  owner identifier.
     * @return created folder.
     */
    private FolderRecord createFolder(FileSystemItemId parentId, UserId ownerId) {

        FileSystemItemId id = createFolderId();

        List<FolderRecord> subfolders = folderStorage.all(parentId, ownerId);
        FileSystemItemName name = createFolderName(subfolders);

        return new FolderRecord(id, name, parentId, ownerId);
    }

    /**
     * Creates folder name.
     * <p>All new folders will be created with {@value NEW_FOLDER_NAME} name plus number.
     * This number is calculated based on previous folder name number.</p>
     *
     * @param folders folders that are placed in the same parent folder.
     * @return folder name.
     */
    private FileSystemItemName createFolderName(List<FolderRecord> folders) {

        int folderNumber = getNextFolderNumber(folders);

        return new FileSystemItemName(NEW_FOLDER_NAME + folderNumber);
    }

    /**
     * Calculates next number that will be added to new folder name.
     *
     * @param folders folders that are placed in the same parent folder.
     * @return next folder name number.
     */
    private int getNextFolderNumber(List<FolderRecord> folders) {

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

        return numbers.stream().max(Integer::compareTo).orElse(0);
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
            throw new NotFoundException("Parent folder not found.");
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
