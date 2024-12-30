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

    @Test
    public void case002() {
        test("002");
    }

    @Test
    public void case003() {
        test("003");
    }

    @Test
    public void case004() {
        test("004");
    }

    @Test
    public void case005() {
        test("005");
    }

    @Test
    public void case006() {
        test("006");
    }

    @Test
    public void case007() {
        test("007");
    }

    @Test
    public void case008() {
        test("008");
    }

    @Test
    public void case009() {
        test("009");
    }

    @Test
    public void case010() {
        test("010");
    }

    @Test
    public void case011() {
        test("011");
    }

    @Test
    public void case012() {
        test("012");
    }

    @Test
    public void case013() {
        test("013");
    }

    @Test
    public void case014() {
        test("014");
    }

    @Test
    public void case015() {
        test("015");
    }

    @Test
    public void case016() {
        test("016");
    }

    @Test
    public void case017() {
        test("017");
    }

    @Test
    public void case018() {
        test("018");
    }

    @Test
    public void case019() {
        test("019");
    }
}
