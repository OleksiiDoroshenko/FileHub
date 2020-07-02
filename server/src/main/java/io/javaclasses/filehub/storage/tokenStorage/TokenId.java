package io.javaclasses.filehub.storage.tokenStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Identifier for {@link TokenRecord}.
 */
@Immutable
public final class TokenId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link TokenId} class.
     *
     * @param value token value.
     */
    public TokenId(String value) {
        this.value = checkNotNull(value);
    }


    @Override
    public String value() {
        return value;
    }
}
