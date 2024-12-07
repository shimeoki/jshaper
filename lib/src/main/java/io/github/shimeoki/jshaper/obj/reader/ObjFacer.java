package io.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.obj.data.ObjGroupName;
import io.github.shimeoki.jshaper.obj.data.ObjTriplet;
import io.github.shimeoki.jshaper.obj.data.ObjTripletFormat;
import io.github.shimeoki.jshaper.obj.geom.ObjFace;

public final class ObjFacer {

    private final List<ObjFace> faces = new ArrayList<>();

    private final ObjTripleter tripleter;
    private final List<ObjTriplet> triplets = new ArrayList<>();

    public ObjFacer(final ObjTripleter tripleter) {
        this.tripleter = Objects.requireNonNull(tripleter);
    }

    public void parse(
            final List<ObjTokenized> tokens,
            final Set<ObjGroupName> groupNames)
            throws ObjReaderException {

        if (!tokens.getFirst().token().is(ObjToken.FACE)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid face format");
        }

        final int len = tokens.size();
        if (len < 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "less than three triplets in one face");
        }

        triplets.clear();

        ObjTripletFormat tripletFormat, faceFormat = null;
        ObjTriplet triplet;

        for (int i = 1; i < len; i++) {
            triplet = tripleter.parse(tokens.get(i).value());
            tripletFormat = triplet.format();

            if (faceFormat == null) {
                faceFormat = tripletFormat;
            }

            if (!faceFormat.equals(tripletFormat)) {
                throw new ObjReaderException(ObjReaderExceptionType.PARSE, "multiple triplet formats in one face");
            }

            triplets.add(triplet);
        }

        faces.add(new ObjFace(new ArrayList<>(triplets), groupNames));
    }

    public List<ObjFace> faces() {
        return faces;
    }
}
