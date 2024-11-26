package com.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
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

    public ObjTokenizer(
            final ObjTokenizerMode mode,
            final Set<ObjToken> whitelist,
            final Set<ObjToken> blacklist) {

        setMode(mode);

        Objects.requireNonNull(whitelist);
        Objects.requireNonNull(blacklist);

        this.whitelist = whitelist;
        this.blacklist = blacklist;
    }

    public static ObjToken parse(final String s) {
        switch (s) {
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
                return null;
        }
    }

    public ObjTokenizerMode mode() {
        return mode;
    }

    public void setMode(final ObjTokenizerMode mode) {
        Objects.requireNonNull(mode);

        this.mode = mode;
    }

    public List<ObjParsedString> parseLine(final String line) {
        Objects.requireNonNull(line);

        final List<ObjParsedString> parsedLine = new ArrayList<>();

        final int len = line.length();
        if (len == 0) {
            return parsedLine;
        }

        char c;
        ObjParsedString parsed;
        ObjToken token;

        for (int i = 0; i <= len; i++) {
            if (i == len) {
                c = ' '; // artificially add a whitespace at the end
            } else {
                c = line.charAt(i);
            }

            if (isComment(c)) {
                if (!builder.isEmpty()) {
                    parsedLine.add(flushAndParse());
                }

                break;
            }

            if (appendAndContinue(c)) {
                continue;
            }

            parsed = flushAndParse();
            token = parsed.token();

            if (!allowed(token) || token.equals(ObjToken.COMMENT)) {
                break;
            } else {
                parsedLine.add(parsed);
            }
        }

        return parsedLine;
    }

    private boolean isComment(final char c) {
        final ObjParsedString parsed = new ObjParsedString(String.valueOf(c));
        return parsed.token().equals(ObjToken.COMMENT);
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

    private ObjParsedString flushAndParse() {
        return new ObjParsedString(flushBuilder());
    }

    public boolean allowed(final ObjToken token) {
        if (token == null) {
            return true;
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
}
