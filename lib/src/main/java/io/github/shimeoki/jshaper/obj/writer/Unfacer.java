package io.github.shimeoki.jshaper.obj.writer;

import java.util.List;
import java.util.Objects;

import io.github.shimeoki.jshaper.ShaperError;
import io.github.shimeoki.jshaper.obj.Face;
import io.github.shimeoki.jshaper.obj.Token;
import io.github.shimeoki.jshaper.obj.Triplet;

public final class Unfacer {

    private final StringBuilder builder = new StringBuilder();

    private final Untripleter untripleter;

    private List<Triplet> triplets;
    private Triplet.Format format;

    public Unfacer(final Untripleter u) {
        untripleter = Objects.requireNonNull(u);
    }

    public String parse(final Face f) throws ShaperError {
        triplets = Objects.requireNonNull(f).triplets();
        if (triplets.size() < 3) {
            throw new ShaperError(ShaperError.Type.PARSE,
                    "less than 3 triplets in a face");
        }

        builder.setLength(0);
        format = triplets.get(0).format();

        builder.append(Token.FACE);
        builder.append(' ');

        for (final Triplet t : triplets) {
            if (!t.format().equals(format)) {
                throw new ShaperError(ShaperError.Type.PARSE,
                        "triplets of different formats in one face");
            }

            builder.append(untripleter.parse(t));
            builder.append(' ');
        }

        builder.append('\n');
        return builder.toString();
    }
}
