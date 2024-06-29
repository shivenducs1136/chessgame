package io.github.shivenducs1136.algebraicnotationsconverter;


import io.github.shivenducs1136.exceptions.PositionSizeTooShortException;
import io.github.shivenducs1136.io.converters.PositionToIndexConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionToIndexConverterTest {

    PositionToIndexConverter converter = new PositionToIndexConverter();

    @Test
    public void testGetIindex() {
        assertEquals(3, converter.getIindex("34"));
        assertEquals(0, converter.getIindex("00"));
        assertThrows(PositionSizeTooShortException.class, () -> converter.getIindex("3"));
    }

    @Test
    public void testGetJindex() {
        assertEquals(4, converter.getJindex("34"));
        assertEquals(0, converter.getJindex("00"));
        assertThrows(PositionSizeTooShortException.class, () -> converter.getJindex("4"));
    }

    @Test
    public void testGetPositionFromIndexes() {
        assertEquals("34", converter.getPositionFromIndexes(3, 4));
        assertEquals("00", converter.getPositionFromIndexes(0, 0));
        assertEquals("77", converter.getPositionFromIndexes(7, 7));
        assertThrows(IndexOutOfBoundsException.class, () -> converter.getPositionFromIndexes(-1, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> converter.getPositionFromIndexes(3, 8));
    }

    @Test
    public void testIsIndexSafe() {
        assertTrue(converter.isIndexSafe(3, 4));
        assertTrue(converter.isIndexSafe(0, 0));
        assertTrue(converter.isIndexSafe(7, 7));
        assertFalse(converter.isIndexSafe(8, 4));
        assertFalse(converter.isIndexSafe(3, -1));
    }
}

