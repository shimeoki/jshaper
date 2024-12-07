package io.github.shimeoki.jshaper.obj;

import java.io.File;

import io.github.shimeoki.jshaper.obj.data.ObjFile;
import io.github.shimeoki.jshaper.obj.reader.ObjReaderException;

@FunctionalInterface
public interface ObjReader {

    public ObjFile read(final File f) throws ObjReaderException;
}
