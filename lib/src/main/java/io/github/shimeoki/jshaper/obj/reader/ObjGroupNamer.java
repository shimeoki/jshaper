package io.github.shimeoki.jshaper.obj.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.ObjTokens;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.data.ObjGroupName;

public final class ObjGroupNamer {

    private final ObjGroupName DEFAULT_GROUP_NAME = new ObjGroupName("default");

    private final Set<String> currentNames = new HashSet<>();
    private final Map<String, ObjGroupName> nameMap = new HashMap<>();

    private final Set<ObjGroupName> currentGroupNames = new HashSet<>();
    private final Set<ObjGroupName> groupNames = new HashSet<>();

    public ObjGroupNamer() {
    }

    public void parse(final ObjTokens tokens) throws ShaperError {
        if (!tokens.lineTokenTypeIs(Token.Type.GROUP_NAME)) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid group name format");
        }

        final int len = tokens.size();
        if (len < 2) {
            throw new ShaperError(ShaperError.Type.PARSE, "no names in group name statement");
        }

        currentNames.clear();
        currentGroupNames.clear();

        ObjGroupName n;
        String s;

        for (int i = 1; i < len; i++) {
            s = tokens.get(i).text();

            currentNames.add(s);
            n = nameMap.getOrDefault(s, new ObjGroupName(s));

            currentGroupNames.add(n);
            groupNames.add(n);

            if (!nameMap.containsKey(s)) {
                nameMap.put(s, n);
            }
        }
    }

    public Set<ObjGroupName> current() {
        final Set<ObjGroupName> current = new HashSet<>(currentGroupNames);

        if (current.isEmpty()) {
            current.add(DEFAULT_GROUP_NAME);
        }

        return current;
    }

    public Set<ObjGroupName> all() {
        return new HashSet<>(groupNames);
    }
}
