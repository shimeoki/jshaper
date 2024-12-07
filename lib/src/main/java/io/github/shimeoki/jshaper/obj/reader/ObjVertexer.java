package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexer {

    public static ObjVertex parseVertex(final ObjTokens tokens) throws ObjReaderException {
        final int len = Objects.requireNonNull(tokens).size();
        if (len < 4 || len > 5) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex format");
        }

        if (!tokens.lineTokenIs(ObjToken.VERTEX)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex token");
        }

        final float x = ObjNumberer.parseFloat(tokens.value(1));
        final float y = ObjNumberer.parseFloat(tokens.value(2));
        final float z = ObjNumberer.parseFloat(tokens.value(3));

        final Float w;
        if (len == 5) {
            w = ObjNumberer.parseFloat(tokens.value(4));
        } else {
            w = null;
        }

        return new ObjVertex(x, y, z, w);
    }

    public static ObjTextureVertex parseTextureVertex(final ObjTokens tokens) throws ObjReaderException {
        final int len = Objects.requireNonNull(tokens).size();
        if (len < 2 || len > 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid texture vertex format");
        }

        if (!tokens.lineTokenIs(ObjToken.TEXTURE_VERTEX)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no texture vertex token");
        }

        final float u = ObjNumberer.parseFloat(tokens.value(1));

        final Float v;
        if (len >= 3) {
            v = ObjNumberer.parseFloat(tokens.value(2));
        } else {
            v = null;
        }

        final Float w;
        if (len == 4) {
            w = ObjNumberer.parseFloat(tokens.value(3));
        } else {
            w = null;
        }

        return new ObjTextureVertex(u, v, w);
    }

    public static ObjVertexNormal parseVertexNormal(final ObjTokens tokens) throws ObjReaderException {
        if (Objects.requireNonNull(tokens).size() != 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex normal format");
        }

        if (!tokens.lineTokenIs(ObjToken.VERTEX_NORMAL)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "no vertex normal token");
        }

        final float i = ObjNumberer.parseFloat(tokens.value(1));
        final float j = ObjNumberer.parseFloat(tokens.value(2));
        final float k = ObjNumberer.parseFloat(tokens.value(3));

        return new ObjVertexNormal(i, j, k);
    }
}
