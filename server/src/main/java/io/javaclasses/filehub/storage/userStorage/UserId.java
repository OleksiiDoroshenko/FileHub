package io.javaclasses.filehub.storage.userStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Identifier for {@link UserRecord}.
 */
@Immutable
public final class UserId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link UserId} class.
     *
     * @param value user identifier.
     */
    public UserId(String value) {
        this.value = checkNotNull(value);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "id: " + value;
    }
}
