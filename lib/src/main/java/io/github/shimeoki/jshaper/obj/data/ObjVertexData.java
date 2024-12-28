package io.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.ObjParameterSpaceVertex;
import io.github.shimeoki.jshaper.obj.geom.TextureVertex;
import io.github.shimeoki.jshaper.obj.geom.Vertex;
import io.github.shimeoki.jshaper.obj.geom.VertexNormal;

public final class ObjVertexData {

    private final List<Vertex> vertices;
    private final List<TextureVertex> textureVertices;
    private final List<VertexNormal> vertexNormals;
    private final List<ObjParameterSpaceVertex> parameterSpaceVertices;

    public ObjVertexData(
            final List<Vertex> vertices,
            final List<TextureVertex> textureVertices,
            final List<VertexNormal> vertexNormals,
            final List<ObjParameterSpaceVertex> parameterSpaceVertices) {

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

    public List<ObjParameterSpaceVertex> parameterSpaceVertices() {
        return parameterSpaceVertices;
    }
}
