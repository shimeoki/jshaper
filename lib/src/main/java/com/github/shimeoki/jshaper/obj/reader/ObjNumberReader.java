package com.github.shimeoki.jshaper.obj.reader;

public final class ObjNumberReader {

    public static float parseFloat(final String s) throws ObjReaderException {
        try {
            return Float.parseFloat(s);
        } catch (final NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid float format");
        }
    }

    public static int parseInt(final String s) throws ObjReaderException {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            throw new ObjReaderException(ObjReaderExceptionType.PARSE, "invalid int format");
        }
    }
}
