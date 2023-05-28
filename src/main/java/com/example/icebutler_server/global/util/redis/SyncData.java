package com.example.icebutler_server.global.util.redis;

import lombok.Data;

@Data
public class SyncData {
    private boolean recipeService;
    private boolean mainService;
    public boolean allTure() {
        return this.recipeService && this.mainService;
    }
    public SyncData changeTrue() {
        this.mainService = true;
        return this;
    }

    public SyncData() {
        this.recipeService = false;
        this.mainService = true;
    }
}
