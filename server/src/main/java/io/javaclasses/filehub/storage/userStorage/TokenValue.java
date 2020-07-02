package io.javaclasses.filehub.storage.userStorage;

import static com.google.common.base.Preconditions.checkNotNull;

public class TokenValue {

    private final String token;

    public TokenValue(String token) {
        this.token = checkNotNull(token);
    }

    public String value() {
        return token;
    }
}
