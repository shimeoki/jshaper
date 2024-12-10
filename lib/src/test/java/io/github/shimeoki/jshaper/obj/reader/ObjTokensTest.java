package io.github.shimeoki.jshaper.obj.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public final class ObjTokensTest {

    @Test
    public void make() {
        final ObjTokens t = new ObjTokens();

        assertTrue(t.empty());
    }

    @Test
    public void addNull() {
        final ObjTokens t = new ObjTokens();

        try {
            t.add(null);
        } catch (final NullPointerException e) {
            return;
        }

        fail("no NullPointerException caught");
    }

    @Test
    public void empty() {
        final ObjTokens t = new ObjTokens();

        assertEquals(t.empty(), t.size() == 0);
    }

    @Test
    public void notEmpty() {
        final ObjTokens t = new ObjTokens();
        t.add(new ObjTokenized("#"));

        assertEquals(!t.empty(), t.size() != 0);
    }

    @Test
    public void add() {
        final ObjTokens tokens = new ObjTokens();
        final ObjTokenized tokenized = new ObjTokenized("v");
        tokens.add(tokenized);

        assertEquals(1, tokens.size());
        assertEquals(tokenized, tokens.tokenized(0));
    }

    @Test
    public void clear() {
        final ObjTokens t = new ObjTokens();
        t.add(new ObjTokenized("vt"));

        assertTrue(!t.empty());

        t.clear();

        assertTrue(t.empty());
    }

    @Test
    public void lineToken() {
        final ObjTokens tokens = new ObjTokens();
        final ObjTokenized tokenized = new ObjTokenized("vn");
        final ObjToken token = tokenized.token();

        tokens.add(tokenized);

        assertEquals(token, tokens.token(0));
        assertEquals(token, tokens.lineToken());
    }

    @Test
    public void noLineToken() {
        final ObjTokens t = new ObjTokens();

        assertEquals(null, t.lineToken());
    }

    @Test
    public void noLineTokenIs() {
        final ObjTokens t = new ObjTokens();

        assertFalse(t.lineTokenIs(ObjToken.PARAMETER_SPACE_VERTEX));
    }

    @Test
    public void lineTokenIs() {
        final ObjTokens tokens = new ObjTokens();
        final ObjTokenized tokenized = new ObjTokenized("f");
        final ObjToken token = tokenized.token();

        tokens.add(tokenized);

        assertTrue(tokens.lineTokenIs(token));
        assertTrue(tokens.lineTokenIs(ObjToken.FACE));
        assertFalse(tokens.lineTokenIs(ObjToken.TEXTURE_VERTEX));
    }

    @Test
    public void size() {
        final ObjTokens t = new ObjTokens();
        assertEquals(0, t.size());

        t.add(new ObjTokenized("vp"));
        assertEquals(1, t.size());

        t.add(new ObjTokenized("g"));
        assertEquals(2, t.size());

        t.add(new ObjTokenized("100"));
        assertEquals(3, t.size());

        t.clear();
        assertEquals(0, t.size());
    }

    @Test
    public void validIndex() {
        final ObjTokens t = new ObjTokens();
        assertFalse(t.validIndex(0));

        t.add(new ObjTokenized("o"));
        assertTrue(t.validIndex(0));
        assertFalse(t.validIndex(1));

        t.add(new ObjTokenized("gg"));
        assertTrue(t.validIndex(1));
        assertFalse(t.validIndex(2));

        t.clear();
        assertFalse(t.validIndex(0));
        assertFalse(t.validIndex(1));
        assertFalse(t.validIndex(2));

        assertFalse(t.validIndex(-1));
        assertFalse(t.validIndex(-26));
        assertFalse(t.validIndex(3));
        assertFalse(t.validIndex(40));
    }

    @Test
    public void tokenized() {
        final ObjTokens tokens = new ObjTokens();

        final ObjTokenized t1 = new ObjTokenized("v");
        tokens.add(t1);

        assertEquals(t1, tokens.tokenized(0));

        final ObjTokenized t2 = new ObjTokenized("vt");
        tokens.add(t2);

        assertEquals(t2, tokens.tokenized(1));

        try {
            tokens.tokenized(-1);
        } catch (final IllegalArgumentException e) {
            return;
        }

        fail("no IllegalArgumentException caught");
    }

    @Test
    public void token() {
        final ObjTokens tokens = new ObjTokens();

        final ObjTokenized t1 = new ObjTokenized("vp");
        tokens.add(t1);

        assertEquals(t1.token(), tokens.token(0));

        final ObjTokenized t2 = new ObjTokenized("vn");
        tokens.add(t2);

        assertEquals(t2.token(), tokens.token(1));

        try {
            tokens.token(-1);
        } catch (final IllegalArgumentException e) {
            return;
        }

        fail("no IllegalArgumentException caught");
    }

    @Test
    public void value() {
        final ObjTokens tokens = new ObjTokens();

        final ObjTokenized t1 = new ObjTokenized("g");
        tokens.add(t1);

        assertEquals(t1.value(), tokens.value(0));

        final ObjTokenized t2 = new ObjTokenized("f");
        tokens.add(t2);

        assertEquals(t2.value(), tokens.value(1));

        try {
            tokens.value(-1);
        } catch (final IllegalArgumentException e) {
            return;
        }

        fail("no IllegalArgumentException caught");
    }
}
