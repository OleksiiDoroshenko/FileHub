package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

@Immutable
public final class FolderId implements RecordId {

    private final String value;

    public FolderId(String value) {
        this.value = checkNotNull(value);
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "FolderId{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderId folderId = (FolderId) o;
        return Objects.equal(value, folderId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
