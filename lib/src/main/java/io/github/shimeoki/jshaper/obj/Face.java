package io.github.shimeoki.jshaper.obj;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.obj.data.Element;
import io.github.shimeoki.jshaper.obj.data.GroupName;
import io.github.shimeoki.jshaper.obj.data.Triplet;

public final class Face implements Element {

    private final List<Triplet> triplets;
    private final Set<GroupName> groupNames;

    public Face(
            final List<Triplet> triplets, final Set<GroupName> groupNames) {

        this.triplets = Objects.requireNonNull(triplets);
        this.groupNames = Objects.requireNonNull(groupNames);
    }

    public List<Triplet> triplets() {
        return triplets;
    }

    @Override
    public Set<GroupName> groupNames() {
        return groupNames;
    }
}
