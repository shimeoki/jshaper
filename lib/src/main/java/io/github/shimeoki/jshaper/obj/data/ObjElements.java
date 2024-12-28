package io.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.Face;

public final class ObjElements {

    // not all elements from the specification
    // are present
    private final List<Face> faces;

    public ObjElements(final List<Face> faces) {
        this.faces = Objects.requireNonNull(faces);
    }

    public List<Face> faces() {
        return faces;
    }
}
