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
        builder.setLength(0);

        builder.append(ObjTokenizer.TEXTURE_VERTEX);

        builder.append(' ');
        builder.append(vt.u());

        builder.append(' ');
        builder.append(vt.v());

        builder.append(' ');
        builder.append(vt.w());

        return builder.toString();
    }

    public String parseVertexNormal(final ObjVertexNormal vn) {
        builder.setLength(0);

        builder.append(ObjTokenizer.VERTEX_NORMAL);

        builder.append(' ');
        builder.append(vn.i());

        builder.append(' ');
        builder.append(vn.j());

        builder.append(' ');
        builder.append(vn.j());

        return builder.toString();
    }
}
