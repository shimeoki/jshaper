package io.github.shimeoki.jshaper.obj;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class Face implements Element {

    private final List<Triplet> triplets;
    private final Set<Group> groups;

    public Face(
            final List<Triplet> triplets, final Set<Group> groups) {

        this.triplets = Objects.requireNonNull(triplets);
        this.groups = Objects.requireNonNull(groups);
    }

    public List<Triplet> triplets() {
        return triplets;
    }

    @Override
    public Set<Group> groups() {
        return groups;
    }
}
