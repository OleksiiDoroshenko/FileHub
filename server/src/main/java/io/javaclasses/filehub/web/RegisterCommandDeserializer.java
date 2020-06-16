package io.javaclasses.filehub.web;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.registrationProcess.Register;

public class RegisterCommandDeserializer {

    public Register deserialize(String json) {

        Gson gson = new Gson();
        Register register = gson.fromJson(json, Register.class);
        System.out.println(register);
        return register;
    }
}
