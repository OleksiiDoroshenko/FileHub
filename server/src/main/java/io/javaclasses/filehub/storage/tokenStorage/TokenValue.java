package io.javaclasses.filehub.storage.tokenStorage;

import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

@Immutable
/**
 * Tiny object for token value.
 */
public final class TokenValue {

    private final String token;

    /**
     * Return instance of {@link TokenValue} class.
     *
     * @param token token value.
     */
    public TokenValue(String token) {
        this.token = checkNotNull(token);
    }

    public String value() {
        return token;
    }
}
