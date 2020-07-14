package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUserRecord;
import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.loggedInUsersStorage.Token;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import io.javaclasses.filehub.web.ServerTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Process in the application that handles {@link LogInUser} command.
 */
public class LoggingIn implements SystemProcess<LogInUser, Token> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingIn.class);
    private static final Period EXPIRATION_INTERVAL = Period.ofDays(3);
    private final UserStorage userStorage;
    private final LoggedInUsersStorage loggedInUsersStorage;

    /**
     * Returns instance of {@link LoggingIn} class.
     *
     * @param userStorage          storage with registered users.
     * @param loggedInUsersStorage storage with tokens for registered users.
     */
    public LoggingIn(UserStorage userStorage, LoggedInUsersStorage loggedInUsersStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.loggedInUsersStorage = checkNotNull(loggedInUsersStorage);
    }

    /**
     * Handles {@link LogInUser} logIn.
     *
     * @param logIn user credentials.
     * @return user session token.
     * @throws UserNotRegisteredException      if user with provided in parameters
     *                                         does not exist in {@link UserStorage}.
     * @throws InvalidUserCredentialsException if user credentials are invalid.
     */
    @Override
    public Token handle(LogInUser logIn) {
        LoginName loginName = logIn.loginName();
        Password password = logIn.password();

        if (logger.isDebugEnabled()) {
            logger.debug(format("Log in process started with. Login: %s.", loginName.value()));
        }

        Optional<UserRecord> userRecord = userStorage.get(loginName);
        if (!userRecord.isPresent()) {

            if (logger.isErrorEnabled()) {
                logger.error("User with such login: " + loginName.value() + " does not exist.");
            }

            throw new UserNotRegisteredException("User with such login does not exist.");
        }

        UserRecord user = userRecord.get();
        String passwordHash = PasswordHasher.getHash(password);
        if (!user.password().equals(passwordHash)) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Wrong login name or password. Login: %s, Password: %s.",
                        loginName.value(), password.value()));
            }
            throw new InvalidUserCredentialsException("Wrong login or password.");
        }

        LocalDate expirationDate = calcExpirationDate();
        Token token = createToken();
        LoggedInUserRecord loggedIdUser = createLoggedInUser(user.id(), expirationDate, token);
        loggedInUsersStorage.add(loggedIdUser);

        if (logger.isDebugEnabled()) {
            logger.debug(format("New loggedIdUser was added. Token id: %s, User id: %s.",
                    loggedIdUser.id().value(), user.id()));
        }
        return loggedIdUser.id();
    }

    /**
     * Creates new logged in user with passed parameters.
     *
     * @param userId         user identifier.
     * @param expirationDate token expiration date.
     * @param token          token.
     * @return logged in user.
     */
    private LoggedInUserRecord createLoggedInUser(UserId userId, LocalDate expirationDate, Token token) {
        return new LoggedInUserRecord(token, userId, expirationDate);
    }

    /**
     * Creates new token.
     *
     * @return token.
     */
    private Token createToken() {
        return new Token(loggedInUsersStorage.generateId());
    }

    /**
     * Calculates token expiration date.
     *
     * @return expiration date.
     */
    private LocalDate calcExpirationDate() {
        return LocalDate.now(ServerTimeZone.get()).plus(EXPIRATION_INTERVAL);
    }
}
