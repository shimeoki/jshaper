package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

public final class ObjFile {

    private final ObjVertexData vertexData;
    private final ObjElements elements;

    public ObjFile(final ObjVertexData vertexData, final ObjElements elements) {
        Objects.requireNonNull(vertexData);
        Objects.requireNonNull(elements);

        this.vertexData = vertexData;
        this.elements = elements;
    }

    public ObjVertexData vertexData() {
        return vertexData;
    }

    public ObjElements elements() {
        return elements;
    }

    public ObjGroupingData groupingData() {
        // TODO
        return null;
    }
}
