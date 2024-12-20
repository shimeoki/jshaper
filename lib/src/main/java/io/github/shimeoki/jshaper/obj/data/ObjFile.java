package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

public final class ObjFile {

    private final ObjVertexData vertexData;
    private final ObjElements elements;
    private final ObjGroupingData groupingData;

    public ObjFile(
            final ObjVertexData vertexData,
            final ObjElements elements,
            final ObjGroupingData groupingData) {

        this.vertexData = Objects.requireNonNull(vertexData);
        this.elements = Objects.requireNonNull(elements);
        this.groupingData = Objects.requireNonNull(groupingData);
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
