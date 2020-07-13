package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderStorage;

/**
 * An exception that is thrown when folder was not found in {@link FolderStorage}.
 */
public class FolderNotFoundException extends RuntimeException {

    /**
     * Returns instance of {@link FolderNotFoundException} with set error message.
     *
     * @param message error message that describes what went wrong.
     */
    public FolderNotFoundException(String message) {
        super(message);
    }
}
