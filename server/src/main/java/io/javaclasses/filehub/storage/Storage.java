package io.javaclasses.filehub.storage;

import java.util.List;

/**
 * Server storage.
 *
 * @param <Record>   - specific storage record.
 * @param <RecordId> - specific record id.
 */
public interface Storage<Record, RecordId> {
    /**
     * @param id - specific record id.
     * @return - record by it's id.
     */
    Record get(RecordId id);

    /**
     * Removes record from storage by it's id.
     *
     * @param id - specific record id.
     * @return - removed record.
     */
    Record remove(RecordId id);

    /**
     * @return - all records from storage.
     */
    List<Record> getAll();

    /**
     * Adds record into the storage.
     *
     * @param record - record to be added.
     * @return - added record id.
     */
    RecordId add(Record record);

    /**
     * Checks if storage contains record..
     *
     * @param record - record to be checked.
     * @return - true if storage contains record / false id it is not.
     */
    boolean contains(Record record);
}
