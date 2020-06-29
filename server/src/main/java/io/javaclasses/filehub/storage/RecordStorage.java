package io.javaclasses.filehub.storage;

import java.util.List;

/**
 * Abstract base storage for FileHub application.
 * Provide basic CRUD operations for stored items.
 *
 * @param <R> - specific storage record.
 * @param <I> - specific record id.
 */
public interface RecordStorage<R, I> {
    /**
     * Returns record by its id.
     *
     * @param id - specific record id.
     * @return record.
     */
    R get(I id);

    /**
     * Removes record from storage by it's id.
     *
     * @param id - specific record id.
     * @return removed record.
     */
    R remove(I id);

    /**
     * Returns all records from storage.
     *
     * @return record list.
     */
    List<R> all();

    /**
     * Adds record into the storage.
     *
     * @param record - record to be added.
     * @return added record id.
     */
    I add(R record);

    /**
     * Checks if storage contains record with provided id.
     *
     * @param recordId - record id to be checked.
     * @return true if storage contains record id/ false id it is not.
     */
    boolean contains(I recordId);

}
