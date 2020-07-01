package io.javaclasses.filehub.api;

/**
 * An abstract base {@link Command} handler.
 *
 * @param <C> specific {@link Command}.
 * @param <R> specific type to be returned in the method.
 */
public interface SystemProcess<C extends Command, R> {

    /**
     * Handles {@link Command} that was requested by client.
     *
     * @param command command to be processed.
     * @return result of handling {@link Command}.
     */
    R handle(C command);
}
