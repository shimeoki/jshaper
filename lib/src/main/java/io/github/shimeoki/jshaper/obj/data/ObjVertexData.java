package io.github.shimeoki.jshaper.obj.data;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.ObjParameterSpaceVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexData {

    private final List<ObjVertex> vertices;
    private final List<ObjTextureVertex> textureVertices;
    private final List<ObjVertexNormal> vertexNormals;
    private final List<ObjParameterSpaceVertex> parameterSpaceVertices;

    public ObjVertexData(
            final List<ObjVertex> vertices,
            final List<ObjTextureVertex> textureVertices,
            final List<ObjVertexNormal> vertexNormals,
            final List<ObjParameterSpaceVertex> parameterSpaceVertices) {

        this.vertices = Objects.requireNonNull(vertices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.vertexNormals = Objects.requireNonNull(vertexNormals);
        this.parameterSpaceVertices = Objects.requireNonNull(parameterSpaceVertices);
    }

    public List<ObjVertex> vertices() {
        return vertices;
    }

    public List<ObjTextureVertex> textureVertices() {
        return textureVertices;
    }

    public List<ObjVertexNormal> vertexNormals() {
        return vertexNormals;
    }

    public List<ObjParameterSpaceVertex> parameterSpaceVertices() {
        return parameterSpaceVertices;
    }
}
