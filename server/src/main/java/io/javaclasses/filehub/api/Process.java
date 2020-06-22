package io.javaclasses.filehub.api;

/**
 * Main interface for all process that can be held in the server.
 *
 * @param <Command>    - specific command type.
 * @param <ReturnType> - specific type to be returned in method.
 */
public interface Process<Command, ReturnType> {
    /**
     * Process specific command which type was passed from generic.
     *
     * @param command - command to be processed.
     * @return class that implements {@link ReturnType} interface.
     */
    ReturnType handle(Command command);
}
