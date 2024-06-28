package pieces;

import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a Rook piece in a chess game, extending from the Piece class.
 */
public class Rook extends Piece {

    /*
     * Constructor to initialize a Rook piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Rook belongs (White or Black).
     * position: The initial position of the Rook in algebraic notation (e.g., "a1" or "h8").
     */
    public Rook(PlayerEnum playerEnum, String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled = false;
        canMove = true;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Rook piece.
     * The Rook can move vertically or horizontally across the board.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Rook.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexes = new int[]{-1, 1, 0, 0};
        int[] jIndexes = new int[]{0, 0, -1, 1};

        // Directions:
        // - keeping decreasing i and j constant -> -1, 0
        // - keeping increasing i and j constant -> 1, 0
        // - keeping i constant and decreasing j -> 0, -1
        // - keeping i constant and increasing j -> 0, 1

        for (int x = 0; x < 4; x++) {
            moves.add(getValidMovesInDirection(iIndexes[x], jIndexes[x]));
        }
        return moves;
    }

    /*
     * Retrieves the current movable state of the Rook.
     * Returns: true if the Rook can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the Rook.
     * Returns: The position of the Rook in algebraic notation (e.g., "a1").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the movable state of the Rook.
     * Parameters:
     * canMove: true to allow the Rook to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Updates the position of the Rook on the chessboard.
     * Parameters:
     * position: The new position of the Rook in algebraic notation (e.g., "a1").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Rook belongs.
     * Returns: The PlayerEnum value representing the player of the Rook (White or Black).
     */
    @Override
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the Rook has been killed (captured).
     * Returns: true if the Rook has been killed, false otherwise.
     */
    @Override
    public boolean isKilled() {
        return isKilled;
    }

    /*
     * Updates the killed state of the Rook.
     * Parameters:
     * isKilled: true if the Rook is killed (captured), false otherwise.
     */
    @Override
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
}
