package io.javaclasses.filehub.storage.fileSystemItemsStorage;

import io.javaclasses.filehub.storage.Record;

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
}
