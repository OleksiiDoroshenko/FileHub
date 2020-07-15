package io.javaclasses.filehub.api.fileUploadingProcess;

import io.javaclasses.filehub.api.getFolderContentView.FileMimeType;
import io.javaclasses.filehub.api.getFolderContentView.FileSize;
import io.javaclasses.filehub.storage.fileSystemItemsStorage.FileSystemItemName;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Value object that contains file information.
 *
 * <p>Used for saving information about file that was passed in the client request.</p>
 */
public class File {

    private final byte[] data;

    private final FileSystemItemName name;

    private final FileMimeType mimeType;

    private final FileSize size;

    /**
     * Returns instance of {@link File} with set parameters.
     *
     * @param data     representation of the file in the byte array form.
     * @param name     file name.
     * @param mimeType file mimeType.
     * @param size     file size in bytes.
     */
    public File(byte[] data, FileSystemItemName name, FileMimeType mimeType, FileSize size) {

        this.data = checkNotNull(data);
        this.name = checkNotNull(name);
        this.mimeType = checkNotNull(mimeType);
        this.size = checkNotNull(size);
    }

    public byte[] data() {
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
}
