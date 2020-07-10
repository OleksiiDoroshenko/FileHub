package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Type of the {@link FileRecord}.
 */
@Immutable
public final class FileType {

    private final String value;

    /**
     * Returns instance of {@link FileType} class.
     *
     * @param value type value.
     */
    public FileType(String value) {
        this.value = checkNotNull(value);
    }

    public String value() {
        return value;
    }
}
