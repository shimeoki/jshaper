package io.github.shimeoki.jshaper.obj.reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.Tokens;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.Group;

public final class Grouper {

    private final Group DEFAULT_GROUP = new Group("default");

    private final Set<String> currentNames = new HashSet<>();
    private final Map<String, Group> nameMap = new HashMap<>();

    private final Set<Group> currentGroups = new HashSet<>();
    private final Set<Group> allGroups = new HashSet<>();

    public Grouper() {
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
        currentGroups.clear();

        Group g;
        String s;

        for (int i = 1; i < len; i++) {
            s = tokens.get(i).text();

            currentNames.add(s);
            g = nameMap.getOrDefault(s, new Group(s));

            currentGroups.add(g);
            allGroups.add(g);

            if (!nameMap.containsKey(s)) {
                nameMap.put(s, g);
            }
        }
    }

    public Set<Group> current() {
        final Set<Group> current = new HashSet<>(currentGroups);

        if (current.isEmpty()) {
            current.add(DEFAULT_GROUP);
        }

        return current;
    }

    public Set<Group> all() {
        return new HashSet<>(allGroups);
    }
}
