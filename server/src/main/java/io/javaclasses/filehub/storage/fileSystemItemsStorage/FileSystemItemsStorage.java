package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.InMemoryRecordStorage;
import io.javaclasses.filehub.storage.RecordStorage;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * The {@link RecordStorage} for saving {@link FileSystemItem}.
 *
 * @param <T> instance of {@link FileSystemItem}.
 */
public abstract class FileSystemItemsStorage<T extends FileSystemItem>
        extends InMemoryRecordStorage<T, FileSystemItemId> {

    /**
     * Returns file system items with passed in the parameters parent and owner identifiers.
     *
     * @param id      parent folder identifier.
     * @param ownerId owner identifier.
     * @return list that contains all folder children.
     */
    public synchronized List<T> all(FileSystemItemId id, UserId ownerId) {
        return all().stream().filter(item -> item.parentId() != null
                && item.parentId().equals(id) && item.ownerId().equals(ownerId)).collect(toList());
    }

    /**
     * Gets {@link FileSystemItem} by its name.
     *
     * @param name item name.
     * @return {@link Optional<FileSystemItem>} if item is present in the storage /
     * empty {@link Optional} if item does not exist in the storage;
     */
    public Optional<T> get(FileSystemItemName name) {
        return all().stream().filter(item -> item.name().equals(name)).findAny();
    }
}
