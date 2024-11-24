package com.github.shimeoki.jshaper.obj;

import java.io.File;

import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.reader.ObjReaderException;

@FunctionalInterface
public interface ObjReader {

    public ObjFile read(final File f) throws ObjReaderException;
}
