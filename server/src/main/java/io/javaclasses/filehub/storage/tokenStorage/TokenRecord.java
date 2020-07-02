package io.javaclasses.filehub.storage.tokenStorage;

import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.userStorage.TokenValue;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class TokenRecord implements Record<TokenId> {

    private final TokenId id;
    private final UserId userId;
    private final TokenValue value;

    public TokenRecord(TokenId id, UserId userId) {
        this.id = checkNotNull(id);
        this.userId = checkNotNull(userId);
        this.value = new TokenValue(generateValue());
    }

    private String generateValue() {
        return UUID.randomUUID().toString();
    }

    public TokenValue value() {
        return value;
    }

    public UserId userId() {
        return userId;
    }

    @Override
    public TokenId id() {
        return id;
    }
}
