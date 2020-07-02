package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.api.registrationProcess.UserAlreadyExistsException;
import io.javaclasses.filehub.storage.tokenStorage.TokenId;
import io.javaclasses.filehub.storage.tokenStorage.TokenRecord;
import io.javaclasses.filehub.storage.tokenStorage.TokenStorage;
import io.javaclasses.filehub.storage.tokenStorage.TokenValue;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

/**
 * Process in the application that handles {@link LogInUser} command.
 */
public class LoggingIn implements SystemProcess<LogInUser, TokenValue> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingIn.class);

    private final UserStorage userStorage;
    private final TokenStorage tokenStorage;

    /**
     * Returns instance of {@link LoggingIn} class.
     *
     * @param userStorage  storage with registered users.
     * @param tokenStorage storage with tokens for registered users.
     */
    public LoggingIn(UserStorage userStorage, TokenStorage tokenStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.tokenStorage = checkNotNull(tokenStorage);
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
    @Override
    public TokenValue handle(LogInUser logIn) {
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

        TokenRecord token = new TokenRecord(new TokenId(tokenStorage.generateId()), user.id());
        tokenStorage.add(token);

        if (logger.isDebugEnabled()) {
            logger.debug(format("New token was added. Token id: %s, User id: %s.", token.id(), user.id()));
        }
        return token.token();
    }
}
