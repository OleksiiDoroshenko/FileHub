package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

/**
 * The {@link AuthenticationFilter} that is used when {@link Request} contains multi-part data.
 */
public class MultiPartAuthenticationFilter extends AuthenticationFilter {
    /**
     * Returns instance of {@link MultiPartAuthenticationFilter} class.
     *
     * @param loggedInUsersStorage logged in user storage.
     */
    public MultiPartAuthenticationFilter(LoggedInUsersStorage loggedInUsersStorage) {
        super(loggedInUsersStorage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Request request, Response response) throws Exception {

        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp",
                        100000000, 100000000, 1024));

        Part uploadedFile = request.raw().getPart("file");
        super.handle(request, response);
    }
}
