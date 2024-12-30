package io.github.shimeoki.jshaper.obj.writer;

import java.util.Objects;

import io.github.shimeoki.jshaper.obj.Token;

public class Commenter {

    private final StringBuilder builder = new StringBuilder();

    public Commenter() {
    }

    public String parse(final String comment) {
        builder.setLength(0);

        builder.append(Token.COMMENT);
        builder.append(' ');
        builder.append(Objects.requireNonNull(comment));
        builder.append('\n');

        return builder.toString();
    }
}
