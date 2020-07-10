package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

/**
 * An identifier for {@link FolderRecord} and {@link FileRecord}.
 */
@Immutable
public final class FileSystemItemId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link FileSystemItemId} class.
     *
     * @param value value of identifier.
     */
    public FileSystemItemId(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemItemId that = (FileSystemItemId) o;
        return Objects.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
