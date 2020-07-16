package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.RecordId;
import io.javaclasses.filehub.storage.userStorage.UserId;

/**
 * An abstract base for all file system items.
 */
public interface FileSystemItem<I extends RecordId> extends Record<I> {

    FolderId parentId();

    UserId ownerId();

    FileSystemItemName name();
}
