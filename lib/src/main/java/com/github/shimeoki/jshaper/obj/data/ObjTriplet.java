package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjTriplet {

    private final ObjVertex v;
    private final ObjTextureVertex vt;
    private final ObjVertexNormal vn;

    private final ObjTripletFormat fmt;

    public ObjTriplet(
            final ObjVertex v,
            final ObjTextureVertex vt,
            final ObjVertexNormal vn) {

        Objects.requireNonNull(v);

        this.v = v;
        this.vt = vt;
        this.vn = vn;

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

    public ObjVertex vertex() {
        return v;
    }

    public ObjTextureVertex textureVertex() {
        return vt;
    }

    public ObjVertexNormal vertexNormal() {
        return vn;
    }

    public ObjTripletFormat format() {
        return fmt;
    }
}
