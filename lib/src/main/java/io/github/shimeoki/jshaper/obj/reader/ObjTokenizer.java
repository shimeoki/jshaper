package io.github.shimeoki.jshaper.obj.reader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class ObjTokenizer {

    private ObjTokenizerMode mode;

    private final Set<ObjToken.Type> whitelist;
    private final Set<ObjToken.Type> blacklist;

    private final StringBuilder builder = new StringBuilder();

    private final ObjTokens tokens = new ObjTokens();

    public ObjTokenizer(
            final ObjTokenizerMode mode,
            final Set<ObjToken.Type> whitelist,
            final Set<ObjToken.Type> blacklist) {

        setMode(mode);

        this.whitelist = Objects.requireNonNull(whitelist);
        this.blacklist = Objects.requireNonNull(blacklist);
    }

    public static Set<ObjToken.Type> typeSet(final ObjToken.Type... types) {
        return new HashSet<>(Arrays.asList(Objects.requireNonNull(types)));
    }

    public ObjTokenizerMode mode() {
        return mode;
    }

    public void setMode(final ObjTokenizerMode mode) {
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
        ObjToken token;

        for (int i = 0; i <= len; i++) {
            if (i == len) {
                c = ' '; // artificially add a whitespace at the end
            } else {
                c = line.charAt(i);
            }

            token = new ObjToken(String.valueOf(c));
            if (token.typeIs(ObjToken.Type.COMMENT)) {
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

    private ObjToken flushAndParse() {
        return new ObjToken(flushBuilder());
    }

    public boolean allowed(final ObjToken token) {
        if (token == null) {
            return true;
        }

        if (token.typeIs(ObjToken.Type.NIL)) {
            return true;
        }

        if (token.typeIs(ObjToken.Type.COMMENT)) {
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

    public ObjTokens tokens() {
        return tokens;
    }
}
