package io.javaclasses.filehub.web.deserializers;

import com.google.gson.*;
import io.javaclasses.filehub.api.logInProcess.LogInUser;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Deserializer for {@link LogInUser} class.
 */
public class LogInUserDeserializer implements JsonDeserializer<LogInUser> {

    private static final Logger logger = LoggerFactory.getLogger(LogInUserDeserializer.class);


    /**
     * Deserializes {@link LogInUser} instance from json.
     * <p>
     * {@inheritDoc}
     * </p>
     *
     * @return deserialized class.
     */
    @Override
    public LogInUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject wrapper = (JsonObject) json;

        if (logger.isDebugEnabled()) {
            logger.debug("Deserialization started. Json: " + json + ".");
        }

        String loginValue = wrapper.getAsJsonPrimitive("login").getAsString();
        String passwordValue = wrapper.getAsJsonPrimitive("password").getAsString();

        LogInUser command = new LogInUser(new LoginName(loginValue), new Password(passwordValue));

        if (logger.isDebugEnabled()) {
            logger.debug("Deserialization completed. Object: " + command.toString() + ".");
        }
        return command;
    }
}
