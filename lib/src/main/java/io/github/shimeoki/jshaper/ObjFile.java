package io.github.shimeoki.jshaper;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.Elements;
import io.github.shimeoki.jshaper.obj.GroupingData;
import io.github.shimeoki.jshaper.obj.VertexData;

public final class ObjFile {

    private final VertexData vertexData;
    private final Elements elements;
    private final GroupingData groupingData;

    public ObjFile(
            final VertexData vertexData,
            final Elements elements,
            final GroupingData groupingData) {

        this.vertexData = Objects.requireNonNull(vertexData);
        this.elements = Objects.requireNonNull(elements);
        this.groupingData = Objects.requireNonNull(groupingData);
    }

    public VertexData vertexData() {
        return vertexData;
    }

    public Elements elements() {
        return elements;
    }

    public GroupingData groupingData() {
        return groupingData;
    }
}
