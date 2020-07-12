package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;

/**
 * Size of the {@link FileRecord}.
 */
@Immutable
public final class FileSize {

    private final long value;

    /**
     * Returns instance of {@link FileSize} class.
     *
     * @param value file size.
     */
    public FileSize(long value) {

        if (value < 0) {
            throw new IllegalArgumentException("File size can not be negative.");
        }
        this.value = value;
    }

    public long value() {
        return value;
    }
}
