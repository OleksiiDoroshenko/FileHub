package io.javaclasses.filehub.api;

import io.javaclasses.filehub.api.registrationProcess.Password;
import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Service for hashing {@link Password}.
 */
@Immutable
public final class PasswordHasher {

    private static final String ALGORITHM = "MD5";

    /**
     * Creates {@link Password} hash.
     *
     * @param password user password.
     * @return created hash.
     */
    public static String getHash(Password password) {

        checkNotNull(password);
        String hash = "";
        try {

            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(password.value().getBytes(UTF_8));
            byte[] digest = md.digest();
            hash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalArgumentException(e.getMessage());
        }
        return hash;

    }
}
