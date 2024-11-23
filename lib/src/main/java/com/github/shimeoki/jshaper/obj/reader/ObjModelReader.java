package com.github.shimeoki.jshaper.obj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;

public final class ObjModelReader implements ObjReader {

    private final List<ObjVertex> vertices = new ArrayList<>();

    private BufferedReader reader(final File f) throws ObjReaderException {
        final Path p = f.toPath();

        final BufferedReader r;
        try {
            r = Files.newBufferedReader(p);
        } catch (IOException e) {
            throw new ObjReaderException(ObjReaderExceptionType.IO, "error while opening the file");
        }

        return r;
    }

    private float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid float format");
        }
    }

    private void parseVertex(final String line) throws ObjReaderException {
        final String[] parts = line.split(" +");

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

    private void parseTextureVertex(final String line) throws ObjReaderException {
        // TODO
    }

    private void parseVertexNormal(final String line) throws ObjReaderException {
        // TODO
    }

    private void parseFace(final String line) throws ObjReaderException {
        // TODO
    }

    private void parseLine(final ObjToken token, final String line) throws ObjReaderException {
        switch (token) {
            case VERTEX:
                parseVertex(line);
                break;
            case TEXTURE_VERTEX:
                parseTextureVertex(line);
                break;
            case VERTEX_NORMAL:
                parseVertexNormal(line);
                break;
            case FACE:
                parseFace(line);
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
