package io.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.Face;

public final class Elements {

    // not all elements from the specification
    // are present
    private final List<Face> faces;

    public Elements(final List<Face> faces) {
        this.faces = Objects.requireNonNull(faces);
    }

    public List<Face> faces() {
        return faces;
    }
}
