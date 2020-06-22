package io.javaclasses.filehub.web;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.registrationProcess.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide functionality for deserialization of {@link Register} command from json.
 */
public class RegisterCommandDeserializer {

    private final Logger logger = LoggerFactory.getLogger(WebApp.class);

    /**
     * Deserialize {@link Register} command from json.
     *
     * @param json - json object.
     * @return {@link Register} command.
     */
    public Register deserialize(String json) {
        logger.debug("Trying to deserialize " + json + ".");
        Gson gson = new Gson();
        Register command = gson.fromJson(json, Register.class);
        logger.debug("Deserialization completed : " + command + ".");
        return command;
    }
}
