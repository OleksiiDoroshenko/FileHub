package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import com.google.common.base.Objects;
import io.javaclasses.filehub.storage.Record;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Data structure for saving file data in the {@link FileDataStorage}.
 */
public class FileDataRecord implements Record<FileSystemItemId> {

    private final byte[] data;
    private final FileSystemItemId id;

    /**
     * Returns instance of {@link FileDataRecord} with set {@link FileSystemItemId} and data.
     *
     * @param id   record identifier.
     * @param data file data.
     */
    public FileDataRecord(FileSystemItemId id, byte[] data) {

        this.id = checkNotNull(id);
        this.data = checkNotNull(data);
    }

    @Override
    public FileSystemItemId id() {
        return id;
    }

    public byte[] data() {
        return data;
    }

    @Override
    public String toString() {
        return "FileDataRecord{" +
                "data=" + Arrays.toString(data) +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDataRecord that = (FileDataRecord) o;
        return Arrays.equals(data, that.data) &&
                Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Arrays.hashCode(data), id);
    }
}
