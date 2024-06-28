package chess;


import abstracts.Piece;
import enums.ColorEnum;
import enums.PieceEnum;


public class ChessPiece extends Piece {

    public ChessPiece(PieceEnum pieceType, ColorEnum colorEnum, String position) {
        super(pieceType, colorEnum, position);
    }
}
