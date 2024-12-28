package io.github.shimeoki.jshaper;

import java.util.Objects;

public final class Numberer {

    public static float parseFloat(final String s) throws ShaperError {
        try {
            return Float.parseFloat(Objects.requireNonNull(s));
        } catch (final NumberFormatException e) {
            throw new ShaperError(ShaperError.Type.PARSE,
                    "invalid float format");
        }
    }

    public static int parseInt(final String s) throws ShaperError {
        try {
            return Integer.parseInt(Objects.requireNonNull(s));
        } catch (final NumberFormatException e) {
            throw new ShaperError(ShaperError.Type.PARSE,
                    "invalid int format");
        }
    }
}
