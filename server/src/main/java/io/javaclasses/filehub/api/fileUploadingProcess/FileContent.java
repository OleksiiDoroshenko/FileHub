package io.javaclasses.filehub.api.fileUploadingProcess;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Content of the {@link File}.
 */
public final class FileContent {

    private final byte[] value;

    /**
     * Returns instance of {@link FileContent}.
     *
     * @param value content value.
     */
    public FileContent(byte[] value) {
        this.value = checkNotNull(value);
    }

    public byte[] value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileContent that = (FileContent) o;
        return Arrays.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(value);
    }

    @Override
    public String toString() {
        return "FileContent{" +
                "value=" + Arrays.toString(value) +
                '}';
    }
}
