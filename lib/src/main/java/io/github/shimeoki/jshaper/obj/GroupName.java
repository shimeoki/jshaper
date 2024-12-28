package io.github.shimeoki.jshaper.obj;

import java.util.Objects;

public final class GroupName {

    // TODO: check name validity
    private String name;

    public GroupName(final String name) {
        setName(name);
    }

    public String name() {
        return name;
    }

    public void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }
}
