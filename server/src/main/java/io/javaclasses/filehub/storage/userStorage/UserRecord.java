package io.javaclasses.filehub.storage.userStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import io.javaclasses.filehub.storage.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Data structure that represents user in the application and will be saved in the {@link UserRecordStorage}.
 */
@Immutable
public class UserRecord implements Record<UserId> {

    private static final Logger logger = LoggerFactory.getLogger(Registration.class);
    private final String login;
    private final String passwordHash;
    private final UserId id;


    /**
     * Returns instance of {@link UserRecord} class.
     *
     * @param userCredentials - user credentials that contains login and password.
     */
    public UserRecord(UserCredentials userCredentials) {
        this.login = userCredentials.login();
        this.passwordHash = createPasswordHash(userCredentials.password());
        this.id = new UserId(UUID.randomUUID().toString());
        logger.debug("New user record was created. User login: " + login + ".");
    }

    private String createPasswordHash(String password) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(UTF_8));
            byte[] digest = md.digest();
            hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return hash;
    }

    public String login() {
        return login;
    }

    public String passwordHash() {
        return passwordHash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserId id() {
        return id;
    }
}
