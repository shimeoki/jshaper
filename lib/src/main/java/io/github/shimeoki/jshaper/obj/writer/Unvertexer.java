package io.github.shimeoki.jshaper.obj.writer;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.TextureVertex;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.Vertex;
import io.github.shimeoki.jshaper.obj.VertexNormal;

public final class Unvertexer {

    private final StringBuilder builder = new StringBuilder();

    public Unvertexer() {
    }

    public String parseVertex(final Vertex v) {
        Objects.requireNonNull(v);
        builder.setLength(0);

        builder.append(Token.VERTEX);

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

    public String parseTextureVertex(final TextureVertex vt) {
        Objects.requireNonNull(vt);
        builder.setLength(0);

        builder.append(Token.TEXTURE_VERTEX);

        builder.append(' ');
        builder.append(vt.u());

        builder.append(' ');
        builder.append(vt.v());

        builder.append(' ');
        builder.append(vt.w());

        return builder.toString();
    }

    public String parseVertexNormal(final VertexNormal vn) {
        Objects.requireNonNull(vn);
        builder.setLength(0);

        builder.append(Token.VERTEX_NORMAL);

        builder.append(' ');
        builder.append(vn.i());

        builder.append(' ');
        builder.append(vn.j());

        builder.append(' ');
        builder.append(vn.j());

        return builder.toString();
    }
}
