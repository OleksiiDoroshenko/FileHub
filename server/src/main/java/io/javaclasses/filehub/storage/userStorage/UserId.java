package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contains user value.
 */
public class UserId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link UserId} class.
     *
     * @param value - user id.
     */
    public UserId(String value) {
        checkNotNull(value);
        this.value = value;
    }

    /**
     * @return user id.
     */
    public String id() {
        return value;
    }

    @Override
    public String toString() {
        return "value: " + value;
    }
}
