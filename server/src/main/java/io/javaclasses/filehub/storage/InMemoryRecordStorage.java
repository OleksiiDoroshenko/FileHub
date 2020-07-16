package io.javaclasses.filehub.storage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.RecordId;
import io.javaclasses.filehub.storage.RecordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * An abstract in-memory storage.
 *
 * <p>As the internal storage uses standard synchronized {@link HashMap}.</p>
 *
 * @param <R> specific {@link Record}.
 * @param <I> specific {@link RecordId}.
 */
public abstract class InMemoryRecordStorage<R extends Record<I>, I extends RecordId> implements RecordStorage<R, I> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final Map<I, R> storage = Collections.synchronizedMap(new HashMap<>());

    /**
     * Returns record by its id.
     *
     * @param id record id.
     * @return record by its id.
     */
    @Override
    public Optional<R> get(I id) {
        if (!storage.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(storage.get(id));
    }

    /**
     * Removes {@link Record} by its id.
     *
     * @param id record id.
     * @return removed record.
     */
    @Override
    public Optional<R> remove(I id) {

        if (logger.isInfoEnabled()) {
            logger.info("Record with " + id + " was removed.");
        }

        return Optional.of(storage.remove(id));
    }

    /**
     * @return all records from the storage.
     */
    @Override
    public List<R> all() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    /**
     * Adds {@link Record} to the storage.
     *
     * @param record record to be added.
     * @return added record id.
     */
    @Override
    public I add(R record) {
        storage.put(record.id(), record);
        if (logger.isInfoEnabled()) {
            logger.info("Record with  " + record.id() + " was added to the storage.");
        }
        return record.id();
    }

    /**
     * Checks if storage contains record with passed id.
     *
     * @param id record id which presence should be checked.
     * @return true if storage contains such record id / false if it is not.
     */
    @Override
    public boolean contains(I id) {
        return storage.keySet().stream().anyMatch(key ->
                key.value().equals(id.value()));
    }

    /**
     * Generates unique identifier.
     *
     * @return identifier.
     */
    public synchronized String generateId() {
        return UUID.randomUUID().toString();
    }

}
