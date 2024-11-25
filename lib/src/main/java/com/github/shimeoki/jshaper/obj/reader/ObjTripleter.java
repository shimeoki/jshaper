package com.github.shimeoki.jshaper.obj.reader;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.data.ObjTriplet;
import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjTripleter {

    private final List<ObjVertex> vertices;
    private final List<ObjTextureVertex> textureVertices;
    private final List<ObjVertexNormal> vertexNormals;

    private final StringBuilder builder = new StringBuilder();
    private final int[] indices = new int[3];

    public ObjTripleter(
            final List<ObjVertex> vertices,
            final List<ObjTextureVertex> textureVertices,
            final List<ObjVertexNormal> vertexNormals) {

        Objects.requireNonNull(vertices);
        Objects.requireNonNull(textureVertices);
        Objects.requireNonNull(vertexNormals);

        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.vertexNormals = vertexNormals;
    }

    private void parseIndices(final String triplet) throws ObjReaderException {
        final int len = triplet.length();
        builder.setLength(0);

        Arrays.fill(indices, 0);
        int index = -1;

        char c;
        for (int i = 0; i < len; i++) {
            c = triplet.charAt(i);

            if (c == ' ') {
                throw new ObjReaderException(ObjReaderExceptionType.PARSE, "found a space in a triplet");
            }

            if (c != '/') {
                builder.append(c);
                continue;
            }

            index++;
            if (index > 2) {
                throw new ObjReaderException(ObjReaderExceptionType.PARSE,
                        "found more than three indices in a triplet");
            }

            if (builder.isEmpty()) {
                continue;
            }

            indices[index] = ObjNumberer.parseInt(builder.toString());
            builder.setLength(0);
        }
    }

    private ObjVertex parseVertex() throws ObjReaderException {
        int i = indices[0];
        if (i == 0) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex index in the triplet");
        }

        final int len = vertices.size();

        if (i < 0) {
            i += len;
        } else {
            i--;
        }

        if (i < 0 || i >= len) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex index");
        }

        return vertices.get(i);
    }

    private ObjTextureVertex parseTextureVertex() throws ObjReaderException {
        int i = indices[1];
        if (i == 0) {
            return null;
        }

        final int len = textureVertices.size();

        if (i < 0) {
            i += len;
        } else {
            i--;
        }

        if (i < 0 || i >= len) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid texture vertex index");
        }

        return textureVertices.get(i);
    }

    private ObjVertexNormal parseVertexNormal() throws ObjReaderException {
        int i = indices[2];
        if (i == 0) {
            return null;
        }

        final int len = textureVertices.size();

        if (i < 0) {
            i += len;
        } else {
            i--;
        }

        if (i < 0 || i >= len) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex normal index");
        }

        return vertexNormals.get(i);
    }

    public ObjTriplet parse(final String triplet) throws ObjReaderException {
        parseIndices(triplet);

        final ObjVertex v = parseVertex();
        final ObjTextureVertex vt = parseTextureVertex();
        final ObjVertexNormal vn = parseVertexNormal();

        return new ObjTriplet(v, vt, vn);
    }
}
