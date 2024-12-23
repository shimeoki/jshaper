package io.github.shimeoki.jshaper.obj.writer;

import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenizer;

public final class ObjUnvertexer {

    private final StringBuilder builder = new StringBuilder();

    public String parseVertex(final ObjVertex v) {
        builder.setLength(0);

        builder.append(ObjTokenizer.VERTEX);

        builder.append(' ');
        builder.append(v.x());

        builder.append(' ');
        builder.append(v.y());

        builder.append(' ');
        builder.append(v.z());

        builder.append(' ');
        builder.append(v.w());

        return builder.toString();
    }

    public String parseTextureVertex(final ObjTextureVertex vt) {
        // TODO
        return null;
    }

    public String parseVertexNormal(final ObjVertexNormal vn) {
        // TODO
        return null;
    }
}
