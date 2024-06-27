package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgebraicNotationConverter {
    // Maps for converting between algebraic notation and coordinate notation
    Map<Character, Character> algebraMap = new HashMap<>();
    Map<Character, Character> numberMap = new HashMap<>();
    Map<Character, Character> algebraMapFlipped = new HashMap<>();
    Map<Character, Character> numberMapFlipped = new HashMap<>();

    // Constructor to initialize the maps
    public AlgebraicNotationConverter() {
        initializeMap();
    }

    /*
     * Converts algebraic notation to coordinate notation.
     * Parameters:
     * algebraicNotation: The input string in algebraic notation format.
     * Returns: The corresponding coordinate notation string.
     */
    public String getCoordinatesFromAlgebraicNotation(String algebraicNotation) {
        String res = "";
        if (algebraicNotation.length() > 1) {
            if (numberMap.containsKey(algebraicNotation.charAt(1))) {
                res += numberMap.get(algebraicNotation.charAt(1));
            }
            if (algebraMap.containsKey(algebraicNotation.charAt(0))) {
                res += algebraMap.get(algebraicNotation.charAt(0));
            }
        }
        return res;
    }

    /*
     * Converts a list of algebraic notations to corresponding coordinate notations.
     * Parameters:
     * algebraicNotations: The list of strings in algebraic notation format.
     * Returns: A list of corresponding coordinate notation strings.
     */
    public List<String> getCoordinatesFromAlgebraicNotation(List<String> algebraicNotations) {
        List<String> res = new ArrayList<>();
        for (String s : algebraicNotations) {
            res.add(getCoordinatesFromAlgebraicNotation(s));
        }
        return res;
    }

    // Private method to initialize the maps for conversion
    private void initializeMap() {
        algebraMap.put('a', '7');
        algebraMap.put('b', '6');
        algebraMap.put('c', '5');
        algebraMap.put('d', '4');
        algebraMap.put('e', '3');
        algebraMap.put('f', '2');
        algebraMap.put('g', '1');
        algebraMap.put('h', '0');

        numberMap.put('1', '0');
        numberMap.put('2', '1');
        numberMap.put('3', '2');
        numberMap.put('4', '3');
        numberMap.put('5', '4');
        numberMap.put('6', '5');
        numberMap.put('7', '6');
        numberMap.put('8', '7');

        algebraMapFlipped.put('7', 'a');
        algebraMapFlipped.put('6', 'b');
        algebraMapFlipped.put('5', 'c');
        algebraMapFlipped.put('4', 'd');
        algebraMapFlipped.put('3', 'e');
        algebraMapFlipped.put('2', 'f');
        algebraMapFlipped.put('1', 'g');
        algebraMapFlipped.put('0', 'h');

        numberMapFlipped.put('0', '1');
        numberMapFlipped.put('1', '2');
        numberMapFlipped.put('2', '3');
        numberMapFlipped.put('3', '4');
        numberMapFlipped.put('4', '5');
        numberMapFlipped.put('5', '6');
        numberMapFlipped.put('6', '7');
        numberMapFlipped.put('7', '8');
    }

    /*
     * Converts coordinate notation to algebraic notation.
     * Parameters:
     * coordinateNotation: The input string in coordinate notation format.
     * Returns: The corresponding algebraic notation string.
     */
    public String getAlgebraicNotationFromCoordinates(String coordinateNotation) {
        String res = "";
        if (coordinateNotation.length() > 1) {
            if (algebraMapFlipped.containsKey(coordinateNotation.charAt(1))) {
                res += algebraMapFlipped.get(coordinateNotation.charAt(1));
            }
            if (numberMapFlipped.containsKey(coordinateNotation.charAt(0))) {
                res += numberMapFlipped.get(coordinateNotation.charAt(0));
            }
        }
        return res;
    }

    /*
     * Converts a list of coordinate notations to corresponding algebraic notations.
     * Parameters:
     * positions: The list of strings in coordinate notation format.
     * Returns: A list of corresponding algebraic notation strings.
     */
    public List<String> getAlgebraicNotationFromCoordinates(List<String> positions) {
        List<String> res = new ArrayList<>();
        for (String s : positions) {
            res.add(getAlgebraicNotationFromCoordinates(s));
        }
        return res;
    }
}
