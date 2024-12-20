package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

// TODO: check name validity
public final class ObjGroupName {

    private String name;

    public ObjGroupName(final String name) {
        setName(name);
    }

    public String name() {
        return name;
    }

    public void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }
}
