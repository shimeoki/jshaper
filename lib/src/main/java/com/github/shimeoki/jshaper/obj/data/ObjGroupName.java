package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

// TODO: check name validity
public final class ObjGroupName {

    private final String name;

    public ObjGroupName(final String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    public String name() {
        return name;
    }
}
