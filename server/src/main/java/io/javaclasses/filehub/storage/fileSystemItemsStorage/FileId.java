package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

@Immutable
public final class FileId implements RecordId {

    private final String value;

    public FileId(String value) {
        this.value = checkNotNull(value);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "FileId{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileId fileId = (FileId) o;
        return Objects.equal(value, fileId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
