package io.javaclasses.filehub.storage.folderStorage;

import com.google.errorprone.annotations.Immutable;
import io.javaclasses.filehub.storage.RecordId;

import javax.annotation.Nullable;

/**
 * An identifier for {@link FolderRecord}.
 */
@Immutable
public final class FolderId implements RecordId {

    private final String value;

    /**
     * Returns instance of {@link FolderId} class.
     *
     * @param value value of identifier.
     */
    public FolderId(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }
}
