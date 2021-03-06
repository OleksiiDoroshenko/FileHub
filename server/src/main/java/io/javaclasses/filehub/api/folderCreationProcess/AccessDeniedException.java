package io.javaclasses.filehub.api.folderCreationProcess;

/**
 * An exception that is thrown when user that is not the owner of the folder trying to manipulate with this folder.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Creates instance of {@link AccessDeniedException} with set error message.
     *
     * @param message error message that describes what went wrong.
     */
    public AccessDeniedException(String message) {
    }
}
