package io.github.shimeoki.jshaper.obj.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.obj.data.ObjTriplet;
import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjTripleter {

    private final List<ObjVertex> vertices;
    private final List<ObjTextureVertex> textureVertices;
    private final List<ObjVertexNormal> vertexNormals;

    private final StringBuilder builder = new StringBuilder();

    private final int[] indices = new int[3];
    private int index;

    public ObjTripleter(
            final List<ObjVertex> vertices,
            final List<ObjTextureVertex> textureVertices,
            final List<ObjVertexNormal> vertexNormals) {

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
                throw new ShaperError(ObjReaderExceptionType.PARSE, "found a space in a triplet");
            }

            if (c != '/') {
                builder.append(c);
                continue;
            }

            index++;
            if (index > 2) {
                throw new ShaperError(ObjReaderExceptionType.PARSE,
                        "found more than three indices in a triplet");
            }

            if (builder.isEmpty()) {
                continue;
            }

            indices[index] = ObjNumberer.parseInt(builder.toString());
            builder.setLength(0);
        }
    }

    private ObjVertex parseVertex() throws ShaperError {
        parseIndex(indices[0], vertices.size());

        if (index < 0) {
            throw new ShaperError(ObjReaderExceptionType.PARSE, "no vertex in a triplet");
        } else {
            return vertices.get(index);
        }
    }

    private ObjTextureVertex parseTextureVertex() throws ShaperError {
        parseIndex(indices[1], textureVertices.size());

        if (index < 0) {
            return null;
        } else {
            return textureVertices.get(index);
        }
    }

    private ObjVertexNormal parseVertexNormal() throws ShaperError {
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
            throw new ShaperError(ObjReaderExceptionType.PARSE, "invalid vertex index");
        }
    }

    public ObjTriplet parse(final String triplet) throws ShaperError {
        parseIndices(Objects.requireNonNull(triplet));

        final ObjVertex v = parseVertex();
        final ObjTextureVertex vt = parseTextureVertex();
        final ObjVertexNormal vn = parseVertexNormal();

        return new ObjTriplet(v, vt, vn);
    }
}
