package com.github.shimeoki.jshaper.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

public final class ObjModelReader implements ObjReader {

    private List<ObjVertex> vertices;
    private List<ObjTextureVertex> textureVertices;
    private List<ObjVertexNormal> vertexNormals;
    private List<ObjFace> faces;
    private Set<ObjGroupName> groupNames;

    private BufferedReader reader;

    private StringBuilder stringer;
    private List<String> strings;

    private StringBuilder tripleter;
    private List<ObjTriplet> triplets;
    private String triplet;
    private ObjTripletFormat format;
    private int[] indices;

    private String groupName;
    private Map<String, ObjGroupName> groupNameMap;

    private int row, col;
    private String line;

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
            reader.close();
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

        tripleter = new StringBuilder();
        triplets = new ArrayList<>();

        groupNameMap = new HashMap<>();

        indices = new int[3];
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
        triplet = null;
        format = null;
        indices = null;

        groupName = null;
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

    private float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(s);
        } catch (final NumberFormatException e) {
            error(ObjReaderExceptionType.PARSE, "invalid float format");
        }

        // shouldn't reach.
        // only for the compiler check
        return 0;
    }

    private int parseInt(final String s) throws ObjReaderException {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            error(ObjReaderExceptionType.PARSE, "invalid int format");
        }

        // shouldn't reach.
        // only for the compiler check
        return 0;
    }

    private void parseVertex() throws ObjReaderException {
        final int len = strings.size();

        if (len < 3 || len > 4) {
            error(ObjReaderExceptionType.PARSE, "invalid format for vertex");
        }

        final float x = parseFloat(strings.get(0));
        final float y = parseFloat(strings.get(1));
        final float z = parseFloat(strings.get(2));

        final Float w;
        if (len == 4) {
            w = parseFloat(strings.get(3));
        } else {
            w = null;
        }

        final ObjVertex v = new ObjVertex(x, y, z, w);
        vertices.add(v);
    }

    private void parseTextureVertex() throws ObjReaderException {
        final int len = strings.size();

        if (len < 1 || len > 3) {
            error(ObjReaderExceptionType.PARSE, "invalid format for texture vertex");
        }

        final float u = parseFloat(strings.get(0));

        final Float v;
        if (len >= 2) {
            v = parseFloat(strings.get(1));
        } else {
            v = null;
        }

        final Float w;
        if (len == 3) {
            w = parseFloat(strings.get(2));
        } else {
            w = null;
        }

        final ObjTextureVertex vt = new ObjTextureVertex(u, v, w);
        textureVertices.add(vt);
    }

    private void parseVertexNormal() throws ObjReaderException {
        if (strings.size() != 3) {
            error(ObjReaderExceptionType.PARSE, "invalid format for vertex normal");
        }

        final float i = parseFloat(strings.get(0));
        final float j = parseFloat(strings.get(1));
        final float k = parseFloat(strings.get(2));

        final ObjVertexNormal vn = new ObjVertexNormal(i, j, k);
        vertexNormals.add(vn);
    }

    private void parseFace() throws ObjReaderException {
        if (strings.size() < 3) {
            error(ObjReaderExceptionType.PARSE, "less than three triplets in one face");
        }

        triplets.clear();

        ObjTriplet t;
        for (final String s : strings) {
            triplet = s;
            t = parseTriplet();
            triplets.add(t);
        }

        final ObjFace f = new ObjFace(new ArrayList<>(triplets), new ObjGroupName("default"));
        faces.add(f);
    }

    private ObjTriplet parseTriplet() throws ObjReaderException {
        final int len = triplet.length();
        tripleter.setLength(0);

        Arrays.fill(indices, 0);
        int index = -1;

        char c;
        for (int i = 0; i < len; i++) {
            c = line.charAt(i);

            if (c == ' ') {
                error(ObjReaderExceptionType.PARSE, "found a space in a triplet");
            }

            if (c != '/') {
                tripleter.append(c);
                continue;
            }

            index++;
            if (index > 2) {
                error(ObjReaderExceptionType.PARSE, "found more than three indices in a triplet");
            }

            if (tripleter.isEmpty()) {
                continue;
            }

            indices[index] = parseInt(tripleter.toString());
            tripleter.setLength(0);
        }

        return parseTripletByIndices();
    }

    private ObjVertex parseVertexByIndex() throws ObjReaderException {
        int i = indices[0];
        if (i == 0) {
            error(ObjReaderExceptionType.PARSE, "no vertex index in the triplet");
        }

        final int len = vertices.size();

        if (i < 0) {
            i += len;
        }

        if (i < 0 || i >= len) {
            error(ObjReaderExceptionType.PARSE, "invalid vertex index");
        }

        return vertices.get(i);
    }

    private ObjTextureVertex parseTextureVertexByIndex() throws ObjReaderException {
        int i = indices[1];
        if (i == 0) {
            return null;
        }

        final int len = textureVertices.size();

        if (i < 0) {
            i += len;
        }

        if (i < 0 || i >= len) {
            error(ObjReaderExceptionType.PARSE, "invalid texture vertex index");
        }

        return textureVertices.get(i);
    }

    private ObjVertexNormal parseVertexNormalByIndex() throws ObjReaderException {
        int i = indices[2];
        if (i == 0) {
            return null;
        }

        final int len = textureVertices.size();

        if (i < 0) {
            i += len;
        }

        if (i < 0 || i >= len) {
            error(ObjReaderExceptionType.PARSE, "invalid vertex normal index");
        }

        return vertexNormals.get(i);
    }

    private ObjTriplet parseTripletByIndices() throws ObjReaderException {
        final ObjVertex v = parseVertexByIndex();
        final ObjTextureVertex vt = parseTextureVertexByIndex();
        final ObjVertexNormal vn = parseVertexNormalByIndex();

        final ObjTriplet t = new ObjTriplet(v, vt, vn);
        final ObjTripletFormat fmt = t.format();

        if (format == null) {
            format = fmt;
        }

        if (!format.equals(fmt)) {
            error(ObjReaderExceptionType.PARSE, "multiple triplet formats in one face");
        }

        return t;
    }

    private void parseLine() throws ObjReaderException {
        stringer.setLength(0);
        strings.clear();
        col = 0;

        final ObjToken lineToken = parseLineToken();
        if (lineToken.equals(ObjToken.COMMENT)) {
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

        final ObjToken token = ObjTokenizer.parse(flushStringer());
        if (token == null) {
            error(ObjReaderExceptionType.PARSE, "unknown token at the start of the line");
        }

        return token;
    }

    private void parseStrings() throws ObjReaderException {
        final int len = line.length();
        if (len == 0) {
            return;
        }

        ObjToken token;
        char c;
        String s;

        for (; col <= len; col++) {
            if (col == len) {
                c = ' '; // artificially add a space at the end
            } else {
                c = line.charAt(col);
            }

            if (c != ' ') {
                stringer.append(c);
                continue;
            }

            if (stringer.isEmpty()) {
                continue;
            }

            s = flushStringer();
            token = ObjTokenizer.parse(s);

            if (token == null) {
                strings.add(s);
                continue;
            }

            if (token.equals(ObjToken.COMMENT)) {
                break;
            } else {
                error(ObjReaderExceptionType.PARSE,
                        "found a token not at the start of the line");
            }
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
                parseVertex();
                break;
            case TEXTURE_VERTEX:
                parseTextureVertex();
                break;
            case VERTEX_NORMAL:
                parseVertexNormal();
                break;
            case FACE:
                parseFace();
                break;
            default:
                error(ObjReaderExceptionType.PARSE, "unsupported token");
        }
    }

    private ObjFile parse() throws ObjReaderException {
        for (readLine(); line != null; readLine(), row++) {
            parseLine();
        }

        final ObjVertexData data = new ObjVertexData(
                vertices,
                textureVertices,
                vertexNormals,
                new ArrayList<>());

        final ObjElements elements = new ObjElements(faces);

        return new ObjFile(data, elements, new ObjGroupingData(new HashSet<>()));
    }

    @Override
    public ObjFile read(final File f) throws ObjReaderException {
        if (!f.canRead()) {
            error(ObjReaderExceptionType.IO, "file is not readable");
        }

        open(f);
        final ObjFile result = parse();
        close();

        return result;
    }
}
