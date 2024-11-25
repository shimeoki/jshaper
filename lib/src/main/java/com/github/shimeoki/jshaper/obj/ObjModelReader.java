package com.github.shimeoki.jshaper.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.shimeoki.jshaper.obj.data.ObjElements;
import com.github.shimeoki.jshaper.obj.data.ObjFile;
import com.github.shimeoki.jshaper.obj.data.ObjGroupName;
import com.github.shimeoki.jshaper.obj.data.ObjGroupingData;
import com.github.shimeoki.jshaper.obj.data.ObjTriplet;
import com.github.shimeoki.jshaper.obj.data.ObjTripletFormat;
import com.github.shimeoki.jshaper.obj.data.ObjVertexData;
import com.github.shimeoki.jshaper.obj.geom.ObjFace;
import com.github.shimeoki.jshaper.obj.geom.ObjTextureVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertex;
import com.github.shimeoki.jshaper.obj.geom.ObjVertexNormal;
import com.github.shimeoki.jshaper.obj.reader.ObjReaderException;
import com.github.shimeoki.jshaper.obj.reader.ObjReaderExceptionType;
import com.github.shimeoki.jshaper.obj.reader.ObjToken;
import com.github.shimeoki.jshaper.obj.reader.ObjTokenizer;
import com.github.shimeoki.jshaper.obj.reader.ObjTripleter;
import com.github.shimeoki.jshaper.obj.reader.ObjVertexer;

public final class ObjModelReader implements ObjReader {

    // parse results
    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;
    private List<ObjFace> faces;
    private Set<ObjGroupName> groupNames;

    // input
    private BufferedReader reader;

    // parse lines
    private StringBuilder stringer;
    private List<String> strings;

    // parse faces
    private ObjTripleter tripleter;
    private List<ObjTriplet> triplets;
    private ObjTripletFormat format;

    // parse groups
    private Set<String> currentGroupNames;
    private Map<String, ObjGroupName> groupNameMap;

    // parse
    private int row, col;
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
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        vertexNormals = new ArrayList<>();
        faces = new ArrayList<>();
        groupNames = new HashSet<>();

        stringer = new StringBuilder();
        strings = new ArrayList<>();

        tripleter = new ObjTripleter(vertices, textureVertices, vertexNormals);
        triplets = new ArrayList<>();

        currentGroupNames = new HashSet<>();
        groupNameMap = new HashMap<>();
    }

    private void uncache() {
        vertices = null;
        textureVertices = null;
        vertexNormals = null;
        faces = null;
        groupNames = null;

        stringer = null;
        strings = null;

        tripleter = null;
        triplets = null;
        format = null;

        currentGroupNames = null;
        groupNameMap = null;

        row = 0;
        col = 0;
        line = null;
    }

    private void readLine() throws ObjReaderException {
        try {
            line = reader.readLine();
        } catch (IOException e) {
            error(ObjReaderExceptionType.IO, "error while reading the file");
        }
    }

    private void parseFace() throws ObjReaderException {
        if (strings.size() < 3) {
            error(ObjReaderExceptionType.PARSE, "less than three triplets in one face");
        }

        triplets.clear();
        format = null;

        ObjTriplet t;
        ObjTripletFormat fmt;

        for (final String s : strings) {
            t = tripleter.parse(s);
            fmt = t.format();

            if (format == null) {
                format = fmt;
            }

            if (!format.equals(fmt)) {
                error(ObjReaderExceptionType.PARSE, "multiple triplet formats in one face");
            }

            triplets.add(t);
        }

        final ObjFace f = new ObjFace(new ArrayList<>(triplets), groupNames());
        faces.add(f);
    }

    private Set<ObjGroupName> groupNames() {
        final Set<ObjGroupName> names = new HashSet<>();

        if (currentGroupNames.isEmpty()) {
            names.add(new ObjGroupName("default"));
            return names;
        }

        for (final String s : currentGroupNames) {
            names.add(groupNameMap.get(s));
        }

        return names;
    }

    private void parseGroupName() throws ObjReaderException {
        if (strings.size() < 1) {
            error(ObjReaderExceptionType.PARSE, "no names in group name statement");
        }

        currentGroupNames.clear();

        ObjGroupName n;
        for (final String s : strings) {
            currentGroupNames.add(s);
            n = groupNameMap.getOrDefault(s, new ObjGroupName(s));

            groupNames.add(n);

            if (!groupNameMap.containsKey(s)) {
                groupNameMap.put(s, n);
            }
        }
    }

    private void parseLine() throws ObjReaderException {
        stringer.setLength(0);
        strings.clear();
        col = 0;

        final ObjToken lineToken = parseLineToken();
        if (lineToken == null) {
            return;
        }

        switch (lineToken) {
            case VERTEX, TEXTURE_VERTEX, VERTEX_NORMAL, FACE, GROUP_NAME:
                break;
            default:
                return;
        }

        parseStrings();

        parseByToken(lineToken);
    }

    private ObjToken parseLineToken() throws ObjReaderException {
        final int len = line.length();

        char c;
        for (; col < len; col++) {
            c = line.charAt(col);

            // the line token cannot be prefixed with spaces
            if (c == ' ') {
                break;
            }

            stringer.append(c);
        }

        return ObjTokenizer.parse(flushStringer());
    }

    private void parseStrings() throws ObjReaderException {
        final int len = line.length();
        if (len == 0) {
            return;
        }

        char c;
        for (; col <= len; col++) {
            if (col == len) {
                c = ' '; // artificially add a space at the end
            } else {
                c = line.charAt(col);
            }

            if (String.valueOf(c).equals(ObjToken.COMMENT.toString())) {
                strings.add(flushStringer());
                break;
            }

            if (c != ' ') {
                stringer.append(c);
                continue;
            }

            if (stringer.isEmpty()) {
                continue;
            }

            strings.add(flushStringer());
        }
    }

    private String flushStringer() {
        final String s = stringer.toString();
        stringer.setLength(0);
        return s;
    }

    private void parseByToken(final ObjToken token) throws ObjReaderException {
        switch (token) {
            case VERTEX:
                vertices.add(ObjVertexer.parseVertex(strings));
                break;
            case TEXTURE_VERTEX:
                textureVertices.add(ObjVertexer.parseTextureVertex(strings));
                break;
            case VERTEX_NORMAL:
                vertexNormals.add(ObjVertexer.parseVertexNormal(strings));
                break;
            case FACE:
                parseFace();
                break;
            case GROUP_NAME:
                parseGroupName();
                break;
            default:
                error(ObjReaderExceptionType.PARSE, "unsupported token");
        }
    }

    private ObjFile parse() throws ObjReaderException {
        for (readLine(); line != null; readLine(), row++) {
            parseLine();
        }

        final ObjVertexData vertexData = new ObjVertexData(
                vertices,
                textureVertices,
                vertexNormals,
                new ArrayList<>());

        final ObjElements elements = new ObjElements(faces);
        final ObjGroupingData groupingData = new ObjGroupingData(groupNames);

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
