package io.javaclasses.filehub.storage;

/**
 * Marks class as a storage {@link Storage} record.
 *
 * @param <I> - specific implementation of {@link RecordId}.
 */
public interface Record<I> {

    /**
     * @return - specific record id.
     */
    I id();
}
