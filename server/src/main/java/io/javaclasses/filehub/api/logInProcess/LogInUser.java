package io.javaclasses.filehub.api.logInProcess;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@link Command} for logging into the FileHub application.
 */
@Immutable
public final class LogInUser implements Command {

    private final LoginName loginName;
    private final Password password;

    /**
     * Returns instance of {@link LogInUser} class.
     *
     * @param loginName user login name.
     * @param password  user password.
     */
    public LogInUser(LoginName loginName, Password password) {
        this.loginName = checkNotNull(loginName);
        this.password = checkNotNull(password);
    }

    public LoginName loginName() {
        return loginName;
    }

    public Password password() {
        return password;
    }

    @Override
    public String toString() {
        return "LogInUser{" +
                "loginName=" + loginName.value() +
                ", password=" + password.value() +
                '}';
    }
}
