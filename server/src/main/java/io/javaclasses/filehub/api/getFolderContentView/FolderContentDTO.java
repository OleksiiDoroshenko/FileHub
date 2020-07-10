package io.javaclasses.filehub.api.getFolderContentView;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.List;

/**
 * DTO for file system folder content.
 *
 * <p>Include files and folders.</p>
 */
public final class FolderContentDTO {

    @Nullable
    @SerializedName("files")
    private final List<FileDTO> files;

    @Nullable
    @SerializedName("folders")
    private final List<FolderDTO> folders;

    /**
     * Returns instance of {@link FolderContentDTO} class.
     *
     * @param files   list of files.
     * @param folders list of folders.
     */
    FolderContentDTO(@Nullable List<FileDTO> files, @Nullable List<FolderDTO> folders) {
        this.files = files;
        this.folders = folders;
    }

    @Nullable
    public List<FileDTO> files() {
        return files;
    }

    @Nullable
    public List<FolderDTO> folders() {
        return folders;
    }
}
