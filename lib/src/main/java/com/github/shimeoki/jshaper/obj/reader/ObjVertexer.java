package com.github.shimeoki.jshaper.obj.reader;

import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexer {

    public static ObjVertex parseVertex(final List<ObjParsedString> strings) throws ObjReaderException {
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

        return new ObjVertex(x, y, z, w);
    }

    public static ObjTextureVertex parseTextureVertex(final List<ObjParsedString> strings) throws ObjReaderException {
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

        return new ObjTextureVertex(u, v, w);
    }

    public static ObjVertexNormal parseVertexNormal(final List<ObjParsedString> strings) throws ObjReaderException {
        if (Objects.requireNonNull(strings).size() != 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex normal format");
        }

        if (!check(strings, ObjToken.VERTEX_NORMAL)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex normal token");
        }

        final float i = ObjNumberer.parseFloat(strings.get(1).value());
        final float j = ObjNumberer.parseFloat(strings.get(2).value());
        final float k = ObjNumberer.parseFloat(strings.get(3).value());

        return new ObjVertexNormal(i, j, k);
    }

    private static boolean check(final List<ObjParsedString> strings, final ObjToken token) {
        return strings.getFirst().token().is(token);
    }
}
