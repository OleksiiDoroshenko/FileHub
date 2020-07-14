package io.javaclasses.filehub.storage.loggedInUsersStorage;

import io.javaclasses.filehub.storage.InMemoryRecordStorage;
import io.javaclasses.filehub.web.ServerTimeZone;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Storage for saving {@link LoggedInUserRecord}.
 */
public class LoggedInUsersStorage extends InMemoryRecordStorage<LoggedInUserRecord, Token> {

    /**
     * Returns token record by its value.
     *
     * <p>Not return token if its expiration date passed.</p>
     *
     * @param token token value.
     * @return token record.
     */
    @Override
    public Optional<LoggedInUserRecord> get(Token token) {
        Optional<LoggedInUserRecord> record = all().stream().filter(item ->
                item.id().value().equals(token.value())).findAny();

        if (!record.isPresent()) {
            return Optional.empty();
        }
        if (isTokenExpired(record.get())) {
            super.remove(record.get().id());
            return Optional.empty();
        }

        return record;
    }

    /**
     * Checks if token of the record is expired.
     *
     * @param record record to be checked.
     * @return true if it is expired / folder id it is not..
     */
    private boolean isTokenExpired(LoggedInUserRecord record) {
        return record.expirationDate().isBefore(LocalDate.now(ServerTimeZone.get()));
    }
}
