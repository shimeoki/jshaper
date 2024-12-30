package io.github.shimeoki.jshaper.obj;

import java.util.List;
import java.util.Objects;

public final class VertexData {

    private final List<Vertex> vertices;
    private final List<TextureVertex> textureVertices;
    private final List<VertexNormal> vertexNormals;
    private final List<ParameterVertex> parameterVertices;

    public VertexData(
            final List<Vertex> vertices,
            final List<TextureVertex> textureVertices,
            final List<VertexNormal> vertexNormals,
            final List<ParameterVertex> parameterVertices) {

        this.vertices = Objects.requireNonNull(vertices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.vertexNormals = Objects.requireNonNull(vertexNormals);
        this.parameterVertices = Objects.requireNonNull(parameterVertices);
    }

    public List<Vertex> vertices() {
        return vertices;
    }

    public List<TextureVertex> textureVertices() {
        return textureVertices;
    }

    public List<VertexNormal> vertexNormals() {
        return vertexNormals;
    }

    public List<ParameterVertex> parameterVertices() {
        return parameterVertices;
    }
}
