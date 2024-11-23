package com.github.shimeoki.jshaper.obj.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.shimeoki.jshaper.obj.data.ObjFile;

public final class ObjModelReader implements ObjReader {

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        if (!f.canRead()) {
            throw new ObjReaderException(ObjReaderExceptionType.IO, "file is not readable");
        }

        final Path p = f.toPath();

        final BufferedReader r;
        try {
            r = Files.newBufferedReader(p);
        } catch (IOException e) {
            throw new ObjReaderException(ObjReaderExceptionType.IO, "error while opening the file");
        }

        // TODO
        return null;
    }
}
