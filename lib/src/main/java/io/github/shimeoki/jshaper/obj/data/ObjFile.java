package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

public final class ObjFile {

    private final ObjVertexData vertexData;
    private final Elements elements;
    private final ObjGroupingData groupingData;

    public ObjFile(
            final ObjVertexData vertexData,
            final Elements elements,
            final ObjGroupingData groupingData) {

        this.vertexData = Objects.requireNonNull(vertexData);
        this.elements = Objects.requireNonNull(elements);
        this.groupingData = Objects.requireNonNull(groupingData);
    }

    public ObjVertexData vertexData() {
        return vertexData;
    }

    public Elements elements() {
        return elements;
    }

    public ObjGroupingData groupingData() {
        return groupingData;
    }
}
