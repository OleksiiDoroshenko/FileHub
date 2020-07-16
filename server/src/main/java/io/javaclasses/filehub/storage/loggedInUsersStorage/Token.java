package io.javaclasses.filehub.storage.loggedInUsersStorage;

import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Token for {@link LoggedInUserRecord}.
 */
@Immutable
public final class Token implements RecordId {

    private final String value;

    /**
     * Creates instance of {@link Token} class.
     *
     * @param value token value.
     */
    public Token(String value) {
        this.value = checkNotNull(value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equal(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String value() {
        return value;
    }
}
