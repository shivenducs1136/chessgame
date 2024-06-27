package pieces;



import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a Knight piece in a chess game, extending from the Piece class.
 */
public class Knight extends Piece {

    String position;

    /*
     * Constructor to initialize a Knight piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Knight belongs (White or Black).
     * position: The initial position of the Knight in algebraic notation (e.g., "e1").
     */
    public Knight(PlayerEnum playerEnum, String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled = false;
        canMove = true;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Knight piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Knight.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1, -1, -2, -2, -1, 1, 2, 2};
        int[] jIndexs = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
        int idx = converter.getIindex(position);
        int jdx = converter.getJindex(position);

        for (int x = 0; x < 8; x++) {
            List<String> path = new ArrayList<>();
            int newIdx = idx + iIndexs[x];
            int newJdx = jdx + jIndexs[x];

            if (converter.isIndexSafe(newIdx, newJdx)) {
                if ((pieceManager.getPieceAtPosition(newIdx, newJdx) == null))
                    path.add(newIdx + "" + newJdx);
                else {
                    if ((pieceManager.getPieceAtPosition(newIdx, newJdx).getPlayer() != player))
                        path.add(newIdx + "" + newJdx);
                }
            }
            moves.add(path);
        }
        return moves;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Knight belongs.
     * Returns: The PlayerEnum value representing the player of the Knight (White or Black).
     */
    public PlayerEnum getPlayerEnum() {
        return player;
    }

    /*
     * Retrieves the current movable state of the Knight.
     * Returns: true if the Knight can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the Knight.
     * Returns: The position of the Knight in algebraic notation (e.g., "e1").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the movable state of the Knight.
     * Parameters:
     * canMove: true to allow the Knight to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Updates the position of the Knight on the chessboard.
     * Parameters:
     * position: The new position of the Knight in algebraic notation (e.g., "e1").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Knight belongs.
     * Returns: The PlayerEnum value representing the player of the Knight (White or Black).
     */
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the Knight has been killed (captured).
     * Returns: true if the Knight has been killed, false otherwise.
     */
    public boolean isKilled() {
        return isKilled;
    }

    /*
     * Updates the killed state of the Knight.
     * Parameters:
     * isKilled: true if the Knight is killed (captured), false otherwise.
     */
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
}

