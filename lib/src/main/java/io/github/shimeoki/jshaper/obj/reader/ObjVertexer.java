package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

import io.github.shimeoki.jshaper.Numberer;
import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.Tokens;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexer {

    public static ObjVertex parseVertex(final Tokens tokens) throws ShaperError {
        final int len = Objects.requireNonNull(tokens).size();
        if (len < 4 || len > 5) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid vertex format");
        }

        if (!tokens.lineTokenTypeIs(Token.Type.VERTEX)) {
            throw new ShaperError(ShaperError.Type.PARSE, "no vertex token");
        }

        final float x = Numberer.parseFloat(tokens.get(1).text());
        final float y = Numberer.parseFloat(tokens.get(2).text());
        final float z = Numberer.parseFloat(tokens.get(3).text());

        final float w;
        if (len == 5) {
            w = Numberer.parseFloat(tokens.get(4).text());
        } else {
            w = ObjVertex.DEFAULT_W;
        }

        return new ObjVertex(x, y, z, w);
    }

    public static ObjTextureVertex parseTextureVertex(final Tokens tokens) throws ShaperError {
        final int len = Objects.requireNonNull(tokens).size();
        if (len < 2 || len > 4) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid texture vertex format");
        }

        if (!tokens.lineTokenTypeIs(Token.Type.TEXTURE_VERTEX)) {
            throw new ShaperError(ShaperError.Type.PARSE, "no texture vertex token");
        }

        final float u = Numberer.parseFloat(tokens.get(1).text());

        final float v;
        if (len >= 3) {
            v = Numberer.parseFloat(tokens.get(2).text());
        } else {
            v = ObjTextureVertex.DEFAULT_V;
        }

        final float w;
        if (len == 4) {
            w = Numberer.parseFloat(tokens.get(3).text());
        } else {
            w = ObjTextureVertex.DEFAULT_W;
        }

        return new ObjTextureVertex(u, v, w);
    }

    public static ObjVertexNormal parseVertexNormal(final Tokens tokens) throws ShaperError {
        if (Objects.requireNonNull(tokens).size() != 4) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid vertex normal format");
        }

        if (!tokens.lineTokenTypeIs(Token.Type.VERTEX_NORMAL)) {
            throw new ShaperError(ShaperError.Type.PARSE, "no vertex normal token");
        }

        final float i = Numberer.parseFloat(tokens.get(1).text());
        final float j = Numberer.parseFloat(tokens.get(2).text());
        final float k = Numberer.parseFloat(tokens.get(3).text());

        return new ObjVertexNormal(i, j, k);
    }
}
