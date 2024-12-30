package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private ObjFile read;
    private ObjFile write;

    private void readFile(final String name) {
        final String filename = String.format("%s/read.obj", name);
        final String path = getClass().getResource(filename).getPath();

        final File f = new File(path);
        assertNotNull(f);

        file = f;
    }

    private void setWriteFile() {
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
        read = obj;
    }

    private void readObjFile(final String name) {
        readFile(name);
        readObj(file);
    }

    private void writeObjFile() {
        try {
            writer.write(read, file);
        } catch (final ShaperError e) {
            fail(e.getMessage());
        }
    }

    private void readWrittenObj() {
        ObjFile obj = null;
        try {
            obj = reader.read(file);
        } catch (final ShaperError e) {
            fail(e.getMessage());
        }

        assertNotNull(obj);
        write = obj;
    }

    private void verify() {
        final VertexData readData = read.vertexData();
        final VertexData writeData = write.vertexData();

        assertEquals(readData.vertices().size(), writeData.vertices().size());
        assertEquals(readData.textureVertices().size(), writeData.textureVertices().size());
        assertEquals(readData.vertexNormals().size(), writeData.vertexNormals().size());
        assertEquals(read.elements().faces().size(), write.elements().faces().size());
    }

    private void test(final String name) {
        readObjFile(name);
        setWriteFile();
        writeObjFile();
        readWrittenObj();
        verify();

    }

    @Test
    public void case001() {
        test("001");
    }
}
