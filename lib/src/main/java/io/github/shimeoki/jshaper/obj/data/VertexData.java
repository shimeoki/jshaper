package io.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.ParameterSpaceVertex;
import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

public final class VertexData {

    private final List<Vertex> vertices;
    private final List<TextureVertex> textureVertices;
    private final List<VertexNormal> vertexNormals;
    private final List<ParameterSpaceVertex> parameterSpaceVertices;

    public VertexData(
            final List<Vertex> vertices,
            final List<TextureVertex> textureVertices,
            final List<VertexNormal> vertexNormals,
            final List<ParameterSpaceVertex> parameterSpaceVertices) {

        this.vertices = Objects.requireNonNull(vertices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.vertexNormals = Objects.requireNonNull(vertexNormals);
        this.parameterSpaceVertices = Objects.requireNonNull(parameterSpaceVertices);
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

    public List<ParameterSpaceVertex> parameterSpaceVertices() {
        return parameterSpaceVertices;
    }
}
