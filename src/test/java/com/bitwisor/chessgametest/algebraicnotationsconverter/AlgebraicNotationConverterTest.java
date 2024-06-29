package com.bitwisor.chessgametest.algebraicnotationsconverter;
import static org.junit.Assert.assertEquals;

import com.bitiwsor.chessgame.io.converters.AlgebraicNotationConverter;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;




    public class AlgebraicNotationConverterTest {

        AlgebraicNotationConverter converter = new AlgebraicNotationConverter();

        @Test
        public void testGetCoordinatesFromAlgebraicNotation_Single() {
            assertEquals("07", converter.getCoordinatesFromAlgebraicNotation("a1"));
            assertEquals("70", converter.getCoordinatesFromAlgebraicNotation("h8"));
            assertEquals("77", converter.getCoordinatesFromAlgebraicNotation("a8"));
            assertEquals("00", converter.getCoordinatesFromAlgebraicNotation("h1"));
            assertEquals("", converter.getCoordinatesFromAlgebraicNotation("a"));
            assertEquals("", converter.getCoordinatesFromAlgebraicNotation("i9"));
        }

        @Test
        public void testGetCoordinatesFromAlgebraicNotation_List() {
            List<String> input = Arrays.asList("a1", "b2", "c3");
            List<String> expected = Arrays.asList("07", "16", "25");
            assertEquals(expected, converter.getCoordinatesFromAlgebraicNotation(input));

            input = Arrays.asList("a1", "z9", "h8");
            expected = Arrays.asList("07", "", "70");
            assertEquals(expected, converter.getCoordinatesFromAlgebraicNotation(input));

            input = List.of();
            expected = List.of();
            assertEquals(expected, converter.getCoordinatesFromAlgebraicNotation(input));
        }

        @Test
        public void testGetAlgebraicNotationFromCoordinates_Single() {
            assertEquals("h8", converter.getAlgebraicNotationFromCoordinates("70"));
            assertEquals("a1", converter.getAlgebraicNotationFromCoordinates("07"));
            assertEquals("a8", converter.getAlgebraicNotationFromCoordinates("77"));
            assertEquals("h1", converter.getAlgebraicNotationFromCoordinates("00"));
            assertEquals("", converter.getAlgebraicNotationFromCoordinates("7"));
            assertEquals("g", converter.getAlgebraicNotationFromCoordinates("a1"));
        }

        @Test
        public void testGetAlgebraicNotationFromCoordinates_List() {
            List<String> input = Arrays.asList("07", "16", "25");
            List<String> expected = Arrays.asList("a1", "b2", "c3");
            assertEquals(expected, converter.getAlgebraicNotationFromCoordinates(input));

            input = Arrays.asList("07", "zz", "70");
            expected = Arrays.asList("a1", "", "h8");
            assertEquals(expected, converter.getAlgebraicNotationFromCoordinates(input));

            input = List.of();
            expected = List.of();
            assertEquals(expected, converter.getAlgebraicNotationFromCoordinates(input));
        }
    }

