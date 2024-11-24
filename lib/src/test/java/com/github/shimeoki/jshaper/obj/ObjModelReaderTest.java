package com.github.shimeoki.jshaper.obj;

import java.io.File;

public final class ObjModelReaderTest {

    private File file(final String filename) {
        final String path = getClass().getResource(filename).getPath();
        return new File(path);
    }
}
