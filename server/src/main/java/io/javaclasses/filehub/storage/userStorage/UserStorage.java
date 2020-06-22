package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Contains inner user storage.
 */
public class UserStorage implements Storage<UserRecord, UserId> {

    private final Map<UserId, UserRecord> storage;
    private int lastId;
    private static final Logger logger = LoggerFactory.getLogger(Registration.class);

    /**
     * Returns instance of {@link UserStorage} class.
     */
    public UserStorage() {
        storage = Collections.synchronizedMap(new HashMap<>());
        logger.debug("Created new user storage.");
    }

    /**
     * @param userId - user lastId.
     * @return - {@link UserRecord} by its lastId.
     */
    @Override
    public UserRecord get(UserId userId) {
        return storage.get(userId);
    }

    /**
     * Removes {@link UserRecord} by its lastId.
     *
     * @param userId user lastId.
     * @return - removed record.
     */
    @Override
    public UserRecord remove(UserId userId) {
        logger.info("User with " + userId + " was removed.");
        return storage.remove(userId);
    }

    /**
     * @return - all {@link UserRecord} from storage.
     */
    @Override
    public List<UserRecord> getAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Adds {@link UserRecord} to the storage.
     *
     * @param userRecord - user record to be added.
     * @return - added record lastId.
     */
    @Override
    public UserId add(UserRecord userRecord) {
        UserId id = generateId();
        storage.put(id, userRecord);
        logger.info("User " + userRecord.getLogin() + " was added to the storage.");
        return id;
    }

    /**
     * Checks if user storage contains record that was passed by params.
     *
     * @param userRecord - user record whose presence should be checked.
     * @return true if storage contains such user record login / false if it is not.
     */
    @Override
    public boolean contains(UserRecord userRecord) {

        return storage.values().stream().anyMatch(record ->
                record.getLogin().equals(userRecord.getLogin())
        );
    }

    /**
     * @return
     */
    private UserId generateId() {
        lastId++;
        return new UserId(lastId + "");
    }
}
