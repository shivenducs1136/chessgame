package io.github.shivenducs1136.chess;


import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;


/**
 * This class represents a specific chess piece. It extends the generic Piece class,
 * providing initialization for a chess piece with a given type, color, and position.
 */
public class ChessPiece extends Piece {

    /**
     * Constructor to initialize a chess piece with the specified type, color, and position.
     *
     * @param pieceType the type of the piece (e.g., King, Queen, Rook, etc.)
     * @param colorEnum the color of the piece (e.g., White, Black)
     * @param position the position of the piece on the chess board (e.g., "e4", "a1")
     */
    public ChessPiece(PieceEnum pieceType, ColorEnum colorEnum, String position) {
        super(pieceType, colorEnum, position); // Call the superclass constructor
    }
}
