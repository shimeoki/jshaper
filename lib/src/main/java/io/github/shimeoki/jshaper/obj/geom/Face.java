package io.github.shimeoki.jshaper.obj.geom;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.obj.data.Element;
import io.github.shimeoki.jshaper.obj.data.GroupName;
import io.github.shimeoki.jshaper.obj.data.ObjTriplet;

public final class Face implements Element {

    private final List<ObjTriplet> triplets;
    private final Set<GroupName> groupNames;

    public Face(
            final List<ObjTriplet> triplets, final Set<GroupName> groupNames) {

        this.triplets = Objects.requireNonNull(triplets);
        this.groupNames = Objects.requireNonNull(groupNames);
    }

    public List<ObjTriplet> triplets() {
        return triplets;
    }

    @Override
    public Set<GroupName> groupNames() {
        return groupNames;
    }
}
