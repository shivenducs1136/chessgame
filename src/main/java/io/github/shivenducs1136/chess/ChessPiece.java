package io.github.shivenducs1136.chess;


import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;


public class ChessPiece extends Piece {

    public ChessPiece(PieceEnum pieceType, ColorEnum colorEnum, String position) {
        super(pieceType, colorEnum, position);
    }
}
