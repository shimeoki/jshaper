package io.github.shimeoki.jshaper.obj.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.Tokens;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.GroupName;

public final class GroupNamer {

    private final GroupName DEFAULT_GROUP_NAME = new GroupName("default");

    private final Set<String> currentNames = new HashSet<>();
    private final Map<String, GroupName> nameMap = new HashMap<>();

    private final Set<GroupName> currentGroupNames = new HashSet<>();
    private final Set<GroupName> groupNames = new HashSet<>();

    public GroupNamer() {
    }

    public void parse(final Tokens tokens) throws ShaperError {
        if (!tokens.lineTokenTypeIs(Token.Type.GROUP_NAME)) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid group name format");
        }

        final int len = tokens.size();
        if (len < 2) {
            throw new ShaperError(ShaperError.Type.PARSE, "no names in group name statement");
        }

        currentNames.clear();
        currentGroupNames.clear();

        GroupName n;
        String s;

        for (int i = 1; i < len; i++) {
            s = tokens.get(i).text();

            currentNames.add(s);
            n = nameMap.getOrDefault(s, new GroupName(s));

            currentGroupNames.add(n);
            groupNames.add(n);

            if (!nameMap.containsKey(s)) {
                nameMap.put(s, n);
            }
        }
    }

    public Set<GroupName> current() {
        final Set<GroupName> current = new HashSet<>(currentGroupNames);

        if (current.isEmpty()) {
            current.add(DEFAULT_GROUP_NAME);
        }

        return current;
    }

    public Set<GroupName> all() {
        return new HashSet<>(groupNames);
    }
}
