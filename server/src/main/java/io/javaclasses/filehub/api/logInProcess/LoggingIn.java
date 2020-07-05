package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.storage.tokenStorage.LoggedIdUserRecord;
import io.javaclasses.filehub.storage.tokenStorage.LoggedInUsersStorage;
import io.javaclasses.filehub.storage.tokenStorage.Token;
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
     * @throws UserAlreadyExistsException      if user with provided in parameters
     *                                         logIn already exists in {@link UserStorage}.
     * @throws InvalidUserCredentialsException if user credentials are invalid.
     */
    @SuppressWarnings("LocalDateTemporalAmount")
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
        if (!(user.loginName().value().equals(loginName.value()) &&
                user.password().equals(passwordHash))) {

            if (logger.isErrorEnabled()) {
                logger.error(format("Wrong login name or password. Login: %s, Password: %s.",
                        loginName.value(), password.value()));
            }
            throw new InvalidUserCredentialsException("Wrong login or password.");
        }

        LocalDate expirationDate = LocalDate.now(ServerTimeZone.get()).plus(EXPIRATION_INTERVAL);
        Token token = new Token(loggedInUsersStorage.generateId());
        LoggedIdUserRecord loggedIdUser = new LoggedIdUserRecord(token, user.id(), expirationDate);
        loggedInUsersStorage.add(loggedIdUser);

        if (logger.isDebugEnabled()) {
            logger.debug(format("New loggedIdUser was added. Token id: %s, User id: %s.",
                    loggedIdUser.id().value(), user.id()));
        }
        return loggedIdUser.id();
    }
}
