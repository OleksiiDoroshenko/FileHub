package io.javaclasses.filehub.web.deserializers;

import com.google.gson.*;
import io.javaclasses.filehub.api.registrationProcess.LoginName;
import io.javaclasses.filehub.api.registrationProcess.Password;
import io.javaclasses.filehub.api.registrationProcess.RegisterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Deserializer for {@link RegisterUser} class.
 */
public class RegisterUserDeserializer implements JsonDeserializer<RegisterUser> {

    private static final Logger logger = LoggerFactory.getLogger(RegisterUserDeserializer.class);


    /**
     * Deserializes {@link RegisterUser} instance from json.
     * <p>
     * {@inheritDoc}
     * </p>
     *
     * @return deserialized class.
     */
    @Override
    public RegisterUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            JsonObject wrapper = (JsonObject) json;

            if (logger.isDebugEnabled()) {
                logger.debug("Deserialization started. Json: " + json + ".");
            }

            String loginValue = wrapper.getAsJsonPrimitive("login").getAsString();
            String passwordValue = wrapper.getAsJsonPrimitive("password").getAsString();

            RegisterUser command = new RegisterUser(new LoginName(loginValue), new Password(passwordValue));

            if (logger.isDebugEnabled()) {
                logger.debug("Deserialization completed. Object: " + command.toString() + ".");
            }
            return command;
        }catch (NullPointerException | ClassCastException e) {
            throw new JsonParseException("Can not parse request body.");
        }
    }
}
