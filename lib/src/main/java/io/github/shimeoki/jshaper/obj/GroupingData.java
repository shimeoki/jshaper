package io.github.shimeoki.jshaper.obj;

import java.util.Objects;
import java.util.Set;

public final class GroupingData {

    // not all grouping data from the specification
    // is present
    private final Set<Group> groups;

    public GroupingData(final Set<Group> groups) {
        this.groups = Objects.requireNonNull(groups);
    }

    public Set<Group> groups() {
        return groups;
    }
}
