package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

// TODO: check name validity
public final class ObjGroup {

    private final String name;

    public ObjGroup(final String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    public String name() {
        return name;
    }
}
