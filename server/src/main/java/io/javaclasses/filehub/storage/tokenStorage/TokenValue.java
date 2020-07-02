package io.javaclasses.filehub.storage.tokenStorage;

import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;
@Immutable
public final class TokenValue {

    private final String token;

    public TokenValue(String token) {
        this.token = checkNotNull(token);
    }

    public String value() {
        return token;
    }
}
