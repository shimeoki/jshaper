package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public enum ObjToken {

    NIL(""),
    COMMENT("#"),
    VERTEX("v"),
    TEXTURE_VERTEX("vt"),
    VERTEX_NORMAL("vn"),
    PARAMETER_SPACE_VERTEX("vp"),
    FACE("f"),
    GROUP_NAME("g");

    private final String txt;

    private ObjToken(final String txt) {
        this.txt = Objects.requireNonNull(txt);
    }

    @Override
    public String toString() {
        return txt;
    }

    public boolean is(final ObjToken other) {
        if (other == null) {
            return false;
        } else {
            return equals(other);
        }
    }
}
