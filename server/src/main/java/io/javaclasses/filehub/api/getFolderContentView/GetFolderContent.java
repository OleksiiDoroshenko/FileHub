package io.javaclasses.filehub.api.getFolderContentView;

import io.javaclasses.filehub.api.SystemView;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.*;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.web.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * The {@link SystemView} that handles {@link GetFolderContent} query.
 */
public class GetFolderContent implements SystemView<FolderContent, FolderContentDto> {

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
    public FolderContentDto process(FolderContent query) {

        checkNotNull(query);

        FileSystemItemId folderId = query.folderId();
        LoggedInUserRecord record = query.loggedInUser();

        checkOnExistence(folderId);

        List<FileDto> files = getChildrenFiles(folderId, record.userId());
        List<FolderDto> folders = getChildrenFolders(folderId, record.userId());

        return createFolderContent(files, folders);
    }

    /**
     * Creates DTO of the folder content.
     *
     * @param files   files that are placed in folder with queried identifier.
     * @param folders folders that are placed in folder with queried identifier.
     * @return DTO of the folder content.
     */
    private FolderContentDto createFolderContent(List<FileDto> files, List<FolderDto> folders) {
        return new FolderContentDto(files, folders);
    }

    /**
     * Returns subfolders of the folder with queried identifier.
     *
     * @param folderId parent folder identifier.
     * @param userId   owner of the folders.
     * @return folder DTO list.
     */
    private List<FolderDto> getChildrenFolders(FileSystemItemId folderId, UserId userId) {
        List<FolderRecord> children = folderStorage.getChildren(folderId, userId);

        return children.stream()
                .map(this::createFolderDto)
                .collect(toList());
    }

    /**
     * Creates {@link FolderDto} based on set {@link FolderRecord}.
     *
     * @param child base for {@link FolderDto}.
     * @return created DTO.
     */
    private FolderDto createFolderDto(FolderRecord child) {
        return new FolderDto(child.name(), child.id(), child.parentId());
    }

    /**
     * Gets all files that are placed in folder with queried identifier.
     *
     * @param folderId parent folder identifier.
     * @param userId   owner of the folders.
     * @return file list.
     */
    private List<FileDto> getChildrenFiles(FileSystemItemId folderId, UserId userId) {
        List<FileRecord> children = fileStorage.getChildren(folderId, userId);

        return children.stream()
                .map(this::createFileDto)
                .collect(toList());
    }

    /**
     * Creates {@link FileDto} based on set {@link FileRecord}.
     *
     * @param child base for {@link FileDto}.
     * @return created DTO.
     */
    private FileDto createFileDto(FileRecord child) {
        return new FileDto(child.name(), child.id(), child.size(), child.mimeType());
    }

    /**
     * Checks if FileHub application storage has folder with required identifier.
     *
     * @param folderId folder identifier.
     * @throws NotFoundException if storage does not contain folder with required identifier.
     */
    private void checkOnExistence(FileSystemItemId folderId) {
        Optional<FolderRecord> record = folderStorage.get(folderId);

        if (!record.isPresent()) {
            throw new NotFoundException(format("Folder with this id %s is not found.", folderId.value()));
        }

    }
}

