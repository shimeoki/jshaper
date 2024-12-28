package io.github.shimeoki.jshaper.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Tokens {

    private final List<Token> tokens = new ArrayList<>();

    public Tokens() {
    }

    public boolean empty() {
        return tokens.isEmpty();
    }

    public void clear() {
        tokens.clear();
    }

    public Token lineToken() {
        if (empty()) {
            return null;
        } else {
            return tokens.getFirst();
        }
    }

    public boolean lineTokenTypeIs(final Token.Type type) {
        if (empty()) {
            return false;
        } else {
            return lineToken().typeIs(type);
        }
    }

    public void add(final Token t) {
        tokens.add(Objects.requireNonNull(t));
    }

    public Token get(final int index) {
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
