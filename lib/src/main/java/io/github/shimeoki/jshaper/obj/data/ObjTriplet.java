package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.TextureVertex;
import io.github.shimeoki.jshaper.obj.geom.Vertex;
import io.github.shimeoki.jshaper.obj.geom.VertexNormal;

public final class ObjTriplet {

    public enum Format {
        VERTEX,
        TEXTURE_VERTEX,
        VERTEX_NORMAL,
        ALL
    }

    private Vertex v;
    private TextureVertex vt;
    private VertexNormal vn;

    private Format format;

    public ObjTriplet(
            final Vertex v,
            final TextureVertex vt,
            final VertexNormal vn) {

        this.v = Objects.requireNonNull(v);
        this.vt = vt;
        this.vn = vn;

        update();
    }

    public Vertex vertex() {
        return v;
    }

    public void setVertex(final Vertex v) {
        this.v = Objects.requireNonNull(v);
        update();
    }

    public TextureVertex textureVertex() {
        return vt;
    }

    public void setTextureVertex(final TextureVertex vt) {
        this.vt = vt;
        update();
    }

    public VertexNormal vertexNormal() {
        return vn;
    }

    public void setVertexNormal(final VertexNormal vn) {
        this.vn = vn;
        update();
    }

    public Format format() {
        return format;
    }

    private void update() {
        if (vt != null && vn != null) {
            format = Format.ALL;
        } else if (vn != null) {
            format = Format.VERTEX_NORMAL;
        } else if (vt != null) {
            format = Format.TEXTURE_VERTEX;
        } else {
            format = Format.VERTEX;
        }
    }
}
