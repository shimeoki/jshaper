package io.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class ObjTokenizer {

    public static final String COMMENT = "#";
    public static final String VERTEX = "v";
    public static final String TEXTURE_VERTEX = "vt";
    public static final String VERTEX_NORMAL = "vn";
    public static final String PARAMETER_SPACE_VERTEX = "vp";
    public static final String FACE = "f";
    public static final String GROUP_NAME = "g";

    private ObjTokenizerMode mode;

    private final Set<ObjToken> whitelist;
    private final Set<ObjToken> blacklist;

    private final StringBuilder builder = new StringBuilder();

    private final List<ObjTokenized> tokens = new ArrayList<>();

    public ObjTokenizer(
            final ObjTokenizerMode mode,
            final Set<ObjToken> whitelist,
            final Set<ObjToken> blacklist) {

        setMode(mode);

        this.whitelist = Objects.requireNonNull(whitelist);
        this.blacklist = Objects.requireNonNull(blacklist);
    }

    public static Set<ObjToken> tokenSet(final ObjToken... tokens) {
        return new HashSet<>(Arrays.asList(Objects.requireNonNull(tokens)));
    }

    public static ObjToken parse(final String s) {
        switch (Objects.requireNonNull(s)) {
            case COMMENT:
                return ObjToken.COMMENT;
            case VERTEX:
                return ObjToken.VERTEX;
            case TEXTURE_VERTEX:
                return ObjToken.TEXTURE_VERTEX;
            case VERTEX_NORMAL:
                return ObjToken.VERTEX_NORMAL;
            case PARAMETER_SPACE_VERTEX:
                return ObjToken.PARAMETER_SPACE_VERTEX;
            case FACE:
                return ObjToken.FACE;
            case GROUP_NAME:
                return ObjToken.GROUP_NAME;
            default:
                return ObjToken.NIL;
        }
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
        ObjTokenized parsed;

        for (int i = 0; i <= len; i++) {
            if (i == len) {
                c = ' '; // artificially add a whitespace at the end
            } else {
                c = line.charAt(i);
            }

            parsed = new ObjTokenized(String.valueOf(c));
            if (parsed.token().is(ObjToken.COMMENT)) {
                if (!builder.isEmpty()) {
                    tokens.add(flushAndParse());
                }

                break;
            }

            if (appendAndContinue(c)) {
                continue;
            }

            parsed = flushAndParse();
            if (!allowed(parsed.token())) {
                break;
            } else {
                tokens.add(parsed);
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

    private ObjTokenized flushAndParse() {
        return new ObjTokenized(flushBuilder());
    }

    public boolean allowed(final ObjToken token) {
        if (token == null) {
            return true;
        }

        if (token.is(ObjToken.NIL)) {
            return true;
        }

        if (token.is(ObjToken.COMMENT)) {
            return false;
        }

        switch (mode) {
            case WHITELIST_ONLY:
                return whitelist.contains(token);
            case BLACKLIST_ONLY:
                return !blacklist.contains(token);
            case WHITELIST_FIRST:
                if (whitelist.contains(token)) {
                    return true;
                } else {
                    return !blacklist.contains(token);
                }
            case BLACKLIST_FIRST:
                if (blacklist.contains(token)) {
                    return false;
                } else {
                    return whitelist.contains(token);
                }
            default:
                return false; // subject to change
        }
    }

    public List<ObjTokenized> tokens() {
        return tokens;
    }

    public ObjToken lineToken() {
        if (tokens.isEmpty()) {
            return null;
        }

        return tokens.getFirst().token();
    }
}
