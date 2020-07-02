package io.javaclasses.filehub.api.logInProcess;

import io.javaclasses.filehub.api.Command;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

public class LogInUser implements Command {

    private static final Logger logger = LoggerFactory.getLogger(LogInUser.class);
    private final LoginName loginName;
    private final Password password;

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
}
