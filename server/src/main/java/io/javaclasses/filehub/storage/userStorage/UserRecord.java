package io.javaclasses.filehub.storage.userStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving user in the {@link UserStorage}.
 */
@Immutable
public final class UserRecord implements Record<UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final LoginName loginName;
    private final String password;
    private final UserId id;


    /**
     * Returns instance of {@link UserRecord} class.
     *
     * @param id        user id.
     * @param loginName user login.
     * @param password  user password.
     */
    public UserRecord(UserId id, LoginName loginName, String password) {
        this.loginName = checkNotNull(loginName);
        this.password = checkNotNull(password);
        this.id = checkNotNull(id);

        if (logger.isDebugEnabled()) {
            logger.debug("New user record was created. User login: " + loginName.value() + ".");
        }
    }

    public LoginName loginName() {
        return loginName;
    }

    public String password() {
        return password;
    }

    @Override
    public UserId id() {
        return id;
    }
}
