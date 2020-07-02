package io.javaclasses.filehub.storage.tokenStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving token in the {@link TokenStorage}.
 */
@Immutable
public final class TokenRecord implements Record<TokenId> {

    private final TokenId id;
    private final UserId userId;
    private final TokenValue token;

    /**
     * Returns instance of {@link TokenRecord} class.
     *
     * @param id     token id.
     * @param userId user id.
     */
    public TokenRecord(TokenId id, UserId userId) {
        this.id = checkNotNull(id);
        this.userId = checkNotNull(userId);
        this.token = new TokenValue(generateValue());
    }

    /**
     * Generates unique token value.
     *
     * @return token value.
     */
    private String generateValue() {
        return UUID.randomUUID().toString();
    }

    public TokenValue token() {
        return token;
    }

    public UserId userId() {
        return userId;
    }

    @Override
    public TokenId id() {
        return id;
    }
}
