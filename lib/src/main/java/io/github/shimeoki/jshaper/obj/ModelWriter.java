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
import io.github.shimeoki.jshaper.obj.writer.Untripleter;
import io.github.shimeoki.jshaper.obj.writer.Unvertexer;

public final class ModelWriter implements Writer {

    private BufferedWriter writer;

    private Unvertexer unvertexer;
    private Untripleter untripleter;

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;
    private List<Face> faces;

    private final Map<Vertex, Integer> vertexIndices = new HashMap<>();
    private final Map<TextureVertex, Integer> textureVertexIndices = new HashMap<>();
    private final Map<VertexNormal, Integer> vertexNormalIndices = new HashMap<>();

    private Triplet.Format format; // FIXME check format
    private StringBuilder faceBuilder;
    private StringBuilder commentBuilder;

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
        faceBuilder = new StringBuilder();
        commentBuilder = new StringBuilder();

        final VertexData d = f.vertexData();
        vertices = d.vertices();
        textureVertices = d.textureVertices();
        vertexNormals = d.vertexNormals();

        faces = f.elements().faces();
    }

    private void uncache() {
        unvertexer = null;
        untripleter = null;
        faceBuilder = null;
        commentBuilder = null;

        vertices = null;
        textureVertices = null;
        vertexNormals = null;

        faces = null;

        vertexIndices.clear();
        textureVertexIndices.clear();
        vertexNormalIndices.clear();
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
        for (final Face f : faces) {
            writeFace(f);
        }
    }

    private void writeFace(final Face f) throws ShaperError {
        final List<Triplet> triplets = f.triplets();
        if (triplets.size() < 3) {
            throw new ShaperError(ShaperError.Type.PARSE,
                    "less than 3 triplets in a face");
        }

        format = triplets.get(0).format();

        faceBuilder.append(Token.FACE);
        faceBuilder.append(' ');

        for (final Triplet t : triplets) {
            faceBuilder.append(untripleter.parse(t));
            faceBuilder.append(' ');
        }

        try {
            writer.write(faceBuilder.toString());
            writer.newLine();
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "io error occured while writing the faces");
        }

        faceBuilder.setLength(0);
    }

    private void writeStats() throws ShaperError {
        // TODO don't hardcode the version
        commentBuilder.append("# generated by jshaper 0.14.0\n");
        commentBuilder.append(String.format("# %s vertices\n",
                vertices.size()));
        commentBuilder.append(String.format("# %s texture vertices\n",
                textureVertices.size()));
        commentBuilder.append(String.format("# %s vertex normals\n",
                vertexNormals.size()));
        commentBuilder.append(String.format("# %s faces\n",
                faces.size()));

        try {
            writer.write(commentBuilder.toString());
            writer.newLine();
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "error occured while writing the stats");
        }
    }
}
