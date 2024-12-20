package io.github.shimeoki.jshaper.obj;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.shimeoki.jshaper.obj.data.ObjFile;
import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;

public final class ObjModelWriter implements ObjWriter {

    private ObjFile src;
    private File dst;

    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;

    private final Map<ObjVertex, Integer> vertexIndices = new HashMap<>();
    private final Map<ObjTextureVertex, Integer> textureVertexIndices = new HashMap<>();
    private final Map<ObjVertexNormal, Integer> vertexNormalIndices = new HashMap<>();

    public ObjModelWriter() {
    }

    @Override
    public void write(final ObjFile src, final File dst) {
        // TODO
    }
}
