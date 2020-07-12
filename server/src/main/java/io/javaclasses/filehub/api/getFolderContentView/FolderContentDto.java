package io.javaclasses.filehub.api.getFolderContentView;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import java.util.List;

/**
 * DTO for file system folder content.
 *
 * <p>Include files and folders.</p>
 */
public final class FolderContentDto {

    @SerializedName("files")
    private final List<FileDto> files;

    @SerializedName("folders")
    private final List<FolderDto> folders;

    /**
     * Returns instance of {@link FolderContentDto} class.
     *
     * @param files   list of files.
     * @param folders list of folders.
     */
    FolderContentDto(List<FileDto> files, List<FolderDto> folders) {
        this.files = files;
        this.folders = folders;
    }

    @Nullable
    public List<FileDto> files() {
        return files;
    }

    @Nullable
    public List<FolderDto> folders() {
        return folders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderContentDto that = (FolderContentDto) o;
        return Objects.equal(files, that.files) &&
                Objects.equal(folders, that.folders);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(files, folders);
    }
}
