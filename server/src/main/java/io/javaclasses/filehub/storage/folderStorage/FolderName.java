package io.javaclasses.filehub.storage.folderStorage;


import com.google.errorprone.annotations.Immutable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The name of the {@link FolderRecord}.
 */
@Immutable
public final class FolderName {

    private final String value;

    /**
     * Returns instance of {@link FolderName} class.
     *
     * @param value folder name.
     */
    public FolderName(String value) {
        this.value = checkNotNull(value);
    }

    public String value() {
        return value;
    }
}
