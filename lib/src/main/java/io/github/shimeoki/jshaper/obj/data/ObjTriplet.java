package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjTriplet {

    private ObjVertex v;
    private ObjTextureVertex vt;
    private ObjVertexNormal vn;

    private ObjTripletFormat fmt;

    public ObjTriplet(
            final ObjVertex v,
            final ObjTextureVertex vt,
            final ObjVertexNormal vn) {

        this.v = Objects.requireNonNull(v);
        this.vt = vt;
        this.vn = vn;

        update();
    }

    public ObjVertex vertex() {
        return v;
    }

    public void setVertex(final ObjVertex v) {
        this.v = Objects.requireNonNull(v);
        update();
    }

    public ObjTextureVertex textureVertex() {
        return vt;
    }

    public void setTextureVertex(final ObjTextureVertex vt) {
        this.vt = vt;
        update();
    }

    public ObjVertexNormal vertexNormal() {
        return vn;
    }

    public void setVertexNormal(final ObjVertexNormal vn) {
        this.vn = vn;
        update();
    }

    public ObjTripletFormat format() {
        return fmt;
    }

    private void update() {
        if (vt != null && vn != null) {
            fmt = ObjTripletFormat.ALL;
        } else if (vn != null) {
            fmt = ObjTripletFormat.VERTEX_NORMAL;
        } else if (vt != null) {
            fmt = ObjTripletFormat.TEXTURE_VERTEX;
        } else {
            fmt = ObjTripletFormat.VERTEX;
        }
    }
}
