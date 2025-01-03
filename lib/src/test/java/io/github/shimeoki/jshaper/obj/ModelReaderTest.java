package io.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;

public final class ModelReaderTest {

    private final Reader reader = new ModelReader();

    private File file;
    private ObjFile obj;
    private VertexData data;

    private void readFile(final String name) {
        final String filename = String.format("%s/read.obj", name);
        final String path = getClass().getResource(filename).getPath();

        final File f = new File(path);
        assertNotNull(f);

        file = f;
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

        data = obj.vertexData();
        assertNotNull(data);
    }

    private void readObjFile(final String name) {
        readFile(name);
        readObj(file);
    }

    private void assertDataSize(
            final Integer verticesSize,
            final Integer textureVerticesSize,
            final Integer vertexNormalsSize,
            final Integer parameterVertices) {

        if (verticesSize != null) {
            assertEquals(verticesSize, data.vertices().size());
        }

        if (textureVerticesSize != null) {
            assertEquals(textureVerticesSize, data.textureVertices().size());
        }

        if (vertexNormalsSize != null) {
            assertEquals(vertexNormalsSize, data.vertexNormals().size());
        }

        if (parameterVertices != null) {
            assertEquals(parameterVertices, data.parameterVertices().size());
        }
    }

    @Test
    public void case001() {
        readObjFile("001");

        assertDataSize(15789, 28209, 26181, null);
        assertEquals(31930, obj.elements().faces().size());
        assertEquals(30, obj.groupingData().groups().size());
    }

    @Test
    public void case002() {
        readObjFile("002");

        assertDataSize(4, 4, 4, null);
    }

    @Test
    public void case003() {
        readObjFile("003");

        assertDataSize(5958, 6295, null, null);
        assertEquals(5922, obj.elements().faces().size());
    }

    @Test
    public void case004() {
        readObjFile("004");

        assertDataSize(19882, 21652, null, null);
        assertEquals(19882, obj.elements().faces().size());
    }

    @Test
    public void case005() {
        readObjFile("005");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case006() {
        readObjFile("006");

        assertDataSize(4057, 4390, null, null);
        assertEquals(4041, obj.elements().faces().size());
    }

    @Test
    public void case007() {
        readObjFile("007");

        assertDataSize(4939, 5165, null, null);
        assertEquals(4868 + 36, obj.elements().faces().size());
    }

    @Test
    public void case008() {
        readObjFile("008");

        assertDataSize(6014, 6351, null, null);
        assertEquals(5964 + 36, obj.elements().faces().size());
    }

    @Test
    public void case009() {
        readObjFile("009");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case010() {
        readObjFile("010");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case011() {
        readObjFile("011");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case012() {
        readObjFile("012");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case013() {
        readObjFile("013");

        // no validation data atm.
        // only time and "readability" check
    }

    @Test
    public void case014() {
        readObjFile("014");

        assertDataSize(692, 1528, 1384, null);
        assertEquals(1416, obj.elements().faces().size());
        assertEquals(1, obj.groupingData().groups().size());
    }

    @Test
    public void case015() {
        readObjFile("015");

        assertDataSize(5289, 10601, 10451, null);
        assertEquals(10670, obj.elements().faces().size());
        assertEquals(6, obj.groupingData().groups().size());
    }

    @Test
    public void case016() {
        readObjFile("016");

        assertDataSize(3232, 7114, 7008, null);
        assertEquals(6712, obj.elements().faces().size());
        assertEquals(7, obj.groupingData().groups().size());
    }

    @Test
    public void case017() {
        readObjFile("017");

        assertDataSize(2574, 5130, 4903, null);
        assertEquals(5208, obj.elements().faces().size());
        assertEquals(8, obj.groupingData().groups().size());
    }

    @Test
    public void case018() {
        readObjFile("018");

        assertDataSize(563, 1144, 1126, null);
        assertEquals(1118, obj.elements().faces().size());
        assertEquals(3, obj.groupingData().groups().size());
    }

    @Test
    public void case019() {
        readObjFile("019");

        // no validation data atm.
        // only time and "readability" check
    }
}
