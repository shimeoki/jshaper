package io.github.shimeoki.jshaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class NumbererTest {

    @Test
    public void parseNullFloat() {
        try {
            Numberer.parseFloat(null);
        } catch (final NullPointerException e) {
            return;
        } catch (final ShaperError e) {
            fail("caught ShaperError");
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseNullInt() {
        try {
            Numberer.parseInt(null);
        } catch (final NullPointerException e) {
            return;
        } catch (final ShaperError e) {
            fail("caught ShaperError");
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void parseValidFloat() {
        try {
            assertEquals(0.0f, Numberer.parseFloat("0.0"));
            assertEquals(1.0f, Numberer.parseFloat("1.0"));
            assertEquals(0.123f, Numberer.parseFloat("0.123"));
            assertEquals(100.456f, Numberer.parseFloat("100.456"));
            assertEquals(50f, Numberer.parseFloat("50"));
            assertEquals(-84f, Numberer.parseFloat("-84"));
            assertEquals(-0.789f, Numberer.parseFloat("-0.789"));
            assertEquals(-0.0f, Numberer.parseFloat("-0.0"));
        } catch (final ShaperError e) {
            fail("caught ShaperError");
        }
    }

    @Test
    public void parseValidInt() {
        try {
            assertEquals(0, Numberer.parseInt("0"));
            assertEquals(1, Numberer.parseInt("1"));
            assertEquals(123, Numberer.parseInt("123"));
            assertEquals(456, Numberer.parseInt("456"));
            assertEquals(-84, Numberer.parseInt("-84"));
            assertEquals(-789, Numberer.parseInt("-789"));
            assertEquals(0, Numberer.parseInt("-0"));
        } catch (final ShaperError e) {
            fail("caught ShaperError");
        }
    }

    @Test
    public void parseBlankFloat() {
        try {
            Numberer.parseFloat("");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseBlankInt() {
        try {
            Numberer.parseInt("");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseSpaceFloat() {
        try {
            Numberer.parseFloat(" ");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseSpaceInt() {
        try {
            Numberer.parseInt(" ");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseSpacedAroundFloat() {
        int count = 0;

        try {
            Numberer.parseFloat("0.123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat(" 0.123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat(" 0.123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("   0.123   ");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(0, count); // java moment? idk why
    }

    @Test
    public void parseSpacedAroundInt() {
        int count = 0;

        try {
            Numberer.parseInt("123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt(" 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt(" 123 ");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt("   123   ");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    public void parseSpacedInsideFloat() {
        int count = 0;

        try {
            Numberer.parseFloat("0. 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("0 .123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("0 . 123");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("0  .  123");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    public void parseCommaFloat() {
        try {
            Numberer.parseFloat("0,123");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseStringAsFloat() {
        try {
            Numberer.parseFloat("value");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseStringAsInt() {
        try {
            Numberer.parseInt("value");
        } catch (final ShaperError e) {
            return;
        }

        fail("no ShaperError caught");
    }

    @Test
    public void parseSpecialCharsAsFloat() {
        int count = 0;

        try {
            Numberer.parseFloat("!");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("+");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat(":");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat(";");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("?");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseFloat("-");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(6, count);
    }

    @Test
    public void parseSpecialCharsAsInt() {
        int count = 0;

        try {
            Numberer.parseInt("!");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt("+");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt(":");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt(";");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt("?");
        } catch (final ShaperError e) {
            count++;
        }

        try {
            Numberer.parseInt("-");
        } catch (final ShaperError e) {
            count++;
        }

        assertEquals(6, count);
    }
}
