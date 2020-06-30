package io.javaclasses.filehub.api.registrationProcess;

import io.javaclasses.filehub.web.InvalidUserCredentialsException;
import jdk.nashorn.internal.ir.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Tiny type for user password.
 */
@Immutable
public class Password {

    private final String value;

    /**
     * Returns instance of {@link Password} class.
     *
     * <p>
     * Throws {@link InvalidUserCredentialsException} if password is not valid.
     * </p>
     *
     * @param value - user password.
     */
    public Password(String value) {

        this.value = validate(value);
    }


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
