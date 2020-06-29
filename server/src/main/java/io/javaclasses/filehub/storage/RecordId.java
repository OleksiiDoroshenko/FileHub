package io.javaclasses.filehub.storage;

/**
 * Abstract base for representing unique identifier for every {@link Record} in the application.
 */
public interface RecordId {

    /**
     * Returns record unique identifier.
     *
     * @return record id.
     */
    String id();
}
