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
import io.github.shimeoki.jshaper.obj.reader.ObjFacer;
import io.github.shimeoki.jshaper.obj.reader.ObjGroupNamer;
import io.github.shimeoki.jshaper.obj.reader.ObjReaderException;
import io.github.shimeoki.jshaper.obj.reader.ObjReaderExceptionType;
import io.github.shimeoki.jshaper.obj.reader.ObjToken;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenized;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenizer;
import io.github.shimeoki.jshaper.obj.reader.ObjTokenizerMode;
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

    // input
    private BufferedReader reader;

    // parse vertices
    private ObjVertexer vertexer;

    // parse lines
    private ObjTokenizer tokenizer;

    // parse faces
    private ObjTripleter tripleter;
    private ObjFacer facer;

    // parse groups
    private ObjGroupNamer groupNamer;

    // parse
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
        }
    }

    private void cache() {
        vertexer = new ObjVertexer();

        tokenizer = new ObjTokenizer(TOKENIZER_MODE, TOKENIZER_WHITELIST, TOKENIZER_BLACKLIST);

        tripleter = new ObjTripleter(vertexer);

        facer = new ObjFacer(tripleter);

        groupNamer = new ObjGroupNamer();
    }

    private void uncache() {
        vertexer = null;

        tokenizer = null;

        tripleter = null;
        facer = null;

        groupNamer = null;

        row = 0;
        line = null;
    }

    private void readLine() throws ObjReaderException {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while reading the file");
        }
    }

    private ObjFile parse() throws ObjReaderException {
        final List<ObjTokenized> tokens = tokenizer.tokens();

        ObjToken lineToken;
        for (readLine(); line != null; readLine(), row++) {
            tokenizer.parseLine(line);

            lineToken = tokenizer.lineToken();
            if (lineToken == null) {
                continue;
            }

            switch (lineToken) {
                case VERTEX:
                    vertexer.parseVertex(tokens);
                    break;
                case TEXTURE_VERTEX:
                    vertexer.parseTextureVertex(tokens);
                    break;
                case VERTEX_NORMAL:
                    vertexer.parseVertexNormal(tokens);
                    break;
                case FACE:
                    facer.parse(tokens, groupNamer.current());
                    break;
                case GROUP_NAME:
                    groupNamer.parse(tokens);
                    break;
                default:
                    // skip unsupported tokens
                    continue;
            }
        }

        final ObjVertexData vertexData = new ObjVertexData(
                vertexer.vertices(),
                vertexer.textureVertices(),
                vertexer.vertexNormals(),
                new ArrayList<>());

        final ObjElements elements = new ObjElements(facer.faces());
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
        final ObjFile result = parse();
        close();

        return result;
    }
}
