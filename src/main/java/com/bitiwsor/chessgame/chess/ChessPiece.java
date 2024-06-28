package com.bitiwsor.chessgame.chess;


import com.bitiwsor.chessgame.abstracts.Piece;
import com.bitiwsor.chessgame.enums.ColorEnum;
import com.bitiwsor.chessgame.enums.PieceEnum;


public class ChessPiece extends Piece {

    public ChessPiece(PieceEnum pieceType, ColorEnum colorEnum, String position) {
        super(pieceType, colorEnum, position);
    }
}
