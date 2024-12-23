package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjNumbererTest {

    @Test
    public void parseNullFloat() {
        try {
            ObjNumberer.parseFloat(null);
        } catch (final NullPointerException e) {
            return;
        } catch (final ShaperError e) {
            fail("caught ObjReaderException");
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseNullInt() {
        try {
            ObjNumberer.parseInt(null);
        } catch (final NullPointerException e) {
            return;
        } catch (final ShaperError e) {
            fail("caught ObjReaderException");
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseValidFloat() {
        try {
            assertEquals(0.0f, ObjNumberer.parseFloat("0.0"));
            assertEquals(1.0f, ObjNumberer.parseFloat("1.0"));
            assertEquals(0.123f, ObjNumberer.parseFloat("0.123"));
            assertEquals(100.456f, ObjNumberer.parseFloat("100.456"));
            assertEquals(50f, ObjNumberer.parseFloat("50"));
            assertEquals(-84f, ObjNumberer.parseFloat("-84"));
            assertEquals(-0.789f, ObjNumberer.parseFloat("-0.789"));
            assertEquals(-0.0f, ObjNumberer.parseFloat("-0.0"));
        } catch (final ShaperError e) {
            fail("caught ObjReaderException");
        }
    }

    @Test
    public void parseValidInt() {
        try {
            assertEquals(0, ObjNumberer.parseInt("0"));
            assertEquals(1, ObjNumberer.parseInt("1"));
            assertEquals(123, ObjNumberer.parseInt("123"));
            assertEquals(456, ObjNumberer.parseInt("456"));
            assertEquals(-84, ObjNumberer.parseInt("-84"));
            assertEquals(-789, ObjNumberer.parseInt("-789"));
            assertEquals(0, ObjNumberer.parseInt("-0"));
        } catch (final ShaperError e) {
            fail("caught ObjReaderException");
        }
    }

    @Test
    public void parseBlankFloat() {
        try {
            ObjNumberer.parseFloat("");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseBlankInt() {
        try {
            ObjNumberer.parseInt("");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseSpaceFloat() {
        try {
            ObjNumberer.parseFloat(" ");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseSpaceInt() {
        try {
            ObjNumberer.parseInt(" ");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseSpacedAroundFloat() {
        int count = 0;

        try {
            ObjNumberer.parseFloat("0.123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat(" 0.123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat(" 0.123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("   0.123   ");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(0, count); // java moment? idk why
    }

    @Test
    public void parseSpacedAroundInt() {
        int count = 0;

        try {
            ObjNumberer.parseInt("123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt(" 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt(" 123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt("   123   ");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    public void parseSpacedInsideFloat() {
        int count = 0;

        try {
            ObjNumberer.parseFloat("0. 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("0 .123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("0 . 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("0  .  123");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    public void parseCommaFloat() {
        try {
            ObjNumberer.parseFloat("0,123");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseStringAsFloat() {
        try {
            ObjNumberer.parseFloat("value");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseStringAsInt() {
        try {
            ObjNumberer.parseInt("value");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ObjReaderException caught");
    }

    @Test
    public void parseSpecialCharsAsFloat() {
        int count = 0;

        try {
            ObjNumberer.parseFloat("!");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("+");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat(":");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat(";");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("?");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseFloat("-");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(6, count);
    }

    @Test
    public void parseSpecialCharsAsInt() {
        int count = 0;

        try {
            ObjNumberer.parseInt("!");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt("+");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt(":");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt(";");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt("?");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            ObjNumberer.parseInt("-");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(6, count);
    }
}
