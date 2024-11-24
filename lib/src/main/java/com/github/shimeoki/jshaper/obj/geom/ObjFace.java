package com.github.shimeoki.jshaper.obj.geom;

import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.data.ObjElement;
import com.github.shimeoki.jshaper.obj.data.ObjGroupName;
import com.github.shimeoki.jshaper.obj.data.ObjTriplet;

public final class ObjFace implements ObjElement {

    private final List<ObjTriplet> triplets;

    public ObjFace(final List<ObjTriplet> triplets) {
        Objects.requireNonNull(triplets);

        if (triplets.size() < 3) {
            throw new IllegalArgumentException("ObjFace: triplets.size() < 3");
        }

        this.triplets = triplets;
    }

    public List<ObjTriplet> triplets() {
        return triplets;
    }

    @Override
    public ObjGroupName groupName() {
        // TODO
        return null;
    }
}
