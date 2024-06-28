package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a Pawn piece in a chess game, extending from the Piece class.
 */
public class Pawn extends Piece {

    public List<String> upgradableMoves = new ArrayList<>();

    /*
     * Constructor to initialize a Pawn piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Pawn belongs (White or Black).
     * position: The initial position of the Pawn in algebraic notation (e.g., "e2" or "e7").
     */
    public Pawn(PlayerEnum playerEnum, String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled = false;
        canMove = true;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Pawn piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Pawn.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        List<String> pathForward = new ArrayList<>();
        List<String> pathDiag = new ArrayList<>();
        int idx = i;

        // For White player
        if (player == PlayerEnum.White) {
            if (isFirstMove) {
                int count = 0;
                while (count < 2) {
                    idx++;
                    if (pieceManager.isPositionEmptyInBoard(idx, j)) {
                        pathForward.add(idx + "" + j);
                    } else {
                        break;
                    }
                    count++;
                }
            } else {
                idx++;
                if (pieceManager.isPositionEmptyInBoard(idx, j)) {
                    pathForward.add(idx + "" + j);
                }
            }

            int dj1 = j - 1;
            int di = i + 1;
            int dj2 = j + 1;

            // Check diagonal captures
            Piece piece1 = pieceManager.getPieceAtPosition(di, dj1);
            Piece piece2 = pieceManager.getPieceAtPosition(di, dj2);

            if (dj1 >= 0 && dj1 < 8) {
                if (piece1 != null && piece1.getPlayer() != player) {
                    pathDiag.add(di + "" + dj1);
                }
            }
            if (dj2 >= 0 && dj2 < 8) {
                if (piece2 != null && piece2.getPlayer() != player) {
                    pathDiag.add(di + "" + dj2);
                }
            }

            // Check for pawn promotion
            for (String s : pathForward) {
                int newI = converter.getIindex(s);
                if (newI == 7) {
                    upgradableMoves.add(s);
                }
            }
            for (String s : pathDiag) {
                int newI = converter.getIindex(s);
                if (newI == 7) {
                    upgradableMoves.add(s);
                }
            }

            moves.add(pathForward);
            moves.add(pathDiag);
            return moves;
        }
        // For Black player
        else {
            if (isFirstMove) {
                int count = 0;
                while (count < 2) {
                    idx--;
                    if (pieceManager.isPositionEmptyInBoard(idx, j)) {
                        pathForward.add(idx + "" + j);
                    } else {
                        break;
                    }
                    count++;
                }
            } else {
                idx--;
                if (pieceManager.isPositionEmptyInBoard(idx, j)) {
                    pathForward.add(idx + "" + j);
                }
            }

            int dj1 = j - 1;
            int di = i - 1;
            int dj2 = j + 1;

            // Check diagonal captures
            Piece piece1 = pieceManager.getPieceAtPosition(di, dj1);
            Piece piece2 = pieceManager.getPieceAtPosition(di, dj2);

            if (dj1 >= 0 && dj1 < 8) {
                if (piece1 != null && piece1.getPlayer() != player) {
                    pathDiag.add(di + "" + dj1);
                }
            }
            if (dj2 >= 0 && dj2 < 8) {
                if (piece2 != null && piece2.getPlayer() != player) {
                    pathDiag.add(di + "" + dj2);
                }
            }

            // Check for pawn promotion
            for (String s : pathForward) {
                int newI = converter.getIindex(s);
                if (newI == 0) {
                    upgradableMoves.add(s);
                }
            }
            for (String s : pathDiag) {
                int newI = converter.getIindex(s);
                if (newI == 0) {
                    upgradableMoves.add(s);
                }
            }

            moves.add(pathForward);
            moves.add(pathDiag);
            return moves;
        }
    }

    /*
     * Retrieves the current movable state of the Pawn.
     * Returns: true if the Pawn can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the Pawn.
     * Returns: The position of the Pawn in algebraic notation (e.g., "e2").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the movable state of the Pawn.
     * Parameters:
     * canMove: true to allow the Pawn to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Updates the position of the Pawn on the chessboard.
     * Parameters:
     * position: The new position of the Pawn in algebraic notation (e.g., "e2").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Marks the Pawn's first move as completed.
     * This method is typically called after the Pawn's initial two-square move.
     */
    public void firstMoveDone() {
        isFirstMove = false;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the Pawn belongs.
     * Returns: The PlayerEnum value representing the player of the Pawn (White or Black).
     */
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the Pawn has been killed (captured).
     * Returns: true if the Pawn has been killed, false otherwise.
     */
    public boolean isKilled() {
        return isKilled;
    }

    /*
     * Updates the killed state of the Pawn.
     * Parameters:
     * isKilled: true if the Pawn is killed (captured), false otherwise.
     */
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
}


