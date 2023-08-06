package com.cryxiaoyu.chinesename.event;

import java.util.UUID;

public class ChineseNameChangeEvent{
    private final String oldName;
    private final String newName;
    private final UUID uuid;

    public ChineseNameChangeEvent(String oldName, String newName, UUID uuid) {
        this.oldName = oldName;
        this.newName = newName;
        this.uuid = uuid;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }

    public UUID getUUID() {
        return uuid;
    }
}