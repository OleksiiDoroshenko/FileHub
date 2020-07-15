package io.javaclasses.fileHub.web;

import com.google.common.testing.NullPointerTester;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.web.RequestId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Request;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("RequestId service should: ")
public class RequestIdTest {

    @DisplayName("correctly parse id from the request.")
    @Test
    public void testRequestIdParsing() {
        Request request = createMockRequestWithValidId();
        FileSystemItemId id = RequestId.parse(request);

        assertNotNull(id, "Parsed id should not be null.");
    }

    @DisplayName("correctly parse id from the request.")
    @Test
    public void testThrowsExceptionIfRequestIdNull() {
        Request request = createMockRequestWithInvalidId();

        assertThrows(NullPointerException.class, () -> {
            RequestId.parse(request);
        }, "RequestId service does not throw NullPointerException when requested id is null, but should.");
    }

    @DisplayName("throw exception if parameters are null.")
    @Test
    public void nullPointerTest() {

        NullPointerTester tester = new NullPointerTester();

        tester.testAllPublicStaticMethods(RequestId.class);
    }

    private Request createMockRequestWithInvalidId() {
        return new Request() {
            @Override
            public String params(String param) {
                return null;
            }
        };
    }

    private Request createMockRequestWithValidId() {
        return new Request() {
            @Override
            public String params(String param) {
                return "id";
            }
        };
    }
}