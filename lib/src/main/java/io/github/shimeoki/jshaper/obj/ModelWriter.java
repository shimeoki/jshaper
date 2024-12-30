package io.github.shimeoki.jshaper.obj;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.writer.Commenter;
import io.github.shimeoki.jshaper.obj.writer.Unfacer;
import io.github.shimeoki.jshaper.obj.writer.Untripleter;
import io.github.shimeoki.jshaper.obj.writer.Unvertexer;

public final class ModelWriter implements Writer {

    private BufferedWriter writer;

    private Unvertexer unvertexer;
    private Untripleter untripleter;
    private Unfacer unfacer;
    private Commenter commenter;

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;
    private List<Face> faces;

    public ModelWriter() {
    }

    private void open(final ObjFile src, final File dst) throws ShaperError {
        cache(src);
        openWriter(dst);
    }

    private void close() throws ShaperError {
        uncache();
        closeWriter();
    }

    private void openWriter(final File f) throws ShaperError {
        try {
            if (!f.createNewFile()) {
                throw new ShaperError(ShaperError.Type.IO,
                        "new file cannot be created");
            }

            writer = Files.newBufferedWriter(f.toPath());
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "error occured while opening the file");
        }
    }

    private void closeWriter() throws ShaperError {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "error while closing the file");
        } finally {
            writer = null;
        }
    }

    private void cache(final ObjFile f) {
        unvertexer = new Unvertexer();
        untripleter = new Untripleter();
        unfacer = new Unfacer(untripleter);
        commenter = new Commenter();

        final VertexData d = f.vertexData();
        vertices = d.vertices();
        textureVertices = d.textureVertices();
        vertexNormals = d.vertexNormals();

        faces = f.elements().faces();
    }

    private void uncache() {
        unvertexer = null;
        untripleter = null;
        unfacer = null;
        commenter = null;

        vertices = null;
        textureVertices = null;
        vertexNormals = null;

        faces = null;
    }

    @Override
    public void write(final ObjFile src, final File dst) throws ShaperError {
        open(src, dst);

        writeStats();
        writeVertexData();
        writeFaces();

        close();
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
        for (final Vertex v : vertices) {
            writer.write(unvertexer.parseVertex(v));
            writer.newLine();
            untripleter.addVertex(v);
        }
    }

    private void writeTextureVertices() throws IOException {
        for (final TextureVertex vt : textureVertices) {
            writer.write(unvertexer.parseTextureVertex(vt));
            writer.newLine();
            untripleter.addTextureVertex(vt);
        }
    }

    private void writeVertexNormals() throws IOException {
        for (final VertexNormal vn : vertexNormals) {
            writer.write(unvertexer.parseVertexNormal(vn));
            writer.newLine();
            untripleter.addVertexNormal(vn);
        }
    }

    private void writeFaces() throws ShaperError {
        try {
            for (final Face f : faces) {
                writer.write(unfacer.parse(f));
                writer.newLine();
            }
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "io error occured while writing the faces");
        }
    }

    private void writeStats() throws ShaperError {
        // TODO don't hardcode the version
        try {
            writer.write(commenter.parse(
                    "generated by jshaper 0.14.0\n"));
            writer.write(commenter.parse(
                    String.format("%s vertices\n", vertices.size())));
            writer.write(commenter.parse(
                    String.format("%s texture vertices\n", textureVertices.size())));
            writer.write(commenter.parse(
                    String.format("%s faces\n", faces.size())));
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "error occured while writing the stats");
        }
    }
}
