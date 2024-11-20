package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;

import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjTriplet {

    private final ObjVertex v;
    private final ObjTextureVertex vt;
    private final ObjVertexNormal vn;

    public ObjTriplet(
            final ObjVertex v,
            final ObjTextureVertex vt,
            final ObjVertexNormal vn) {

        Objects.requireNonNull(v);

        this.v = v;
        this.vt = vt;
        this.vn = vn;
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
}
