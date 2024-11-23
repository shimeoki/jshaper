package com.github.shimeoki.jshaper.obj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.shimeoki.jshaper.obj.data.ObjFile;

public final class ObjModelReader implements ObjReader {

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

    private void parseVertex(final String line) throws ObjReaderException {
        // TODO
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
