package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of {@link FileSystemItemsStorage} that saves {@link FolderRecord}.
 */
public class FolderStorage extends FileSystemItemsStorage<FolderRecord, FolderId> {

    /**
     * Creates folders that are placed in the folder with set identifier.
     *
     * @param id parent folder.
     * @return list with folders.
     */
    public synchronized List<FolderRecord> subFolders(FolderId id) {
        return all().stream().filter(item -> item.parentId() != null
                && item.parentId().equals(id)).collect(toList());
    }
}
