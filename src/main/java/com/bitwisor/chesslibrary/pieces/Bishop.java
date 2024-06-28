package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a Bishop piece in a chess game, extending from the Piece class.
 */
public class Bishop extends Piece {

    /*
     * Constructor to initialize a Bishop piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Bishop belongs (White or Black).
     * position: The initial position of the Bishop in algebraic notation (e.g., "e2").
     */
    public Bishop(PlayerEnum playerEnum, String position) {
        isKilled = false;
        canMove = true;
        player = playerEnum;
        this.position = position;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Bishop piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Bishop.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexes = new int[] { -1, 1, 1, -1 };
        int[] jIndexes = new int[] { -1, 1, -1, 1 };

        // Directions: decreasing i and decreasing j -> -1,-1
        //             increasing i and increasing j -> 1,1
        //             increasing i and decreasing j -> 1,-1
        //             decreasing i and increasing j -> -1,1
        for (int x = 0; x < 4; x++) {
            moves.add(getValidMovesInDirection(iIndexes[x], jIndexes[x]));
        }
        return moves;
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
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the Bishop has been killed (captured).
     * Returns: true if the Bishop has been killed, false otherwise.
     */
    public boolean isKilled() {
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

}


