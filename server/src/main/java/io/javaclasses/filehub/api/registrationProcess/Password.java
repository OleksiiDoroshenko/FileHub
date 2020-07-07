package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Tiny type for user password.
 */
@Immutable
public final class Password {

    private final String value;

    /**
     * Returns instance of {@link Password} class.
     *
     * @param value user password.
     * @throws InvalidUserCredentialsException if password is not valid.
     */
    public Password(String value) {

        this.value = validate(value);
    }

    /**
     * Validates user password by specific rules.
     *
     * @param value user password.
     * @return user password.
     * @throws InvalidUserCredentialsException if user password is invalid.
     */
    private String validate(String value) {

        int minPasswordLength = 8;
        checkNotNull(value);

        if (value.length() < minPasswordLength) {

            throw new InvalidUserCredentialsException("User password is too short.");
        }

        return value;
    }


    public String value() {
        return value;
    }
}
