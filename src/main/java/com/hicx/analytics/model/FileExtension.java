package com.hicx.analytics.model;

public enum FileExtension {

    TXT,
    UNKNOWN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
