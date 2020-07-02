package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.api.PasswordHasher;
import io.javaclasses.filehub.api.SystemProcess;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.storage.tokenStorage.TokenId;
import io.javaclasses.filehub.storage.tokenStorage.TokenRecord;
import io.javaclasses.filehub.storage.tokenStorage.TokenStorage;
import io.javaclasses.filehub.storage.userStorage.TokenValue;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.storage.userStorage.UserStorage;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoggingIn implements SystemProcess<LogInUser, TokenValue> {

    private static final Logger logger = LoggerFactory.getLogger(LoggingIn.class);

    private final UserStorage userStorage;
    private final TokenStorage tokenStorage;

    public LoggingIn(UserStorage userStorage, TokenStorage tokenStorage) {
        this.userStorage = checkNotNull(userStorage);
        this.tokenStorage = checkNotNull(tokenStorage);
    }

    @Override
    public TokenValue handle(LogInUser command) {
        LoginName loginName = command.loginName();
        Password password = command.password();

        Optional<UserRecord> userRecord = userStorage.get(loginName);
        if (!userRecord.isPresent()) {

            throw new UserNotRegisteredException("User with such login does not exist.");
        }

        UserRecord user = userRecord.get();
        String passwordHash = PasswordHasher.getHash(password);
        if (!(user.loginName().value().equals(loginName.value()) &&
                user.password().equals(passwordHash))) {

            throw new InvalidUserCredentialsException("Wrong login or password.");
        }

        TokenRecord token = new TokenRecord(new TokenId(tokenStorage.generateId()), user.id());
        tokenStorage.add(token);

        return token.value();
    }
}
