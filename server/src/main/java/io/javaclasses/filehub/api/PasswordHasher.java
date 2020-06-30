package io.javaclasses.filehub.api;

import io.javaclasses.filehub.api.registrationProcess.Password;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class PasswordHasher {

    private static final String ALGORITHM = "MD5";

    public static String getHash(Password password) {

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
