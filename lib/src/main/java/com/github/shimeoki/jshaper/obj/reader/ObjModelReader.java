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

    private void parseVertex(final String[] parts) throws ObjReaderException {
        if (parts.length < 3 || parts.length > 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for vertex");
        }

        final float x = parseFloat(parts[0]);
        final float y = parseFloat(parts[1]);
        final float z = parseFloat(parts[2]);

        final Float w;
        if (parts.length == 4) {
            w = parseFloat(parts[3]);
        } else {
            w = null;
        }

        final ObjVertex v = new ObjVertex(x, y, z, w);
        vertices.add(v);
    }

    private void parseTextureVertex(final String[] parts) throws ObjReaderException {
        if (parts.length < 1 || parts.length > 3) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for texture vertex");
        }

        final float u = parseFloat(parts[0]);

        final Float v;
        if (parts.length >= 2) {
            v = parseFloat(parts[1]);
        } else {
            v = null;
        }

        final Float w;
        if (parts.length == 3) {
            w = parseFloat(parts[2]);
        } else {
            w = null;
        }

        final ObjTextureVertex vt = new ObjTextureVertex(u, v, w);
        textureVertices.add(vt);
    }

    private void parseVertexNormal(final String[] parts) throws ObjReaderException {
        if (parts.length != 3) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid format for vertex normal");
        }

        final float i = parseFloat(parts[0]);
        final float j = parseFloat(parts[1]);
        final float k = parseFloat(parts[2]);

        final ObjVertexNormal vn = new ObjVertexNormal(i, j, k);
        vertexNormals.add(vn);
    }

    private void parseFace(final String[] parts) throws ObjReaderException {
        // TODO
    }

    private void parseLine(final ObjToken token, final String line) throws ObjReaderException {
        // TODO
        final String[] parts = null;

        switch (token) {
            case VERTEX:
                parseVertex(parts);
                break;
            case TEXTURE_VERTEX:
                parseTextureVertex(parts);
                break;
            case VERTEX_NORMAL:
                parseVertexNormal(parts);
                break;
            case FACE:
                parseFace(parts);
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
