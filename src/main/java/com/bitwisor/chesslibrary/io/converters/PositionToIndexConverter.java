package io.converters;

import exceptions.PositionSizeTooShortException;

public class PositionToIndexConverter {
    /*
     * Converts the first character of the position string to its integer representation.
     * Parameters:
     * position: The position string in format "ij" where i is row index and j is column index.
     * Throws:
     * PositionSizeTooShortException: If the position string length is less than 2.
     * Returns: The integer value of the row index i.
     */
    public int getIindex(String position) {
        if (position.length() < 2) throw new PositionSizeTooShortException();
        return position.charAt(0) - '0';
    }

    /*
     * Converts the second character of the position string to its integer representation.
     * Parameters:
     * position: The position string in format "ij" where i is row index and j is column index.
     * Throws:
     * PositionSizeTooShortException: If the position string length is less than 2.
     * Returns: The integer value of the column index j.
     */
    public int getJindex(String position) {
        if (position.length() < 2) throw new PositionSizeTooShortException();
        return position.charAt(1) - '0';
    }

    /*
     * Converts row index i and column index j to a position string.
     * Parameters:
     * i: The row index, ranging from 0 to 7.
     * j: The column index, ranging from 0 to 7.
     * Throws:
     * IndexOutOfBoundsException: If either i or j is out of the valid range [0, 7].
     * Returns: The position string in format "ij".
     */
    public String getPositionFromIndexes(int i, int j) {
        if (i < 0 || i > 7) throw new IndexOutOfBoundsException();
        if (j < 0 || j > 7) throw new IndexOutOfBoundsException();
        return i + "" + j;
    }

    /*
     * Checks if the given row index i and column index j are within the valid board range.
     * Parameters:
     * i: The row index to check.
     * j: The column index to check.
     * Returns: true if both i and j are within the range [0, 7], false otherwise.
     */
    public boolean isIndexSafe(int i, int j) {
        return i >= 0 && i < 8 && j >= 0 && j < 8;
    }
}
