package io.javaclasses.filehub.storage.userStorage;

import io.javaclasses.filehub.storage.Storage;

import java.util.*;

public class UserStorage implements Storage<UserRecord, UserId> {

    private final Map<UserId, UserRecord> storage;
    private int id;

    public UserStorage() {
        storage = Collections.synchronizedMap(new HashMap<UserId, UserRecord>());
    }

    @Override
    public UserRecord get(UserId userId) {
        return storage.get(userId);
    }

    @Override
    public UserRecord remove(UserId userId) {
        return storage.remove(userId);
    }

    @Override
    public List<UserRecord> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public UserId add(UserRecord userRecord) {
        UserId id = generateId();
        storage.put(id, userRecord);
        return id;
    }

    private UserId generateId() {
        id++;
        return new UserId(id + "");
    }
}
