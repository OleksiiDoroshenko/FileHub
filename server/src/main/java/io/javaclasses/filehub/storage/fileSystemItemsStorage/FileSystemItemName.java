package io.javaclasses.filehub.storage.fileSystemItemsStorage;


import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The name of the {@link FolderRecord}.
 */
@Immutable
public final class FileSystemItemName {

    private final String value;

    /**
     * Creates instance of {@link FileSystemItemName} class.
     *
     * @param value folder name.
     */
    public FileSystemItemName(String value) {
        this.value = checkNotNull(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSystemItemName name = (FileSystemItemName) o;
        return Objects.equal(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
