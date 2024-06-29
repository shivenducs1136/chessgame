package io.github.shivenducs1136.abstracts;

import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;

public abstract class Piece {
    protected ColorEnum playerColor;
    protected boolean isKilled;
    protected boolean canMove;
    protected String position = "";
    protected boolean isFirstMove = true;
    protected PieceEnum pieceType;

    protected Piece(PieceEnum pieceType,ColorEnum colorEnum,String position){
        this.playerColor = colorEnum;
        this.position = position;
        this.isKilled = false;
        this.canMove = true;
        this.pieceType = pieceType;
    }
    /*
     * Retrieves the current movable state of the Bishop.
     * Returns: true if the Bishop can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the Bishop.
     * Returns: The position of the Bishop in algebraic notation (e.g., "e2").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the movable state of the Bishop.
     * Parameters:
     * canMove: true to allow the Bishop to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Updates the position of the Bishop on the chessboard.
     * Parameters:
     * position: The new position of the Bishop in algebraic notation (e.g., "e2").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Bishop belongs.
     * Returns: The PlayerEnum value representing the player of the Bishop (White or Black).
     */
    public ColorEnum getPlayerColor() {
        return playerColor;
    }

    /*
     * Checks if the Bishop has been killed (captured).
     * Returns: true if the Bishop has been killed, false otherwise.
     */
    public boolean getIsKilled() {
        return isKilled;
    }
    /*
     * Updates the killed state of the Bishop.
     * Parameters:
     * isKilled: true if the Bishop is killed (captured), false otherwise.
     */
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
    public void setFirstMoveToFalse(){
        this.isFirstMove = false;
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public PieceEnum getPieceType() {
        return this.pieceType;
    }
}
