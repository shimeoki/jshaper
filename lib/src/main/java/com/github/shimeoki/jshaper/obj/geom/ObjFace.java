package com.github.shimeoki.jshaper.obj.geom;

import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.data.ObjTriplet;

public final class ObjFace {

    private final List<ObjTriplet> triplets;

    public ObjFace(final List<ObjTriplet> triplets) {
        Objects.requireNonNull(triplets);

        this.triplets = triplets;
    }

    public List<ObjTriplet> triplets() {
        return triplets;
    }
}
