package io.github.shivenducs1136.enums;

public enum PieceEnum {
    King(false),
    Queen(true),
    Knight(true),
    Bishop(true),
    Rook(true),
    Pawn(false);

    public final boolean canUpgradeByPawn;
    private PieceEnum(boolean canUpgradeByPawn){
        this.canUpgradeByPawn = canUpgradeByPawn;
    }
}