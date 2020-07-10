package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.userStorage.UserId;

/**
 * An abstract base for all file system items.
 */
public interface FileSystemItem extends Record<FileSystemItemId> {

    FileSystemItemId parentId();

    UserId ownerId();

    FileSystemItemName name();
}
