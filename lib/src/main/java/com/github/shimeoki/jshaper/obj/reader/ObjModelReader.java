package com.github.shimeoki.jshaper.obj.reader;

import java.io.File;

import com.github.shimeoki.jshaper.obj.data.ObjFile;

public final class ObjModelReader implements ObjReader {

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        if (!f.canRead()) {
            throw new IllegalArgumentException("ObjModelReader::read: file is not readable");
        }

        // TODO
        return null;
    }
}
