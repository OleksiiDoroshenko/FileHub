package io.javaclasses.fileHub.web.routes;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.userStorage.UserRecordStorage;
import io.javaclasses.filehub.web.routes.RegistrationRoute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import spark.Request;
import spark.Response;

import java.util.stream.Stream;

import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("RegistrationRoute should: ")
public class RegistrationRouteTest {

    @DisplayName("process valid requests.")
    @Test
    public void validRequestsTest() {
        UserRecordStorage storage = new UserRecordStorage();
        RegistrationRoute route = new RegistrationRoute(storage);

        String validRequestBody = "{\"login\":\"test\", \"password\":\"test123456\"}";

        Request request = createMockRequest(validRequestBody);
        Response response = createMockResponse();

        try {

            route.handle(request, response);
            assertEquals(response.status(), SC_ACCEPTED,
                    "Handle method throws exception when request is valid.");
        } catch (Exception exception) {

            fail("RegistrationRoute should not throw any exceptions.");
        }
    }

    private static Stream<Arguments> invalidRequestBody() {

        return Stream.of(
                Arguments.of("{\"log\":\"test\", \"password\":\"test123456\"}"),
                Arguments.of("<p></p>"),
                Arguments.of("Hello")
        );
    }


    @DisplayName("return error responses when requests are invalid.")
    @ParameterizedTest
    @MethodSource("invalidRequestBody")
    public void invalidRequestsTest(String body) {
        UserRecordStorage storage = new UserRecordStorage();
        RegistrationRoute route = new RegistrationRoute(storage);

        Request request = createMockRequest(body);
        Response response = createMockResponse();

        try {

            route.handle(request, response);
            assertEquals(response.status(), SC_BAD_REQUEST,
                    "Handle method does not throws exception when request is invalid.");
        } catch (Exception exception) {

            fail("RegistrationRoute should not throw any exceptions.");
        }
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();
        tester.setDefault(UserRecordStorage.class, new UserRecordStorage());

        tester.testAllPublicConstructors(RegistrationRoute.class);
        tester.testAllPublicStaticMethods(RegistrationRoute.class);
    }

    private Response createMockResponse() {
        return new Response() {
            int status;

            @Override
            public void type(String contentType) {
            }

            @Override
            public void status(int statusCode) {
                status = statusCode;
            }

            @Override
            public int status() {
                return status;
            }
        };
    }

    private Request createMockRequest(String body) {
        return new Request() {
            @Override
            public String body() {
                return body;
            }
        };
    }


}
