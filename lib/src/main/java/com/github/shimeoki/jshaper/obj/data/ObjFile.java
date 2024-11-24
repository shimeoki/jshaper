package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

public final class ObjFile {

    private final ObjVertexData vertexData;
    private final ObjElements elements;
    private final ObjGroupingData groupingData;

    public ObjFile(
            final ObjVertexData vertexData,
            final ObjElements elements,
            final ObjGroupingData groupingData) {

        Objects.requireNonNull(vertexData);
        Objects.requireNonNull(elements);
        Objects.requireNonNull(groupingData);

        this.vertexData = vertexData;
        this.elements = elements;
        this.groupingData = groupingData;
    }

    public ObjVertexData vertexData() {
        return vertexData;
    }

    public ObjElements elements() {
        return elements;
    }

    public ObjGroupingData groupingData() {
        return groupingData;
    }
}
