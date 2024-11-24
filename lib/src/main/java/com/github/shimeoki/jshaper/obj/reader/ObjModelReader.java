package com.github.shimeoki.jshaper.obj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.geom.ObjFace;
import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjModelReader implements ObjReader {

    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;
    private List<ObjFace> faces;

    private BufferedReader reader;

    private StringBuilder stringer;
    private List<String> strings;

    private StringBuilder tripleter;
    private String triplet;

    private int row, col;
    private String line;

    private void error(final ObjReaderExceptionType type, final String msg) throws ObjReaderException {
        final int row = this.row + 1;

        uncache();
        closeReader();

        throw new ObjReaderException(
                type, String.format("%s at row %d", msg, row));
    }

    private void openReader(final File f) throws ObjReaderException {
        final Path p = f.toPath();

        try {
            reader = Files.newBufferedReader(p);
        } catch (final IOException e) {
            error(ObjReaderExceptionType.IO, "error while opening the file");
        }
    }

    private void closeReader() throws ObjReaderException {
        try {
            reader.close();
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while closing the file");
        } finally {
            // can be unsafe, but otherwise the recursion can occur
            reader = null;
        }
    }

    private void cache() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        vertexNormals = new ArrayList<>();

        stringer = new StringBuilder();
        strings = new ArrayList<>();
    }

    private void uncache() {
        vertices = null;
        textureVertices = null;
        vertexNormals = null;

        stringer = null;
        strings = null;

        row = 0;
        col = 0;
        line = null;
    }

    private void readLine() throws ObjReaderException {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while reading the file");
        }
    }

    private float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(s);
        } catch (final NumberFormatException e) {
            error(ObjReaderExceptionType.PARSE, "invalid float format");
        }

        // shouldn't reach.
        // only for the compiler check
        return 0;
    }

    private int parseInt(final String s) throws ObjReaderException {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            error(ObjReaderExceptionType.PARSE, "invalid int format");
        }

        // shouldn't reach.
        // only for the compiler check
        return 0;
    }

    private void parseVertex() throws ObjReaderException {
        final int len = strings.size();

        if (len < 3 || len > 4) {
            error(ObjReaderExceptionType.PARSE, "invalid format for vertex");
        }

        final float x = parseFloat(strings.get(0));
        final float y = parseFloat(strings.get(1));
        final float z = parseFloat(strings.get(2));

        final Float w;
        if (len == 4) {
            w = parseFloat(strings.get(3));
        } else {
            w = null;
        }

        final ObjVertex v = new ObjVertex(x, y, z, w);
        vertices.add(v);
    }

    private void parseTextureVertex() throws ObjReaderException {
        final int len = strings.size();

        if (len < 1 || len > 3) {
            error(ObjReaderExceptionType.PARSE, "invalid format for texture vertex");
        }

        final float u = parseFloat(strings.get(0));

        final Float v;
        if (len >= 2) {
            v = parseFloat(strings.get(1));
        } else {
            v = null;
        }

        final Float w;
        if (len == 3) {
            w = parseFloat(strings.get(2));
        } else {
            w = null;
        }

        final ObjTextureVertex vt = new ObjTextureVertex(u, v, w);
        textureVertices.add(vt);
    }

    private void parseVertexNormal() throws ObjReaderException {
        if (strings.size() != 3) {
            error(ObjReaderExceptionType.PARSE, "invalid format for vertex normal");
        }

        final float i = parseFloat(strings.get(0));
        final float j = parseFloat(strings.get(1));
        final float k = parseFloat(strings.get(2));

        final ObjVertexNormal vn = new ObjVertexNormal(i, j, k);
        vertexNormals.add(vn);
    }

    private void parseFace() throws ObjReaderException {
        // TODO
    }

    private void parseLine() throws ObjReaderException {
        stringer.setLength(0);
        strings.clear();
        col = 0;

        final ObjToken lineToken = lineToken();
        if (lineToken.equals(ObjToken.COMMENT)) {
            return;
        }

        fillStrings();

        parseStrings(lineToken);
    }

    private ObjToken lineToken() throws ObjReaderException {
        final int len = line.length();

        char c;
        for (; col < len; col++) {
            c = line.charAt(col);

            // the line token cannot be prefixed with spaces
            if (c == ' ') {
                break;
            }

            stringer.append(c);
        }

        final ObjToken token = ObjTokenizer.parse(flush());
        if (token == null) {
            error(ObjReaderExceptionType.PARSE, "unknown token at the start of the line");
        }

        return token;
    }

    private void fillStrings() throws ObjReaderException {
        final int len = line.length();
        if (len == 0) {
            return;
        }

        ObjToken token;
        char c;
        String s;

        for (; col <= len; col++) {
            if (col == len) {
                c = ' '; // artificially add a space at the end
            } else {
                c = line.charAt(col);
            }

            if (c != ' ') {
                stringer.append(c);
                continue;
            }

            if (stringer.isEmpty()) {
                continue;
            }

            s = flush();
            token = ObjTokenizer.parse(s);

            if (token == null) {
                strings.add(s);
                continue;
            }

            if (token.equals(ObjToken.COMMENT)) {
                break;
            } else {
                error(ObjReaderExceptionType.PARSE,
                        "found a token not at the start of the line");
            }
        }
    }

    private String flush() {
        final String s = stringer.toString();
        stringer.setLength(0);
        return s;
    }

    private void parseStrings(final ObjToken token) throws ObjReaderException {
        switch (token) {
            case VERTEX:
                parseVertex();
                break;
            case TEXTURE_VERTEX:
                parseTextureVertex();
                break;
            case VERTEX_NORMAL:
                parseVertexNormal();
                break;
            case FACE:
                parseFace();
                break;
            default:
                error(ObjReaderExceptionType.PARSE, "unsupported token");
        }
    }

    private void parseLines() throws ObjReaderException {
        for (readLine(); line != null; readLine(), row++) {
            parseLine();
        }
    }

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        if (!f.canRead()) {
            error(ObjReaderExceptionType.IO, "file is not readable");
        }

        cache();
        openReader(f);

        parseLines();

        uncache();
        closeReader();

        // TODO
        return null;
    }
}
