package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.InMemoryRecordStorage;
import io.javaclasses.filehub.storage.RecordId;
import io.javaclasses.filehub.storage.RecordStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The {@link RecordStorage} for saving {@link FileSystemItem}.
 *
 * @param <T> instance of {@link FileSystemItem}.
 */
public abstract class FileSystemItemsStorage<T extends FileSystemItem<I>, I extends RecordId>
        extends InMemoryRecordStorage<T, I> {

    /**
     * Creates file system items with passed in the parameters parent and owner identifiers.
     *
     * @param id      parent folder identifier.
     * @param ownerId owner identifier.
     * @return list that contains all folder children.
     */
    public synchronized List<T> all(FolderId id, UserId ownerId) {
        return all().stream().filter(item -> item.parentId() != null
                && item.parentId().equals(id) && item.ownerId().equals(ownerId)).collect(toList());
    }
}
