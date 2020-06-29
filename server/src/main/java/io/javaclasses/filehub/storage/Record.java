package io.javaclasses.filehub.storage;

/**
 * Data structure that will be stored in {@link RecordStorage}.
 *
 * @param <I> - specific implementation of {@link RecordId}.
 */
public interface Record<I> {

    /**
     * Returns unique record identifier.
     *
     * @return specific record id.
     */
    I id();
}
