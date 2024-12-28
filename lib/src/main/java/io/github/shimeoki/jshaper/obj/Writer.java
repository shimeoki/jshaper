package io.github.shimeoki.jshaper.obj;

import java.io.File;

import io.github.shimeoki.jshaper.ObjFile;

@FunctionalInterface
public interface Writer {

    public void write(final ObjFile src, final File dst);
}
