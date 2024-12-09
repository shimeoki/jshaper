package io.github.shimeoki.jshaper.obj.reader;

import java.util.Objects;

public final class ObjNumberer {

    public static float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(Objects.requireNonNull(s));
        } catch (final NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE,
                    "invalid float format");
        }
    }

    public static int parseInt(final String s) throws ObjReaderException {
        try {
            return Integer.parseInt(Objects.requireNonNull(s));
        } catch (final NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE,
                    "invalid int format");
        }
    }
}
