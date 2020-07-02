package io.javaclasses.filehub.storage.tokenStorage;

import io.javaclasses.filehub.storage.RecordId;

import static com.google.common.base.Preconditions.checkNotNull;

public class TokenId implements RecordId {

    private final String value;

    public TokenId(String value) {
        this.value = checkNotNull(value);
    }


    @Override
    public String value() {
        return value;
    }
}
