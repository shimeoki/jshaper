package io.github.shimeoki.jshaper.obj.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.Numberer;
import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.data.Triplet;
import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

public final class Tripleter {

    private final List<Vertex> vertices;
    private final List<TextureVertex> textureVertices;
    private final List<VertexNormal> vertexNormals;

    private final StringBuilder builder = new StringBuilder();

    private final int[] indices = new int[3];
    private int index;

    public Tripleter(
            final List<Vertex> vertices,
            final List<TextureVertex> textureVertices,
            final List<VertexNormal> vertexNormals) {

        this.vertices = Objects.requireNonNull(vertices);
        this.textureVertices = Objects.requireNonNull(textureVertices);
        this.vertexNormals = Objects.requireNonNull(vertexNormals);
    }

    private void parseIndices(final String triplet) throws ShaperError {
        final int len = triplet.length();
        builder.setLength(0);

        Arrays.fill(indices, 0);
        int index = -1;

        char c;
        for (int i = 0; i < len; i++) {
            c = triplet.charAt(i);

            if (c == ' ') {
                throw new ShaperError(ShaperError.Type.PARSE,
                        "found a space in a triplet");
            }

            if (c != '/') {
                builder.append(c);
                continue;
            }

            index++;
            if (index > 2) {
                throw new ShaperError(ShaperError.Type.PARSE,
                        "found more than three indices in a triplet");
            }

            if (builder.isEmpty()) {
                continue;
            }

            indices[index] = Numberer.parseInt(builder.toString());
            builder.setLength(0);
        }
    }

    private Vertex parseVertex() throws ShaperError {
        parseIndex(indices[0], vertices.size());

        if (index < 0) {
            throw new ShaperError(ShaperError.Type.PARSE, "no vertex in a triplet");
        } else {
            return vertices.get(index);
        }
    }

    private TextureVertex parseTextureVertex() throws ShaperError {
        parseIndex(indices[1], textureVertices.size());

        if (index < 0) {
            return null;
        } else {
            return textureVertices.get(index);
        }
    }

    private VertexNormal parseVertexNormal() throws ShaperError {
        parseIndex(indices[2], vertexNormals.size());

        if (index < 0) {
            return null;
        } else {
            return vertexNormals.get(index);
        }
    }

    private void parseIndex(final int i, final int len) throws ShaperError {
        if (i < 0) {
            index = i + len;
        } else if (i > 0) {
            index = i - 1;
        } else {
            index = -1;
            return;
        }

        if (index < 0 || index >= len) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid vertex index");
        }
    }

    public Triplet parse(final String triplet) throws ShaperError {
        parseIndices(Objects.requireNonNull(triplet));

        final Vertex v = parseVertex();
        final TextureVertex vt = parseTextureVertex();
        final VertexNormal vn = parseVertexNormal();

        return new Triplet(v, vt, vn);
    }
}
