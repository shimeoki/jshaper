package io.github.shimeoki.jshaper.obj;

import java.io.File;

import io.github.shimeoki.jshaper.obj.data.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;

@FunctionalInterface
public interface Reader {

    public ObjFile read(final File f) throws ShaperError;
}
