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

    private BufferedWriter writer;

    private Unvertexer unvertexer;

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;
    private List<Face> faces;

    private final Map<Vertex, Integer> vertexIndices = new HashMap<>();
    private final Map<TextureVertex, Integer> textureVertexIndices = new HashMap<>();
    private final Map<VertexNormal, Integer> vertexNormalIndices = new HashMap<>();

    private int vertexDataIndex;

    private Triplet.Format format;
    private StringBuilder faceBuilder;
    private StringBuilder tripletBuilder;

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
        faceBuilder = new StringBuilder();
        tripletBuilder = new StringBuilder();

        final VertexData d = f.vertexData();
        vertices = d.vertices();
        textureVertices = d.textureVertices();
        vertexNormals = d.vertexNormals();

        faces = f.elements().faces();
    }

    private void uncache() {
        unvertexer = null;
        faceBuilder = null;
        tripletBuilder = null;

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
        vertexDataIndex = 1;

        for (final Vertex v : vertices) {
            writer.write(unvertexer.parseVertex(v));
            writer.newLine();
            vertexIndices.put(v, vertexDataIndex);
            vertexDataIndex++;
        }
    }

    private void writeTextureVertices() throws IOException {
        vertexDataIndex = 1;

        for (final TextureVertex vt : textureVertices) {
            writer.write(unvertexer.parseTextureVertex(vt));
            writer.newLine();
            textureVertexIndices.put(vt, vertexDataIndex);
            vertexDataIndex++;
        }
    }

    private void writeVertexNormals() throws IOException {
        vertexDataIndex = 1;

        for (final VertexNormal vn : vertexNormals) {
            writer.write(unvertexer.parseVertexNormal(vn));
            writer.newLine();
            vertexNormalIndices.put(vn, vertexDataIndex);
            vertexDataIndex++;
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
            faceBuilder.append(writeTriplet(t));
            faceBuilder.append(' ');
        }

        try {
            writer.write(faceBuilder.toString());
            writer.newLine();
        } catch (final IOException e) {
            throw new ShaperError(ShaperError.Type.IO,
                    "io error occured while writing the vertex data");
        }

        faceBuilder.setLength(0);
    }

    private String writeTriplet(final Triplet t) throws ShaperError {
        tripletBuilder.setLength(0);
        switch (format) {
            case VERTEX:
                return writeVertexFormat(t);
            case TEXTURE_VERTEX:
                return writeTextureVertexFormat(t);
            case VERTEX_NORMAL:
                return writeVertexNormalFormat(t);
            case ALL:
                return writeAllFormat(t);
            default:
                return null;
        }
    }

    private String writeVertexFormat(final Triplet t) {
        if (!t.format().equals(Triplet.Format.VERTEX)) {
            return null;
        }

        tripletBuilder.append(vertexIndices.get(t.vertex()));
        return tripletBuilder.toString();
    }

    private String writeTextureVertexFormat(final Triplet t) {
        if (!t.format().equals(Triplet.Format.TEXTURE_VERTEX)) {
            return null;
        }

        tripletBuilder.append(vertexIndices.get(t.vertex()));
        tripletBuilder.append('/');
        tripletBuilder.append(textureVertexIndices.get(t.textureVertex()));
        return tripletBuilder.toString();
    }

    private String writeVertexNormalFormat(final Triplet t) {
        if (!t.format().equals(Triplet.Format.VERTEX_NORMAL)) {
            return null;
        }

        tripletBuilder.append(vertexIndices.get(t.vertex()));
        tripletBuilder.append('/');
        tripletBuilder.append('/');
        tripletBuilder.append(vertexNormalIndices.get(t.vertexNormal()));
        return tripletBuilder.toString();

    }

    private String writeAllFormat(final Triplet t) {
        if (!t.format().equals(Triplet.Format.ALL)) {
            return null;
        }

        tripletBuilder.append(vertexIndices.get(t.vertex()));
        tripletBuilder.append('/');
        tripletBuilder.append(textureVertexIndices.get(t.textureVertex()));
        tripletBuilder.append('/');
        tripletBuilder.append(vertexNormalIndices.get(t.vertexNormal()));
        return tripletBuilder.toString();

    }
}
