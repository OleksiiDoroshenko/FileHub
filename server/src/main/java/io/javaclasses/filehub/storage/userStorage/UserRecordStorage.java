package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.RecordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Storage for in-memory saving {@link UserRecord}.
 */
public class UserRecordStorage implements RecordStorage<UserRecord, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final Map<UserId, UserRecord> storage = Collections.synchronizedMap(new HashMap<>());

    /**
     * @param id user id.
     * @return {@link UserRecord} by its lastId.
     */
    @Override
    public Optional<UserRecord> get(UserId id) {
        return Optional.of(storage.get(id));
    }

    /**
     * Removes {@link UserRecord} by its lastId.
     *
     * @param id user id.
     * @return removed record.
     */
    @Override
    public Optional<UserRecord> remove(UserId id) {
        logger.info("User with " + id + " was removed.");
        return Optional.of(storage.remove(id));
    }

    /**
     * @return all {@link UserRecord} from storage.
     */
    @Override
    public List<UserRecord> all() {

        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    /**
     * Adds {@link UserRecord} to the storage.
     *
     * @param record user record to be added.
     * @return added record lastId.
     */
    @Override
    public UserId add(UserRecord record) {

        LoginName loginName = record.loginName();
        if (!contains(loginName)) {

            storage.put(record.id(), record);
        } else {

            if (get(loginName).isPresent()) {

                UserId id = get(loginName).get().id();
                UserRecord newRecord = createRecordFromExisting(id, record);
                storage.put(newRecord.id(), newRecord);
            }
        }

        logger.info("User " + loginName + " was added to the storage.");
        return record.id();
    }

    private UserRecord createRecordFromExisting(UserId id, UserRecord record) {
        return new UserRecord(id, record.loginName(), record.password());
    }

    /**
     * Returns {@link Optional<UserRecord>} by {@link LoginName}.
     *
     * @param loginName user login name.
     * @return {@link Optional<UserRecord>}.
     */
    public Optional<UserRecord> get(LoginName loginName) {
        return storage.values().stream().filter(item ->
                item.loginName().value().equals(loginName.value())).findAny();
    }

    /**
     * Generates {@link UserId}.
     *
     * @return user id.
     */
    public synchronized UserId generateId() {

        String id = UUID.randomUUID().toString();
        return new UserId(id);
    }

    /**
     * Checks if user storage contains record id that was passed by params.
     *
     * @param id user record id whose presence should be checked.
     * @return true if storage contains such user record id login / false if it is not.
     */
    @Override
    public boolean contains(UserId id) {
        return storage.keySet().stream().anyMatch(key ->
                key.value().equals(id.value()));
    }

    /**
     * Checks if storage contains user with such login that was passed by params.
     *
     * @param loginName user loginName.
     * @return true if storage contains such user record / false if it is not.
     */
    public boolean contains(LoginName loginName) {
        return storage.values().stream().anyMatch(record ->
                record.loginName().value().equals(loginName.value()));
    }

}
