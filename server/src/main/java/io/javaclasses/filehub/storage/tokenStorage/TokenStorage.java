package io.javaclasses.filehub.storage.tokenStorage;

import io.javaclasses.filehub.storage.InMemoryRecordStorage;

import java.util.Optional;

/**
 * Storage for saving {@link TokenRecord}.
 */
public class TokenStorage extends InMemoryRecordStorage<TokenRecord, TokenId> {

    /**
     * Returns token record by its value.
     *
     * @param token token value.
     * @return token record.
     */
    public Optional<TokenRecord> get(TokenValue token) {
        return all().stream().filter(item ->
                item.token().value().equals(token.value())).findAny();
    }
}
