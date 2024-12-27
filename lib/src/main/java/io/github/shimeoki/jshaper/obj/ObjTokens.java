package io.github.shimeoki.jshaper.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ObjTokens {

    private final List<ObjToken> tokens = new ArrayList<>();

    public ObjTokens() {
    }

    public boolean empty() {
        return tokens.isEmpty();
    }

    public void clear() {
        tokens.clear();
    }

    public ObjToken lineToken() {
        if (empty()) {
            return null;
        } else {
            return tokens.getFirst();
        }
    }

    public boolean lineTokenTypeIs(final ObjToken.Type type) {
        if (empty()) {
            return false;
        } else {
            return lineToken().typeIs(type);
        }
    }

    public void add(final ObjToken t) {
        tokens.add(Objects.requireNonNull(t));
    }

    public ObjToken get(final int index) {
        if (!validIndex(index)) {
            throw new IllegalArgumentException("invalid index");
        }

        return tokens.get(index);
    }

    public int size() {
        return tokens.size();
    }

    public boolean validIndex(final int i) {
        if (i < 0) {
            return false;
        } else if (i >= size()) {
            return false;
        } else {
            return true;
        }
    }
}
