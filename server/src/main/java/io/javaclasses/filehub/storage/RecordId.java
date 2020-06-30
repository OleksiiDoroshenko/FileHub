package io.javaclasses.filehub.storage;

/**
 * Abstract base identifier for every {@link Record} in the application.
 */
public interface RecordId {

    /**
     * Returns record unique identifier.
     *
     * @return record id.
     */
    String value();
}
