package io.github.shimeoki.jshaper.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.ObjFile;
import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.reader.Facer;
import io.github.shimeoki.jshaper.obj.reader.Grouper;
import io.github.shimeoki.jshaper.obj.reader.Tokenizer;
import io.github.shimeoki.jshaper.obj.reader.Tripleter;
import io.github.shimeoki.jshaper.obj.reader.Vertexer;

public final class ModelReader implements Reader {

    private static final Tokenizer.Mode TOKENIZER_MODE = Tokenizer.Mode.WHITELIST_ONLY;
    private static final Set<Token.Type> TOKENIZER_BLACKLIST = new HashSet<>();
    private static final Set<Token.Type> TOKENIZER_WHITELIST = Tokenizer.typeSet(
            Token.Type.VERTEX,
            Token.Type.TEXTURE_VERTEX,
            Token.Type.VERTEX_NORMAL,
            Token.Type.FACE,
            Token.Type.GROUP);

    private List<Vertex> vertices;
    private List<TextureVertex> textureVertices;
    private List<VertexNormal> vertexNormals;
    private List<Face> faces;

    private Tokenizer tokenizer;
    private Tripleter tripleter;
    private Facer facer;
    private Grouper grouper;

    private BufferedReader reader;
    private int row;
    private String line;

    public ModelReader() {
    }

    private void open(final File f) throws ShaperError {
        cache();
        openReader(f);
    }

    private void close() throws ShaperError {
        uncache();
        closeReader();
    }

    private void error(final ShaperError.Type t, final String msg) throws ShaperError {
        final int row = this.row + 1;

        close();

        throw new ShaperError(
                t, String.format("%s at row %d", msg, row));
    }

    private void openReader(final File f) throws ShaperError {
        final Path p = f.toPath();

        try {
            reader = Files.newBufferedReader(p);
        } catch (final IOException e) {
            error(ShaperError.Type.IO, "error while opening the file");
        }
    }

    private void closeReader() throws ShaperError {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            error(ShaperError.Type.IO, "error while closing the file");
        } finally {
            // can be unsafe, but otherwise the recursion can occur
            reader = null;
            row = 0;
            line = null;
        }
    }

    private void cache() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        vertexNormals = new ArrayList<>();
        faces = new ArrayList<>();

        tokenizer = new Tokenizer(
                TOKENIZER_MODE, TOKENIZER_WHITELIST, TOKENIZER_BLACKLIST);
        tripleter = new Tripleter(vertices, textureVertices, vertexNormals);
        facer = new Facer(tripleter);
        grouper = new Grouper();
    }

    private void uncache() {
        vertices = null;
        textureVertices = null;
        vertexNormals = null;
        faces = null;

        tokenizer = null;
        tripleter = null;
        facer = null;
        grouper = null;
    }

    private void readLine() throws ShaperError {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            error(ShaperError.Type.IO, "error while reading the file");
        }
    }

    private void parse() throws ShaperError {
        final Tokens tokens = tokenizer.tokens();

        Token lineToken;
        for (readLine(); line != null; readLine(), row++) {
            tokenizer.parseLine(line);

            lineToken = tokens.lineToken();
            if (lineToken == null) {
                continue;
            }

            switch (lineToken.type()) {
                case VERTEX:
                    vertices.add(Vertexer.parseVertex(tokens));
                    break;
                case TEXTURE_VERTEX:
                    textureVertices.add(Vertexer.parseTextureVertex(tokens));
                    break;
                case VERTEX_NORMAL:
                    vertexNormals.add(Vertexer.parseVertexNormal(tokens));
                    break;
                case FACE:
                    faces.add(facer.parse(tokens, grouper.current()));
                    break;
                case GROUP:
                    grouper.parse(tokens);
                    break;
                default:
                    // skip unsupported tokens
                    continue;
            }
        }
    }

    private ObjFile obj() {
        final VertexData vertexData = new VertexData(
                vertices,
                textureVertices,
                vertexNormals,
                new ArrayList<>());

        final Elements elements = new Elements(faces);
        final GroupingData groupingData = new GroupingData(grouper.all());

        return new ObjFile(vertexData, elements, groupingData);
    }

    @Override
    public ObjFile read(final File f) throws ShaperError {
        Objects.requireNonNull(f);

        if (!f.canRead()) {
            error(ShaperError.Type.IO, "file is not readable");
        }

        open(f);

        parse();
        final ObjFile obj = obj();

        close();

        return obj;
    }
}
