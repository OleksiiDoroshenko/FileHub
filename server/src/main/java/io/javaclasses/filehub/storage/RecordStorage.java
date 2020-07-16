package io.javaclasses.filehub.storage;

import java.util.List;
import java.util.Optional;

/**
 * An abstract base storage for FileHub application.
 * Provide CRUD operations for stored items.
 *
 * @param <R> specific {@link Record}.
 * @param <I> specific {@link RecordId}.
 */
public interface RecordStorage<R extends Record<I>, I extends RecordId> {
    /**
     * Returns record by its id.
     *
     * @param id specific record id.
     * @return record.
     */
    Optional<R> get(I id);

    /**
     * Removes record from storage by it's id.
     *
     * @param id specific record id.
     * @return removed record.
     */
    Optional<R> remove(I id);

    /**
     * Returns all records from storage.
     *
     * @return record list.
     */
    List<R> all();

    /**
     * Adds record into the storage.
     *
     * @param record record to be added.
     * @return added record id.
     */
    I add(R record);

    /**
     * Checks if storage contains record with provided id.
     *
     * @param recordId record id to be checked.
     * @return true if storage contains record id/ false id it is not.
     */
    boolean contains(I recordId);

}
