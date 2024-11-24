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
        final File file = file("001/cnc.obj");

        ObjFile f = null;
        try {
            f = reader.read(file);
        } catch (final ObjReaderException e) {
            fail(e.getMessage());
        }

        final ObjVertexData vertexData = f.vertexData();
        assertNotNull(vertexData);

        assertEquals(15789, vertexData.vertices().size());
        assertEquals(28209, vertexData.textureVertices().size());
        assertEquals(26181, vertexData.vertexNormals().size());
        assertEquals(31930, f.elements().faces().size());
        assertEquals(30, f.groupingData().groupNames().size());
    }
}
