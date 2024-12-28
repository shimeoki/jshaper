package io.github.shimeoki.jshaper.obj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.shimeoki.jshaper.ObjFile;

public final class ModelWriter implements Writer {

    private ObjFile src;
    private File dst;

    private BufferedWriter writer;

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;

    private final Map<Vertex, Integer> vertexIndices = new HashMap<>();
    private final Map<TextureVertex, Integer> textureVertexIndices = new HashMap<>();
    private final Map<VertexNormal, Integer> vertexNormalIndices = new HashMap<>();

    public ModelWriter() {
    }

    private void open(final File f) {
        openWriter(f);
    }

    private void openWriter(final File f) {
        try {
            writer = Files.newBufferedWriter(f.toPath());
        } catch (final IOException e) {
            // TODO throw error
            return;
        }
    }

    @Override
    public void write(final ObjFile src, final File dst) {
        // TODO
    }
}
