package io.javaclasses.filehub.api;

/**
 * Abstract base process in the application that handles {@link Command} from client.
 *
 * @param <Command>    - specific command type.
 * @param <ReturnType> - specific type to be returned in method.
 */
public interface SystemProcess<Command, ReturnType> {

    /**
     * Handles {@link Command} that was requested by client.
     *
     * @param command - command to be processed.
     * @return result of handling {@link Command}.
     */
    ReturnType handle(Command command);
}
