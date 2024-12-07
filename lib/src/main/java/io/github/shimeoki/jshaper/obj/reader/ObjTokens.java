package io.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.List;

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
}
