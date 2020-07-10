package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;

/**
 * Size of the {@link FileRecord}.
 */
@Immutable
public final class FileSize {

    private final long size;

    /**
     * Returns instance of {@link FileSize} class.
     *
     * @param size file size.
     */
    public FileSize(long size) {
        this.size = size;
    }

    public long size() {
        return size;
    }
}
