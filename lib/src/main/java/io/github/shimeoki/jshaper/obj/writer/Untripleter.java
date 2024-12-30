package io.github.shimeoki.jshaper.obj.writer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.Triplet;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

public final class Untripleter {

    private final StringBuilder builder = new StringBuilder();

    private final Map<Vertex, Integer> vertices = new HashMap<>();
    private int vertexIndex = 1;

    private final Map<TextureVertex, Integer> textureVertices = new HashMap<>();
    private int textureVertexIndex = 1;

    private final Map<VertexNormal, Integer> vertexNormals = new HashMap<>();
    private int vertexNormalIndex = 1;

    public Untripleter() {
    }

    public void addVertex(final Vertex v) {
        vertices.put(Objects.requireNonNull(v), vertexIndex++);
    }

    public void addTextureVertex(final TextureVertex vt) {
        textureVertices.put(Objects.requireNonNull(vt), textureVertexIndex++);
    }

    public void addVertexNormal(final VertexNormal vn) {
        vertexNormals.put(Objects.requireNonNull(vn), vertexNormalIndex++);
    }

    public String parse(final Triplet t) {
        builder.setLength(0);

        switch (Objects.requireNonNull(t).format()) {
            case VERTEX -> parseVertex(t);
            case TEXTURE_VERTEX -> parseTextureVertex(t);
            case VERTEX_NORMAL -> parseVertexNormal(t);
            case ALL -> parseAll(t);
        }

        return builder.toString();
    }

    private void parseVertex(final Triplet t) {
        builder.append(vertices.get(t.vertex()));
    }

    private void parseTextureVertex(final Triplet t) {
        builder.append(vertices.get(t.vertex()));
        builder.append('/');
        builder.append(textureVertices.get(t.textureVertex()));
    }

    private void parseVertexNormal(final Triplet t) {
        builder.append(vertices.get(t.vertex()));
        builder.append('/');
        builder.append('/');
        builder.append(vertexNormals.get(t.vertexNormal()));
    }

    private void parseAll(final Triplet t) {
        builder.append(vertices.get(t.vertex()));
        builder.append('/');
        builder.append(textureVertices.get(t.textureVertex()));
        builder.append('/');
        builder.append(vertexNormals.get(t.vertexNormal()));
    }
}
