package io.javaclasses.filehub.storage;

/**
 * Data structure that is stored in {@link RecordStorage}.
 *
 * @param <I> {@link RecordId} implementation.
 */
public interface Record<I extends RecordId> {

    /**
     * Returns record identifier.
     *
     * @return specific {@link RecordId}.
     */
    I id();
}
