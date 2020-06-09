package io.javaclasses.filehub.web.fileService;

import io.javaclasses.filehub.web.Id;

public abstract class Item {

    private String name;
    private String type;
    private Id id;
    private Id parentId;
    private String mimeType;
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Id getId() {
        return id;
    }

    public Id getParentId() {
        return parentId;
    }

    public void setParentId(Id parentId) {
        this.parentId = parentId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

}
