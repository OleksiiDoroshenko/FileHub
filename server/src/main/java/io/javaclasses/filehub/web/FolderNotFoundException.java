package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemsStorage;

/**
 * An exception that is thrown when file system item was not found in {@link FileSystemItemsStorage}.
 */
public class FolderNotFoundException extends RuntimeException {
    /**
     * Returns instance of {@link FolderNotFoundException} class.
     *
     * @param message error message that describes what went wrong.
     */
    public FolderNotFoundException(String message) {
        super(message);
    }
}
