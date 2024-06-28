package com.bitiwsor.chessgame.enums;
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