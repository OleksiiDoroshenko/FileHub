package io.javaclasses.filehub.storage;

/**
 * Data structure that will be stored in {@link RecordStorage}.
 *
 * @param <I> - {@link RecordId} implementation.
 */
public interface Record<I> {

    /**
     * Returns record identifier.
     *
     * @return specific record id.
     */
    I id();
}
