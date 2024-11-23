package com.github.shimeoki.jshaper.obj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjModelReader implements ObjReader {

    // TODO
    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;

    // TODO
    private StringBuilder stringer;
    private List<String> strings;

    private BufferedReader reader(final File f) throws ObjReaderException {
        final Path p = f.toPath();

        final BufferedReader r;
        try {
            r = Files.newBufferedReader(p);
        } catch (final IOException e) {
            throw new ObjReaderException(ObjReaderExceptionType.IO, "error while opening the file");
        }

        return r;
    }

    private float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(s);
        } catch (final NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid float format");
        }
    }

    private void parseVertex() throws ObjReaderException {
        final int len = strings.size();

        if (len < 3 || len > 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for vertex");
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
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for texture vertex");
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
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for vertex normal");
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

    private void parseLine(final String line) throws ObjReaderException {
        final int len = line.length();
        if (len == 0) {
            return;
        }

        stringer.setLength(0);
        strings.clear();

        ObjToken lineToken = null, token = null;

        char c;
        String s;

        for (int i = 0; i < len; i++) {
            c = line.charAt(i);

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
                if (lineToken == null) {
                    throw new ObjReaderException(ObjReaderExceptionType.PARSE,
                            "invalid token at the start of the line");
                }

                strings.add(s);
                continue;
            }

            if (token.equals(ObjToken.COMMENT)) {
                break;
            }

            if (lineToken == null) {
                lineToken = token;
            } else {
                throw new ObjReaderException(ObjReaderExceptionType.PARSE, "token found not at the start of the line");
            }
        }
    }

    private String flush() {
        final String s = stringer.toString();
        stringer.setLength(0);
        return s;
    }

    private void redirectLine(final ObjToken token, final String line) throws ObjReaderException {
        // TODO
        final String[] parts = null;

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
                throw new ObjReaderException(ObjReaderExceptionType.PARSE, "unsupported token");
        }
    }

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        if (!f.canRead()) {
            throw new ObjReaderException(ObjReaderExceptionType.IO, "file is not readable");
        }

        // TODO
        return null;
    }
}
