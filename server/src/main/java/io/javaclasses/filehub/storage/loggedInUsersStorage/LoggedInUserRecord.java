package io.javaclasses.filehub.storage.loggedInUsersStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.Record;
import io.javaclasses.filehub.storage.userStorage.UserId;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving token in the {@link LoggedInUsersStorage}.
 */
@Immutable
public final class LoggedInUserRecord implements Record<Token> {

    private final Token token;
    private final UserId userId;
    private final LocalDate expirationDate;

    /**
     * Creates instance of {@link LoggedInUserRecord} class.
     *
     * @param token          token value.
     * @param userId         user token.
     * @param expirationDate time for token expiration.
     */
    public LoggedInUserRecord(Token token, UserId userId, LocalDate expirationDate) {
        this.token = checkNotNull(token);
        this.userId = checkNotNull(userId);
        this.expirationDate = checkNotNull(expirationDate);
    }


    public UserId userId() {
        return userId;
    }

    public LocalDate expirationDate() {
        return expirationDate;
    }

    @Override
    public Token id() {
        return token;
    }
}
