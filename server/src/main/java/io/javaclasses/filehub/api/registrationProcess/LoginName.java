package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import jdk.nashorn.internal.ir.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Tiny type for user login name.
 */
@Immutable
public final class LoginName {

    private final String loginName;

    /**
     * Returns instance of {@link LoginName} class.
     *
     * @param value user login name.
     * @throws InvalidUserCredentialsException if login name is not valid.
     */
    public LoginName(String value) {
        this.loginName = validate(value);
    }

    /**
     * Validates user login by specific rules.
     *
     * @param value user login.
     * @return user ligin.
     * @throws InvalidUserCredentialsException if user login is invalid.
     */
    private String validate(String value) {

        int minLoginNameLength = 4;
        checkNotNull(value);

        if (value.length() < minLoginNameLength) {

            throw new InvalidUserCredentialsException("User login is too short.");
        }
        return value;
    }

    public String value() {
        return loginName;
    }


}
