package io.github.shimeoki.jshaper.obj;

import java.io.File;

import io.github.shimeoki.jshaper.obj.data.ObjFile;

@FunctionalInterface
public interface ObjWriter {

    public void write(final ObjFile src, final File dst);
}
