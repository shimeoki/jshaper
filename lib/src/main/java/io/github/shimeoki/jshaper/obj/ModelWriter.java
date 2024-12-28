package io.github.shimeoki.jshaper.obj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.writer.Unvertexer;

public final class ModelWriter implements Writer {

    private ObjFile src;
    private File dst;

    private BufferedWriter writer;

    private Unvertexer unvertexer;

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;

    private final Map<Vertex, Integer> vertexIndices = new HashMap<>();
    private final Map<TextureVertex, Integer> textureVertexIndices = new HashMap<>();
    private final Map<VertexNormal, Integer> vertexNormalIndices = new HashMap<>();

    private int vertexDataIndex;

    public ModelWriter() {
    }

    private void open(final File f) {
        openWriter(f);
        unvertexer = new Unvertexer();
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

    private void writeVertexData() throws ShaperError {
        try {
            writeVertices();
            writeTextureVertices();
            writeVertexNormals();
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "io error occured while writing the vertex data");
        }
    }

    private void writeVertices() throws IOException {
        vertexDataIndex = 1;

        for (final Vertex v : vertices) {
            writer.write(unvertexer.parseVertex(v));
            writer.newLine();
            vertexIndices.put(v, vertexDataIndex);
        }
    }

    private void writeTextureVertices() throws IOException {
        vertexDataIndex = 1;

        for (final TextureVertex vt : textureVertices) {
            writer.write(unvertexer.parseTextureVertex(vt));
            writer.newLine();
            textureVertexIndices.put(vt, vertexDataIndex);
        }
    }

    private void writeVertexNormals() throws IOException {
        vertexDataIndex = 1;

        for (final VertexNormal vn : vertexNormals) {
            writer.write(unvertexer.parseVertexNormal(vn));
            writer.newLine();
            vertexNormalIndices.put(vn, vertexDataIndex);
        }
    }
}
