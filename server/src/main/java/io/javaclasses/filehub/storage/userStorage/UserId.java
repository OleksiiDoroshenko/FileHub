package io.javaclasses.filehub.storage.userStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object that represents unique identifier for {@link UserRecord}.
 */
@Immutable
public class UserId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link UserId} class.
     *
     * @param value - user unique identifier.
     */
    public UserId(String value) {
        checkNotNull(value);
        this.value = value;
    }

    @Override
    public String id() {
        return value;
    }

    @Override
    public String toString() {
        return "id: " + value;
    }
}
