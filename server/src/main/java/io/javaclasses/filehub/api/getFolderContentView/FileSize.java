package io.javaclasses.filehub.api.getFolderContentView;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;

import static com.google.common.base.Preconditions.*;

/**
 * Size of the {@link FileRecord}.
 */
@Immutable
public final class FileSize {

    private final long value;

    /**
     * Returns instance of {@link FileSize} class.
     *
     * @param value file size in bites.
     */
    public FileSize(long value) {

        checkArgument(value >= 0, "File size can not be negative.");
        this.value = value;
    }

    public long value() {
        return value;
    }
}
