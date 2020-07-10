package io.javaclasses.filehub.api.getFolderContentView;

import io.javaclasses.filehub.api.SystemView;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link SystemView} that handles {@link GetFolderContent} query.
 */
public class GetFolderContent implements SystemView<FolderContent, FolderContentDTO> {

    private final FolderStorage folderStorage;
    private final FileStorage fileStorage;

    /**
     * Returns instance of {@link GetFolderContent} class.
     *
     * @param folderStorage folder storage.
     * @param fileStorage   file storage.
     */
    public GetFolderContent(FolderStorage folderStorage, FileStorage fileStorage) {
        this.folderStorage = checkNotNull(folderStorage);
        this.fileStorage = checkNotNull(fileStorage);
    }

    /**
     * Handles {@link FolderContent} query.
     * <p>
     * Gets all {@link FolderRecord} and {@link FileRecord} that have the parent id the same as query include.
     * </p>
     *
     * @param query query to be processed.
     * @return folder content.
     */
    @Override
    public FolderContentDTO handle(FolderContent query) {

        checkNotNull(query);

        FileSystemItemId folderId = checkNotNull(query.folderId());
        LoggedInUserRecord record = checkNotNull(query.loggedInUser());

        List<FileDTO> files = getChildrenFiles(folderId, record.userId());
        List<FolderDTO> folders = getChildrenFolders(folderId, record.userId());

        return createFolderContent(files, folders);
    }

    /**
     * Creates DTO of the folder content.
     *
     * @param files   files that are placed in folder with queried identifier.
     * @param folders folders that are placed in folder with queried identifier.
     * @return DTO of the folder content.
     */
    private FolderContentDTO createFolderContent(List<FileDTO> files, List<FolderDTO> folders) {
        return new FolderContentDTO(files, folders);
    }

    /**
     * Gets all folders that are placed in folder with queried identifier.
     *
     * @param folderId parent folder identifier.
     * @param userId   owner of the folders.
     * @return folder list.
     */
    private List<FolderDTO> getChildrenFolders(FileSystemItemId folderId, UserId userId) {
        List<FolderRecord> children = folderStorage.getChildren(folderId, userId);
        List<FolderDTO> result = new ArrayList<>();

        children.forEach(child -> {
            result.add(createFolderDTO(child));
        });
        return result;
    }

    /**
     * Creates {@link FolderDTO} based on set {@link FolderRecord}.
     *
     * @param child base for {@link FolderDTO}.
     * @return created DTO.
     */
    private FolderDTO createFolderDTO(FolderRecord child) {
        return new FolderDTO(child.name(), child.id(), child.parentId());
    }

    /**
     * Gets all files that are placed in folder with queried identifier.
     *
     * @param folderId parent folder identifier.
     * @param userId   owner of the folders.
     * @return file list.
     */
    private List<FileDTO> getChildrenFiles(FileSystemItemId folderId, UserId userId) {
        List<FileRecord> children = fileStorage.getChildren(folderId, userId);
        List<FileDTO> result = new ArrayList<>();

        children.forEach(child -> {
            result.add(createFileDTO(child));
        });
        return result;
    }

    /**
     * Creates {@link FileDTO} based on set {@link FileRecord}.
     *
     * @param child base for {@link FileDTO}.
     * @return created DTO.
     */
    private FileDTO createFileDTO(FileRecord child) {
        return new FileDTO(child.name(), child.id(), child.parentId(), child.size(), child.mimeType(), child.type());
    }
}

