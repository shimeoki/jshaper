package io.github.shimeoki.jshaper.obj;

import java.util.Objects;

public final class Group {

    private String name; // TODO check validity

    public Group(final String name) {
        setName(name);
    }

    public String name() {
        return name;
    }

    public void setName(final String name) {
        this.name = Objects.requireNonNull(name);
    }
}
