package io.javaclasses.filehub.api.getFolderContentView;

import com.google.common.base.Objects;
import com.google.errorprone.annotations.Immutable;
import com.google.gson.annotations.SerializedName;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileRecord;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemId;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DTO for {@link FileRecord}.
 */
@Immutable
public final class FileDTO {

    @SerializedName("name")
    private final FileSystemItemName name;

    @SerializedName("id")
    private final FileSystemItemId id;

    @SerializedName("parentId")
    private final FileSystemItemId parentId;

    @SerializedName("size")
    private final FileSize size;

    @SerializedName("mimeType")
    private final FileMimeType mimeType;

    @SerializedName("type")
    private final FileType type;

    /**
     * Returns instance of {@link FileDTO} class.
     *
     * @param name     file name.
     * @param id       file identifier.
     * @param parentId identifier of the parent of the file.
     * @param size     file size.
     * @param mimeType file mimeType.
     * @param type     file type.
     */
    public FileDTO(FileSystemItemName name, FileSystemItemId id, FileSystemItemId parentId,
                   FileSize size, FileMimeType mimeType, FileType type) {
        this.name = checkNotNull(name);
        this.id = checkNotNull(id);
        this.parentId = checkNotNull(parentId);
        this.size = checkNotNull(size);
        this.mimeType = checkNotNull(mimeType);
        this.type = checkNotNull(type);
    }

    public FileSystemItemName name() {
        return name;
    }

    public FileSystemItemId id() {
        return id;
    }

    public FileSystemItemId parentId() {
        return parentId;
    }

    public FileSize size() {
        return size;
    }

    public FileMimeType mimeType() {
        return mimeType;
    }

    public FileType type() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Objects.equal(name, fileDTO.name) &&
                Objects.equal(id, fileDTO.id) &&
                Objects.equal(parentId, fileDTO.parentId) &&
                Objects.equal(size, fileDTO.size) &&
                Objects.equal(mimeType, fileDTO.mimeType) &&
                Objects.equal(type, fileDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, parentId, size, mimeType, type);
    }
}
