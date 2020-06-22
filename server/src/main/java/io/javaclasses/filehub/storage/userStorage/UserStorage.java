package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Contains inner user storage.
 */
public class UserStorage implements Storage<UserRecord, UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final Map<UserId, UserRecord> storage;


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
    public List<UserRecord> all() {
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
        storage.put(userRecord.id(), userRecord);
        logger.info("User " + userRecord.login() + " was added to the storage.");
        return userRecord.id();
    }

    /**
     * Checks if user storage contains record id that was passed by params.
     *
     * @param userId - user record id whose presence should be checked.
     * @return true if storage contains such user record id login / false if it is not.
     */
    @Override
    public boolean contains(UserId userId) {
        return storage.keySet().stream().anyMatch(key ->
                key.equals(userId.id()));
    }

    /**
     * Checks if storage contains user with such login that was passed by params.
     *
     * @param userCredentials - user credentials.
     * @return true if storage contains such user record / false if it is not.
     */
    public boolean containsUser(UserCredentials userCredentials) {
        return storage.values().stream().anyMatch(record ->
                record.login().equals(userCredentials.login()));
    }
}
