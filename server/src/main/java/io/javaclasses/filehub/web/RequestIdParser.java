package io.javaclasses.filehub.web;

import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FolderId;
import spark.Request;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Service that provides functionality for parsing {@link FolderId} and {@link FileId} identifiers from client
 * request {@link Request}.
 */
public final class RequestIdParser {

    private static final String ID_PARAMETER = ":id";

    /**
     * Parses {@link FolderId} from the client request {@link Request}.
     *
     * @param request client HTTP request.
     * @return folder identifier.
     */
    public static FolderId parseFolderId(Request request) {

        checkNotNull(request);

        String id = request.params(ID_PARAMETER);

        return new FolderId(id);
    }

    /**
     * Parses {@link FileId} from the client request {@link Request}.
     *
     * @param request client HTTP request.
     * @return folder identifier.
     */
    public static FileId parseFileId(Request request) {

        checkNotNull(request);

        String id = request.params(ID_PARAMETER);

        return new FileId(id);
    }
}
