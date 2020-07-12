package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemsStorage;

/**
 * An exception that is thrown when file system item was not found in {@link FileSystemItemsStorage}.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Returns instance of {@link NotFoundException} class.
     *
     * @param message error message that describes what went wrong.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
