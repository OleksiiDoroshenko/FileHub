package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.common.base.Objects;
import io.javaclasses.filehub.api.fileUploadingProcess.FileContent;
import io.javaclasses.filehub.storage.Record;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving file data in the {@link FileContentStorage}.
 */
public class FileContentRecord implements Record<FileId> {

    private final FileContent data;
    private final FileId id;

    /**
     * Creates instance of {@link FileContentRecord} with set {@link FileId} and data.
     *
     * @param id   record identifier.
     * @param data file data.
     */
    public FileContentRecord(FileId id, FileContent data) {

        this.id = checkNotNull(id);
        this.data = checkNotNull(data);
    }

    @Override
    public FileId id() {
        return id;
    }

    public FileContent data() {
        return data;
    }

    @Override
    public String toString() {
        return "FileDataRecord{" +
                "data=" + data +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileContentRecord that = (FileContentRecord) o;
        return Objects.equal(data, that.data) &&
                Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data, id);
    }
}
