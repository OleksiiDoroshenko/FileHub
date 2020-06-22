package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.storage.RecordId;

/**
 * Contains user id.
 */
public class UserId implements RecordId {
    private final String id;

    /**
     * Returns instance of {@link UserId} class.
     *
     * @param id - user id.
     */
    public UserId(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id;
    }
}
