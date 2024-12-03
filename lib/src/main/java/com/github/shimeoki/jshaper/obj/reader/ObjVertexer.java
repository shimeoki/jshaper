package com.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexer {

    private List<ObjVertex> vertices = new ArrayList<>();
    private List<ObjTextureVertex> textureVertices = new ArrayList<>();
    private List<ObjVertexNormal> vertexNormals = new ArrayList<>();

    public ObjVertexer() {
    }

    public void parseVertex(final List<ObjParsedString> strings) throws ObjReaderException {
        final int len = Objects.requireNonNull(strings).size();
        if (len < 4 || len > 5) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex format");
        }

        if (!check(strings, ObjToken.VERTEX)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex token");
        }

        final float x = ObjNumberer.parseFloat(strings.get(1).value());
        final float y = ObjNumberer.parseFloat(strings.get(2).value());
        final float z = ObjNumberer.parseFloat(strings.get(3).value());

        final Float w;
        if (len == 5) {
            w = ObjNumberer.parseFloat(strings.get(4).value());
        } else {
            w = null;
        }

        vertices.add(new ObjVertex(x, y, z, w));
    }

    public void parseTextureVertex(final List<ObjParsedString> strings) throws ObjReaderException {
        final int len = Objects.requireNonNull(strings).size();
        if (len < 2 || len > 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid texture vertex format");
        }

        if (!check(strings, ObjToken.TEXTURE_VERTEX)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no texture vertex token");
        }

        final float u = ObjNumberer.parseFloat(strings.get(1).value());

        final Float v;
        if (len >= 3) {
            v = ObjNumberer.parseFloat(strings.get(2).value());
        } else {
            v = null;
        }

        final Float w;
        if (len == 4) {
            w = ObjNumberer.parseFloat(strings.get(3).value());
        } else {
            w = null;
        }

        textureVertices.add(new ObjTextureVertex(u, v, w));
    }

    public void parseVertexNormal(final List<ObjParsedString> strings) throws ObjReaderException {
        if (Objects.requireNonNull(strings).size() != 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex normal format");
        }

        if (!check(strings, ObjToken.VERTEX_NORMAL)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex normal token");
        }

        final float i = ObjNumberer.parseFloat(strings.get(1).value());
        final float j = ObjNumberer.parseFloat(strings.get(2).value());
        final float k = ObjNumberer.parseFloat(strings.get(3).value());

        vertexNormals.add(new ObjVertexNormal(i, j, k));
    }

    private static boolean check(final List<ObjParsedString> strings, final ObjToken token) {
        return strings.getFirst().token().is(token);
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
}
