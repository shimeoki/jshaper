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

    private File file(final String filename) {
        final String path = getClass().getResource(filename).getPath();
        return new File(path);
    }

    @Test
    public void case1() {
        final File f = file("001/001.obj");
        assertNotNull(f);

        ObjFile obj = null;
        try {
            obj = reader.read(f);
        } catch (final ObjReaderException e) {
            fail(e.getMessage());
        }

        assertNotNull(obj);

        final ObjVertexData vertexData = obj.vertexData();
        assertNotNull(vertexData);

        assertEquals(15789, vertexData.vertices().size());
        assertEquals(28209, vertexData.textureVertices().size());
        assertEquals(26181, vertexData.vertexNormals().size());
        assertEquals(31930, obj.elements().faces().size());
        assertEquals(30, obj.groupingData().groupNames().size());
    }
}
