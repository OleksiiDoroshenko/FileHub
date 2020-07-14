package io.javaclasses.filehub.api;

/**
 * An abstract base {@link Query} handler.
 *
 * @param <Q> specific {@link Query}.
 * @param <R> specific type to be returned in the method.
 */
public interface SystemView<Q extends Query, R> {

    /**
     * Handles {@link Query} that was requested by client.
     *
     * @param query command to be processed.
     * @return result of handling {@link Query}.
     */
    R handle(Q query);
}
