package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a Queen piece in a chess game, extending from the Piece class.
 */
public class Queen extends Piece {

    /*
     * Constructor to initialize a Queen piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Queen belongs (White or Black).
     * position: The initial position of the Queen in algebraic notation (e.g., "d1" or "d8").
     */
    public Queen(PlayerEnum playerEnum, String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled = false;
        canMove = true;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Queen piece.
     * The Queen can move in any direction: horizontally, vertically, or diagonally.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Queen.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexes = new int[]{-1, 1, 0, 0, -1, 1, 1, -1};
        int[] jIndexes = new int[]{0, 0, -1, 1, -1, 1, -1, 1};

        // Directions:
        // - keeping decreasing i and j constant -> -1, 0
        // - keeping increasing i and j constant -> 1, 0
        // - keeping i constant and decreasing j -> 0, -1
        // - keeping i constant and increasing j -> 0, 1
        // - decreasing i and decreasing j -> -1, -1
        // - increasing i and increasing j -> 1, 1
        // - increasing i and decreasing j -> 1, -1
        // - decreasing i and increasing j -> -1, 1

        for (int x = 0; x < 8; x++) {
            moves.add(getValidMovesInDirection(iIndexes[x], jIndexes[x]));
        }
        return moves;
    }

    /*
     * Retrieves the current movable state of the Queen.
     * Returns: true if the Queen can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the Queen.
     * Returns: The position of the Queen in algebraic notation (e.g., "d1").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the movable state of the Queen.
     * Parameters:
     * canMove: true to allow the Queen to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Updates the position of the Queen on the chessboard.
     * Parameters:
     * position: The new position of the Queen in algebraic notation (e.g., "d1").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Queen belongs.
     * Returns: The PlayerEnum value representing the player of the Queen (White or Black).
     */
    @Override
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the Queen has been killed (captured).
     * Returns: true if the Queen has been killed, false otherwise.
     */
    @Override
    public boolean isKilled() {
        return isKilled;
    }

    /*
     * Updates the killed state of the Queen.
     * Parameters:
     * isKilled: true if the Queen is killed (captured), false otherwise.
     */
    @Override
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }

}


