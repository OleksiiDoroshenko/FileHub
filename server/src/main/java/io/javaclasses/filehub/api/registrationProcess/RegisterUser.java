package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.storage.userStorage.UserRecord;
import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import com.google.errorprone.annotations.Immutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Command} for new {@link UserRecord} registration.
 */
@Immutable
public final class RegisterUser implements Command {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUser.class);
    private final LoginName loginName;
    private final Password password;

    /**
     * Creates instance of {@link RegisterUser} command.
     *
     * <p>
     * Validates passed parameters and throws {@link InvalidUserCredentialsException}
     * if at least one of them is invalid.
     * </p>
     *
     * @param loginName user login.
     * @param password  user password.
     */
    public RegisterUser(LoginName loginName, Password password) {

        if (logger.isDebugEnabled()) {
            logger.debug("Trying to create Register command.");
        }

        this.loginName = checkNotNull(loginName);
        this.password = checkNotNull(password);

        if (logger.isDebugEnabled()) {
            logger.debug("Register command was created. Login: " + loginName.value() +
                    ", password: " + password.value() + ".");
        }
    }

    public LoginName loginName() {
        return loginName;
    }

    public Password password() {
        return password;
    }

    @Override
    public String toString() {
        return "RegisterUser{" +
                "loginName=" + loginName.value() +
                ", password=" + password.value() +
                '}';
    }
}
