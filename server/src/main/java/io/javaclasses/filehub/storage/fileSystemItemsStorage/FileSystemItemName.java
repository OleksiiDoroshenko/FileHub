package io.javaclasses.filehub.storage.fileSystemItemsStorage;


import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The name of the {@link FolderRecord}.
 */
@Immutable
public final class FileSystemItemName {

    private final String value;

    /**
     * Returns instance of {@link FileSystemItemName} class.
     *
     * @param value folder name.
     */
    public FileSystemItemName(String value) {
        this.value = checkNotNull(value);
    }

    public String value() {
        return value;
    }
}
