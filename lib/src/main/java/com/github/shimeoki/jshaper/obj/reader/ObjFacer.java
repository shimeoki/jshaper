package com.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.github.shimeoki.jshaper.obj.data.ObjGroupName;
import com.github.shimeoki.jshaper.obj.data.ObjTriplet;
import com.github.shimeoki.jshaper.obj.data.ObjTripletFormat;
import com.github.shimeoki.jshaper.obj.geom.ObjFace;

public final class ObjFacer {

    private final ObjTripleter tripleter;
    private final List<ObjTriplet> triplets = new ArrayList<>();

    public ObjFacer(final ObjTripleter tripleter) {
        this.tripleter = Objects.requireNonNull(tripleter);
    }

    public ObjFace parse(
            final List<ObjParsedString> tokens,
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

        ObjTripletFormat format = null;
        ObjTripletFormat fmt;
        ObjTriplet t;
        ObjParsedString parsed;

        for (int i = 1; i < len; i++) {
            parsed = tokens.get(i);
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

        return new ObjFace(new ArrayList<>(triplets), groupNames);
    }
}
