package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;

public final class ModelWriterTest {

    private final Reader reader = new ModelReader();
    private final Writer writer = new ModelWriter();

    private File file;
    private ObjFile obj;

    private void readFile(final String name) {
        final String filename = String.format("%s/read.obj", name);
        final String path = getClass().getResource(filename).getPath();

        final File f = new File(path);
        assertNotNull(f);

        file = f;
    }

    private void writeFile(final String name) {
        file = new File(String.format("%s/write.obj", file.getParent()));
        if (file.exists()) {
            file.delete();
        }
    }

    private void readObj(final File f) {
        ObjFile obj = null;
        try {
            obj = reader.read(f);
        } catch (final ShaperError e) {
            fail(e.getMessage());
        }

        assertNotNull(obj);
        this.obj = obj;
    }

    private void readObjFile(final String name) {
        readFile(name);
        readObj(file);
    }

    private void setup(final String name) {
        readObjFile(name);
        writeFile(name);
    }

    private void write() {
        try {
            writer.write(obj, file);
        } catch (final ShaperError e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void case001() {
        setup("001");
        write();
        // FIXME check the result
    }
}
