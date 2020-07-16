package io.javaclasses.filehub.api.fileUploadingProcess;

import com.google.common.base.Objects;
import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object that contains file information.
 *
 * <p>Used for saving information about file that was passed in the client request.</p>
 */
public final class File {

    private final FileSystemItemName name;

    private final FileMimeType mimeType;

    private final FileSize size;

    private final FileContent data;

    /**
     * Creates instance of {@link File} with set parameters.
     *
     * @param content  representation of the file in the byte array form.
     * @param name     file name.
     * @param mimeType file mimeType.
     * @param size     file size in bytes.
     */
    public File(FileContent content, FileSystemItemName name, FileMimeType mimeType, FileSize size) {

        this.data = checkNotNull(content);
        this.name = checkNotNull(name);
        this.mimeType = checkNotNull(mimeType);
        this.size = checkNotNull(size);
    }

    public FileContent data() {
        return data;
    }

    public FileSystemItemName name() {
        return name;
    }

    public FileMimeType mimeType() {
        return mimeType;
    }

    public FileSize size() {
        return size;
    }

    @Override
    public String toString() {
        return "File{" +
                "data=" + data.toString() +
                ", name=" + name.value() +
                ", mimeType=" + mimeType.value() +
                ", size=" + size.value() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equal(name, file.name) &&
                Objects.equal(mimeType, file.mimeType) &&
                Objects.equal(size, file.size) &&
                Objects.equal(data, file.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, mimeType, size, data);
    }
}
