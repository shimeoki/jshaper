package com.github.shimeoki.jshaper.obj.data;

import java.util.Objects;
import java.util.Set;

public final class ObjGroupingData {

    // not all grouping data from the specification
    // is present
    private final Set<ObjGroupName> groupNames;

    public ObjGroupingData(final Set<ObjGroupName> groupNames) {
        Objects.requireNonNull(groupNames);

        this.groupNames = groupNames;
    }

    public Set<ObjGroupName> groupNames() {
        return groupNames;
    }
}
