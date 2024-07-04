package io.github.shivenducs1136.enums;

public enum PieceEnum {
    King(false,'k'),
    Queen(true,'q'),
    Knight(true,'n'),
    Bishop(true,'b'),
    Rook(true,'r'),
    Pawn(false,'p');

    public final boolean canUpgradeByPawn;
    public final char getPieceChar;

    PieceEnum(boolean canUpgradeByPawn,char getPieceChar)
    {
        this.canUpgradeByPawn = canUpgradeByPawn;
        this.getPieceChar = getPieceChar;
    }
}