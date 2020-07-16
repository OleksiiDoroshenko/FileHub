package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.loggedInUsersStorage.LoggedInUsersStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;

/**
 * The {@link AuthenticationFilter} that is used when {@link Request} contains multi-part data.
 */
public class MultiPartAuthenticationFilter extends AuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(MultiPartAuthenticationFilter.class);


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

        request.raw().getParts();

        if (logger.isDebugEnabled()) {
            logger.debug("Request parts {}.", request.raw().getParts());
        }

        super.handle(request, response);
    }
}
