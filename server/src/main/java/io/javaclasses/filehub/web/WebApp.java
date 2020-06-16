package io.javaclasses.filehub.web;

import com.google.gson.Gson;
import io.javaclasses.filehub.api.registrationProcess.Register;
import io.javaclasses.filehub.api.registrationProcess.Registration;
import io.javaclasses.filehub.storage.userStorage.UserId;
import io.javaclasses.filehub.storage.userStorage.UserStorage;

import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class WebApp {

    private static UserStorage userStorage;
    private static RegisterCommandDeserializer serializer;

    public static void main(String[] args) {

        userStorage = new UserStorage();
        serializer = new RegisterCommandDeserializer();

        staticFiles.location("/webclient");

        post("/register", (req, res) -> {
            System.out.println(req.body());
            try {
                Gson gson = new Gson();
                Register command = gson.fromJson(req.body(), Register.class);
                UserId userId = new Registration(userStorage).handle(command);
                System.out.println(userId.id());
                return 200;
            } catch (InvalidUserDataException e) {

                res.status(400);
                res.body(e.getMessage());
                return res;
            }
        });


    }
}
