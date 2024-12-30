package io.github.shimeoki.jshaper.obj.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.Tokens;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.Group;
import io.github.shimeoki.jshaper.obj.Triplet;
import io.github.shimeoki.jshaper.obj.Face;

public final class Facer {

    private final List<Triplet> triplets = new ArrayList<>();
    private final Tripleter tripleter;

    public Facer(final Tripleter tripleter) {
        this.tripleter = Objects.requireNonNull(tripleter);
    }

    public Face parse(final Tokens tokens, final Set<Group> groups)
            throws ShaperError {

        if (!tokens.lineTokenTypeIs(Token.Type.FACE)) {
            throw new ShaperError(ShaperError.Type.PARSE, "invalid face format");
        }

        final int len = tokens.size();
        if (len < 4) {
            throw new ShaperError(ShaperError.Type.PARSE, "less than three triplets in one face");
        }

        triplets.clear();

        Triplet.Format tripletFormat, faceFormat = null;
        Triplet triplet;

        for (int i = 1; i < len; i++) {
            triplet = tripleter.parse(tokens.get(i).text());
            tripletFormat = triplet.format();

            if (faceFormat == null) {
                faceFormat = tripletFormat;
            }

            if (!faceFormat.equals(tripletFormat)) {
                throw new ShaperError(ShaperError.Type.PARSE, "multiple triplet formats in one face");
            }

            triplets.add(triplet);
        }

        return new Face(new ArrayList<>(triplets), groups);
    }
}
