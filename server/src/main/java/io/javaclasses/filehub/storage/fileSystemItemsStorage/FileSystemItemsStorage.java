package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.InMemoryRecordStorage;
import io.javaclasses.filehub.storage.RecordStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link RecordStorage} for saving {@link FileSystemItem}.
 *
 * @param <T> instance of {@link FileSystemItem}.
 */
public abstract class FileSystemItemsStorage<T extends FileSystemItem>
        extends InMemoryRecordStorage<T, FileSystemItemId> {

    /**
     * Returns file system items with passed int the parameters parent and owner identifiers.
     *
     * @param id      parent folder identifier.
     * @param ownerId owner identifier.
     * @return list that contains all folder children.
     */
    public synchronized List<T> getChildren(FileSystemItemId id, UserId ownerId) {
        List<T> result = new ArrayList<>();
        all().forEach(item -> {
            if (item.parentId() != null && item.parentId().equals(id) && item.ownerId().equals(ownerId)) {
                result.add(item);
            }
        });
        return result;
    }
}
