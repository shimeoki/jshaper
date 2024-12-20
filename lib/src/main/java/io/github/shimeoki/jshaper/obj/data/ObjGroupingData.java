package io.github.shimeoki.jshaper.obj.data;

import java.util.Objects;
import java.util.Set;

public final class ObjGroupingData {

    // not all grouping data from the specification
    // is present
    private final Set<ObjGroupName> groupNames;

    public ObjGroupingData(final Set<ObjGroupName> groupNames) {
        this.groupNames = Objects.requireNonNull(groupNames);
    }

    public Set<ObjGroupName> groupNames() {
        return groupNames;
    }
}
