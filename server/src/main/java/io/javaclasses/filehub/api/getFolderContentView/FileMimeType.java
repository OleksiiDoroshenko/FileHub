package io.javaclasses.filehub.api.getFolderContentView;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Mime type of the {@link FileRecord}.
 */
@Immutable
public final class FileMimeType {

    private final String value;

    /**
     * Creates instance of {@link FileMimeType} class.
     *
     * @param value mimeType value.
     */
    public FileMimeType(String value) {
        this.value = checkNotNull(value);
    }

    public String value() {
        return value;
    }
}
