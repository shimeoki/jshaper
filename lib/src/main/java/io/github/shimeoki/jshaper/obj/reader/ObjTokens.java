package io.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ObjTokens {

    private final List<ObjTokenized> tokens = new ArrayList<>();

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
            return tokens.getFirst().token();
        }
    }

    public void add(final ObjTokenized token) {
        tokens.add(Objects.requireNonNull(token));
    }

    public ObjTokenized tokenized(final int index) {
        if (!validIndex(index)) {
            throw new IllegalArgumentException("invalid index");
        }

        return tokens.get(index);
    }

    public ObjToken token(final int index) {
        if (!validIndex(index)) {
            throw new IllegalArgumentException("invalid index");
        }

        return tokens.get(index).token();
    }

    public String value(final int index) {
        if (!validIndex(index)) {
            throw new IllegalArgumentException("invalid index");
        }

        return tokens.get(index).value();
    }

    public int size() {
        return tokens.size();
    }

    public boolean validIndex(final int index) {
        if (index < 0) {
            return false;
        } else if (index >= size()) {
            return false;
        } else {
            return true;
        }
    }
}
