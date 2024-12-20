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

import io.github.shimeoki.jshaper.obj.data.ObjElements;
import io.github.shimeoki.jshaper.obj.data.ObjFile;
import io.github.shimeoki.jshaper.obj.data.ObjGroupingData;
import io.github.shimeoki.jshaper.obj.data.ObjVertexData;
import io.github.shimeoki.jshaper.obj.geom.ObjFace;
import io.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertex;
import io.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;
import io.github.shimeoki.jshaper.obj.reader.ObjFacer;
import io.github.shimeoki.jshaper.obj.reader.ObjGroupNamer;
import io.github.shimeoki.jshaper.obj.reader.ObjReaderException;
import io.github.shimeoki.jshaper.obj.reader.ObjReaderExceptionType;
import io.github.shimeoki.jshaper.obj.reader.ObjToken;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenizer;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenizerMode;
import io.github.shimeoki.jshaper.obj.reader.ObjTokens;
import io.github.shimeoki.jshaper.obj.reader.ObjTripleter;
import io.github.shimeoki.jshaper.obj.reader.ObjVertexer;

public final class ObjModelReader implements ObjReader {

    private static final ObjTokenizerMode TOKENIZER_MODE = ObjTokenizerMode.WHITELIST_ONLY;
    private static final Set<ObjToken> TOKENIZER_BLACKLIST = new HashSet<>();
    private static final Set<ObjToken> TOKENIZER_WHITELIST = ObjTokenizer.tokenSet(
            ObjToken.VERTEX,
            ObjToken.TEXTURE_VERTEX,
            ObjToken.VERTEX_NORMAL,
            ObjToken.FACE,
            ObjToken.GROUP_NAME);

    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;
    private List<ObjFace> faces;

    private ObjTokenizer tokenizer;
    private ObjTripleter tripleter;
    private ObjFacer facer;
    private ObjGroupNamer groupNamer;

    private BufferedReader reader;
    private int row;
    private String line;

    public ObjModelReader() {
    }

    private void open(final File f) throws ObjReaderException {
        cache();
        openReader(f);
    }

    private void close() throws ObjReaderException {
        uncache();
        closeReader();
    }

    private void error(final ObjReaderExceptionType type, final String msg) throws ObjReaderException {
        final int row = this.row + 1;

        close();

        throw new ObjReaderException(
                type, String.format("%s at row %d", msg, row));
    }

    private void openReader(final File f) throws ObjReaderException {
        final Path p = f.toPath();

        try {
            reader = Files.newBufferedReader(p);
        } catch (final IOException e) {
            error(ObjReaderExceptionType.IO, "error while opening the file");
        }
    }

    private void closeReader() throws ObjReaderException {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while closing the file");
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

        tokenizer = new ObjTokenizer(
                TOKENIZER_MODE, TOKENIZER_WHITELIST, TOKENIZER_BLACKLIST);
        tripleter = new ObjTripleter(vertices, textureVertices, vertexNormals);
        facer = new ObjFacer(tripleter);
        groupNamer = new ObjGroupNamer();
    }

    private void uncache() {
        vertices = null;
        textureVertices = null;
        vertexNormals = null;
        faces = null;

        tokenizer = null;
        tripleter = null;
        facer = null;
        groupNamer = null;
    }

    private void readLine() throws ObjReaderException {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while reading the file");
        }
    }

    private void parse() throws ObjReaderException {
        final ObjTokens tokens = tokenizer.tokens();

        ObjToken lineToken;
        for (readLine(); line != null; readLine(), row++) {
            tokenizer.parseLine(line);

            lineToken = tokens.lineToken();
            if (lineToken == null) {
                continue;
            }

            switch (lineToken) {
                case VERTEX:
                    vertices.add(ObjVertexer.parseVertex(tokens));
                    break;
                case TEXTURE_VERTEX:
                    textureVertices.add(ObjVertexer.parseTextureVertex(tokens));
                    break;
                case VERTEX_NORMAL:
                    vertexNormals.add(ObjVertexer.parseVertexNormal(tokens));
                    break;
                case FACE:
                    faces.add(facer.parse(tokens, groupNamer.current()));
                    break;
                case GROUP_NAME:
                    groupNamer.parse(tokens);
                    break;
                default:
                    // skip unsupported tokens
                    continue;
            }
        }
    }

    private ObjFile obj() {
        final ObjVertexData vertexData = new ObjVertexData(
                vertices,
                textureVertices,
                vertexNormals,
                new ArrayList<>());

        final ObjElements elements = new ObjElements(faces);
        final ObjGroupingData groupingData = new ObjGroupingData(groupNamer.all());

        return new ObjFile(vertexData, elements, groupingData);
    }

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        Objects.requireNonNull(f);

        if (!f.canRead()) {
            error(ObjReaderExceptionType.IO, "file is not readable");
        }

        open(f);

        parse();
        final ObjFile obj = obj();

        close();

        return obj;
    }
}
