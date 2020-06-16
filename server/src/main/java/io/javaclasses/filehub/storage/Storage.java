package io.javaclasses.filehub.storage;

import java.util.List;

public interface Storage<Record, RecordId> {
    Record get(RecordId id);

    Record remove(RecordId id);

    List<Record> getAll();

    RecordId add(Record record);
}
