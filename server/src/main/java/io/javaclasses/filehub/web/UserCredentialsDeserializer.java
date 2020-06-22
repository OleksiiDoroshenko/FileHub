package io.javaclasses.filehub.web;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.registrationProcess.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide functionality for deserialization of {@link UserCredentials} class from json.
 */
public class UserCredentialsDeserializer {

    private final Logger logger = LoggerFactory.getLogger(UserCredentialsDeserializer.class);

    /**
     * Deserialize {@link UserCredentials} command from json.
     *
     * @param json - json object.
     * @return {@link UserCredentials} class.
     */
    public UserCredentials deserialize(String json) {
        logger.debug("Trying to deserialize " + json + ".");
        Gson gson = new Gson();
        UserCredentials userCredentials = gson.fromJson(json, UserCredentials.class);
        logger.debug("Deserialization completed : " + userCredentials + ".");
        return userCredentials;
    }
}
