package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import jdk.nashorn.internal.ir.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Tiny type for user login name.
 */
@Immutable
public class LoginName {

    private final String loginName;

    /**
     * Returns instance of {@link LoginName} class.
     *
     * <p>
     * Throws {@link InvalidUserCredentialsException} if login name is not valid.
     * </p>
     *
     * @param value - user login name.
     */
    public LoginName(String value) {
        validate(value);
        this.loginName = value;
    }

    private void validate(String value) {

        int minLoginNameLength = 4;
        checkNotNull(value);

        if (value.length() < minLoginNameLength) {

            throw new InvalidUserCredentialsException("User login is too short.");
        }
    }

    public String value() {
        return loginName;
    }


}
