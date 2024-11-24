package com.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.geom.ObjFace;

public final class ObjElements {

    // not all elements from the specification
    // are present
    private final List<ObjFace> faces;

    public ObjElements(final List<ObjFace> faces) {
        Objects.requireNonNull(faces);

        this.faces = faces;
    }

    public List<ObjFace> faces() {
        return faces;
    }
}
