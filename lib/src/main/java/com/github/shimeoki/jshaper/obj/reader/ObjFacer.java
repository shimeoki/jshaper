package com.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.github.shimeoki.jshaper.obj.data.ObjTriplet;
import com.github.shimeoki.jshaper.obj.data.ObjTripletFormat;
import com.github.shimeoki.jshaper.obj.geom.ObjFace;

public final class ObjFacer {

    private final ObjTripleter tripleter;
    private final List<ObjTriplet> triplets = new ArrayList<>();

    private ObjTripletFormat format;

    public ObjFacer(final ObjTripleter tripleter) {
        this.tripleter = Objects.requireNonNull(tripleter);
    }

    public ObjFace parse(List<ObjParsedString> tokens) throws ObjReaderException {
        if (!tokens.getFirst().token().is(ObjToken.FACE)) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid face format");
        }

        if (tokens.size() < 4) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "less than three triplets in one face");
        }

        triplets.clear();
        format = null;

        ObjTriplet t;
        ObjTripletFormat fmt;

        for (final ObjParsedString parsed : tokens) {
            t = tripleter.parse(parsed.value());
            fmt = t.format();

            if (format == null) {
                format = fmt;
            }

            if (!format.equals(fmt)) {
                throw new ObjReaderException(ObjReaderExceptionType.PARSE, "multiple triplet formats in one face");
            }

            triplets.add(t);
        }

        // TODO group names
        return new ObjFace(new ArrayList<>(triplets), new HashSet<>());
    }
}
