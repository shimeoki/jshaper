package com.github.shimeoki.jshaper.obj.reader;

import java.io.File;

import com.github.shimeoki.jshaper.obj.data.ObjFile;

public interface ObjReader {

    public ObjFile read(final File f);
}
