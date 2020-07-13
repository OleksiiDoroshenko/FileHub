package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import spark.Request;

/**
 * Service that parses folder identifier from the client request.
 */
public final class RequestId {

    public static final String ID_PARAMETER = ":id";

    /**
     * Parses folder identifier from the client HTTP request.
     *
     * @param request client HTTP request.
     * @return folder identifier.
     */
    public static FileSystemItemId parse(Request request) {

        String id = request.params(ID_PARAMETER);

        return new FileSystemItemId(id);
    }
}
