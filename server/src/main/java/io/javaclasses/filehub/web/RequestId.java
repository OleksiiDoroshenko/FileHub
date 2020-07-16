package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import spark.Request;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service that provides functionality for getting identifier from client request {@link Request}.
 */
public final class RequestId {

    private static final String ID_PARAMETER = ":id";

    /**
     * Parses identifier from the client request {@link Request}.
     *
     * @param request client HTTP request.
     * @return folder identifier.
     */
    public static FolderId parse(Request request) {

        checkNotNull(request);

        String id = request.params(ID_PARAMETER);

        return new FolderId(id);
    }
}
