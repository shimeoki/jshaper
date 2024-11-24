package com.github.shimeoki.jshaper.obj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.data.ObjVertexData;
import com.github.shimeoki.jshaper.obj.reader.ObjReaderException;

public final class ObjModelReaderTest {

    private final ObjModelReader reader = new ObjModelReader();

    private File file(final String name) {
        final String filename = String.format("%s/%s.obj", name, name);
        final String path = getClass().getResource(filename).getPath();

        final File f = new File(path);
        assertNotNull(f);

        return f;
    }

    private ObjFile obj(final File f) {
        ObjFile obj = null;
        try {
            obj = reader.read(f);
        } catch (final ObjReaderException e) {
            fail(e.getMessage());
        }

        assertNotNull(obj);

        return obj;
    }

    @Test
    public void case1() {
        final File f = file("001");
        final ObjFile obj = obj(f);

        final ObjVertexData data = obj.vertexData();
        assertNotNull(data);

        assertEquals(15789, data.vertices().size());
        assertEquals(28209, data.textureVertices().size());
        assertEquals(26181, data.vertexNormals().size());
        assertEquals(31930, obj.elements().faces().size());
        assertEquals(30, obj.groupingData().groupNames().size());
    }

    @Test
    public void case2() {
        final File f = file("002");
        final ObjFile obj = obj(f);

        final ObjVertexData data = obj.vertexData();
        assertNotNull(data);

        assertEquals(4, data.vertices().size());
        assertEquals(4, data.textureVertices().size());
        assertEquals(4, data.vertexNormals().size());
        // parameter space vertices are not supported yet
    }
}
