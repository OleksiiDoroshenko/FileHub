package io.javaclasses.filehub.storage.tokenStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Token for {@link LoggedIdUserRecord}.
 */
@Immutable
public final class Token implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link Token} class.
     *
     * @param value token value.
     */
    public Token(String value) {
        this.value = checkNotNull(value);
    }


    @Override
    public String value() {
        return value;
    }
}
