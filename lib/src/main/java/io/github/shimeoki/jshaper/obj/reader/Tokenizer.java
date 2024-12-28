package io.github.shimeoki.jshaper.obj.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.Tokens;

public final class Tokenizer {

    public enum Mode {
        WHITELIST_ONLY,
        BLACKLIST_ONLY,
        WHITELIST_FIRST,
        BLACKLIST_FIRST
    }

    private Mode mode;

    private final Set<Token.Type> whitelist;
    private final Set<Token.Type> blacklist;

    private final StringBuilder builder = new StringBuilder();

    private final Tokens tokens = new Tokens();

    public Tokenizer(
            final Mode mode,
            final Set<Token.Type> whitelist,
            final Set<Token.Type> blacklist) {

        setMode(mode);

        this.whitelist = Objects.requireNonNull(whitelist);
        this.blacklist = Objects.requireNonNull(blacklist);
    }

    public static Set<Token.Type> typeSet(final Token.Type... types) {
        return new HashSet<>(Arrays.asList(Objects.requireNonNull(types)));
    }

    public Mode mode() {
        return mode;
    }

    public void setMode(final Mode mode) {
        this.mode = Objects.requireNonNull(mode);
    }

    public void parseLine(final String line) {
        final int len = Objects.requireNonNull(line).length();
        if (len == 0) {
            return;
        }

        tokens.clear();
        builder.setLength(0);

        char c;
        Token token;

        for (int i = 0; i <= len; i++) {
            if (i == len) {
                c = ' '; // artificially add a whitespace at the end
            } else {
                c = line.charAt(i);
            }

            token = new Token(String.valueOf(c));
            if (token.typeIs(Token.Type.COMMENT)) {
                if (!builder.isEmpty()) {
                    tokens.add(flushAndParse());
                }

                break;
            }

            if (appendAndContinue(c)) {
                continue;
            }

            token = flushAndParse();
            if (!allowed(token)) {
                break;
            } else {
                tokens.add(token);
            }
        }
    }

    private String flushBuilder() {
        final String s = builder.toString();
        builder.setLength(0);
        return s;
    }

    private boolean appendAndContinue(final char c) {
        if (c != ' ') {
            builder.append(c);
            return true;
        }

        if (builder.isEmpty()) {
            return true;
        }

        return false;
    }

    private Token flushAndParse() {
        return new Token(flushBuilder());
    }

    public boolean allowed(final Token token) {
        if (token == null) {
            return true;
        }

        if (token.typeIs(Token.Type.NIL)) {
            return true;
        }

        if (token.typeIs(Token.Type.COMMENT)) {
            return false;
        }

        switch (mode) {
            case WHITELIST_ONLY:
                return whitelist.contains(token.type());
            case BLACKLIST_ONLY:
                return !blacklist.contains(token.type());
            case WHITELIST_FIRST:
                if (whitelist.contains(token.type())) {
                    return true;
                } else {
                    return !blacklist.contains(token.type());
                }
            case BLACKLIST_FIRST:
                if (blacklist.contains(token.type())) {
                    return false;
                } else {
                    return whitelist.contains(token.type());
                }
            default:
                return false; // subject to change
        }
    }

    public Tokens tokens() {
        return tokens;
    }
}
