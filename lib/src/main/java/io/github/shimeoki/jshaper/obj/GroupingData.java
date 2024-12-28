package io.github.shimeoki.jshaper.obj;

import java.util.Objects;
import java.util.Set;

public final class GroupingData {

    // not all grouping data from the specification
    // is present
    private final Set<GroupName> groupNames;

    public GroupingData(final Set<GroupName> groupNames) {
        this.groupNames = Objects.requireNonNull(groupNames);
    }

    public Set<GroupName> groupNames() {
        return groupNames;
    }
}
