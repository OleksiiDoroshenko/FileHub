package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemsStorage;

/**
 * An exception is thrown when file system item was not found in {@link FileSystemItemsStorage}.
 */
public class FileSystemItemNotFoundException extends RuntimeException {
    /**
     * Returns instance of {@link FileSystemItemNotFoundException} class.
     *
     * @param message error message that describes what went wrong.
     */
    public FileSystemItemNotFoundException(String message) {
        super(message);
    }
}
