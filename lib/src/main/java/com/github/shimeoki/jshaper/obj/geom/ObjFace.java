package com.github.shimeoki.jshaper.obj.geom;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.github.shimeoki.jshaper.obj.data.ObjElement;
import com.github.shimeoki.jshaper.obj.data.ObjGroupName;
import com.github.shimeoki.jshaper.obj.data.ObjTriplet;

public final class ObjFace implements ObjElement {

    private final List<ObjTriplet> triplets;
    private final Set<ObjGroupName> groupNames;

    public ObjFace(final List<ObjTriplet> triplets, final Set<ObjGroupName> groupNames) {
        Objects.requireNonNull(triplets);
        Objects.requireNonNull(groupNames);

        if (triplets.size() < 3) {
            throw new IllegalArgumentException("ObjFace: triplets.size() < 3");
        }

        this.triplets = triplets;
        this.groupNames = groupNames;
    }

    public List<ObjTriplet> triplets() {
        return triplets;
    }

    @Override
    public Set<ObjGroupName> groupNames() {
        return groupNames;
    }
}
