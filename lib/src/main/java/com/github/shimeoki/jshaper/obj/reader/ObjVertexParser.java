package com.github.shimeoki.jshaper.obj.reader;

import java.util.List;

import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjVertexParser {

    public static ObjVertex parseVertex(final List<String> strings) throws ObjReaderException {
        final int len = strings.size();

        if (len < 3 || len > 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex format");
        }

        final float x = ObjNumberParser.parseFloat(strings.get(0));
        final float y = ObjNumberParser.parseFloat(strings.get(1));
        final float z = ObjNumberParser.parseFloat(strings.get(2));

        final Float w;
        if (len == 4) {
            w = ObjNumberParser.parseFloat(strings.get(3));
        } else {
            w = null;
        }

        return new ObjVertex(x, y, z, w);
    }

    public static ObjTextureVertex parseTextureVertex(final List<String> strings) throws ObjReaderException {
        final int len = strings.size();

        if (len < 1 || len > 3) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid texture vertex format");
        }

        final float u = ObjNumberParser.parseFloat(strings.get(0));

        final Float v;
        if (len >= 2) {
            v = ObjNumberParser.parseFloat(strings.get(1));
        } else {
            v = null;
        }

        final Float w;
        if (len == 3) {
            w = ObjNumberParser.parseFloat(strings.get(2));
        } else {
            w = null;
        }

        return new ObjTextureVertex(u, v, w);
    }

    public static ObjVertexNormal parseVertexNormal(final List<String> strings) throws ObjReaderException {
        if (strings.size() != 3) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid vertex normal format");
        }

        final float i = ObjNumberParser.parseFloat(strings.get(0));
        final float j = ObjNumberParser.parseFloat(strings.get(1));
        final float k = ObjNumberParser.parseFloat(strings.get(2));

        return new ObjVertexNormal(i, j, k);
    }
}
