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
public final class FileDto {

    @SerializedName("name")
    private final String name;

    @SerializedName("id")
    private final String id;

    @SerializedName("size")
    private final String size;

    @SerializedName("mimeType")
    private final String mimeType;

    /**
     * Returns instance of {@link FileDto} class.
     *
     * @param name     file name.
     * @param id       file identifier.
     * @param size     file size.
     * @param mimeType file mimeType.
     */
    public FileDto(FileSystemItemName name, FileSystemItemId id,
                   FileSize size, FileMimeType mimeType) {

        this.name = checkNotNull(name.value());
        this.id = checkNotNull(id.value());
        this.size = String.valueOf(size.value());
        this.mimeType = checkNotNull(mimeType.value());
    }


    public String name() {
        return name;
    }

    public String id() {
        return id;
    }

    public String size() {
        return size;
    }

    public String mimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto fileDto = (FileDto) o;
        return Objects.equal(name, fileDto.name) &&
                Objects.equal(id, fileDto.id) &&
                Objects.equal(size, fileDto.size) &&
                Objects.equal(mimeType, fileDto.mimeType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, size, mimeType);
    }
}
