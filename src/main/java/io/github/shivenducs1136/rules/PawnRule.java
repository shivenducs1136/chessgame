package io.github.shivenducs1136.rules;


import java.util.ArrayList;
import java.util.List;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.abstracts.Rule;
import io.github.shivenducs1136.chess.Board;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;

/*
 * Represents a Pawn piece in a chess game, extending from the Piece class.
 */
public class PawnRule extends Rule {


    /*
     * Constructor to initialize a Pawn piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Pawn belongs (White or Black).
     * position: The initial position of the Pawn in algebraic notation (e.g., "e2" or "e7").
     */
    public PawnRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Pawn piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Pawn.
     */
    public List<List<String>> expectedPaths() {
        List<List<String>> moves = new ArrayList<>();
        int i = converter.getIindex(piece.getPosition());
        int j = converter.getJindex(piece.getPosition());
        List<String> pathForward = new ArrayList<>();
        List<String> pathDiag = new ArrayList<>();
        int idx = i;

        // For White player
        if (piece.getPlayerColor() == ColorEnum.White) {
            if (piece.isFirstMove()) {
                int count = 0;
                while (count < 2) {
                    idx++;
                    if (board.isPositionEmptyInBoard(idx, j)) {
                        pathForward.add(idx + "" + j);
                    } else {
                        break;
                    }
                    count++;
                }
            } else {
                idx++;
                if (board.isPositionEmptyInBoard(idx, j)) {
                    pathForward.add(idx + "" + j);
                }
            }

            int dj1 = j - 1;
            int di = i + 1;
            int dj2 = j + 1;

            // Check diagonal captures
            Piece piece1 = board.getPieceAtPosition(di, dj1);
            Piece piece2 = board.getPieceAtPosition(di, dj2);

            if (dj1 >= 0 && dj1 < 8) {
                if (piece1 != null && piece1.getPlayerColor() != piece.getPlayerColor()) {
                    pathDiag.add(di + "" + dj1);
                }
            }
            if (dj2 >= 0 && dj2 < 8) {
                if (piece2 != null && piece2.getPlayerColor() != piece.getPlayerColor()) {
                    pathDiag.add(di + "" + dj2);
                }
            }

            // Check for pawn promotion
            for (String s : pathForward) {
                int newI = converter.getIindex(s);

            }
            for (String s : pathDiag) {
                int newI = converter.getIindex(s);

            }

            moves.add(pathForward);
            moves.add(pathDiag);
            return moves;
        }
        // For Black player
        else {
            if (piece.isFirstMove()) {
                int count = 0;
                while (count < 2) {
                    idx--;
                    if (board.isPositionEmptyInBoard(idx, j)) {
                        pathForward.add(idx + "" + j);
                    } else {
                        break;
                    }
                    count++;
                }
            } else {
                idx--;
                if (board.isPositionEmptyInBoard(idx, j)) {
                    pathForward.add(idx + "" + j);
                }
            }

            int dj1 = j - 1;
            int di = i - 1;
            int dj2 = j + 1;

            // Check diagonal captures
            Piece piece1 = board.getPieceAtPosition(di, dj1);
            Piece piece2 = board.getPieceAtPosition(di, dj2);

            if (dj1 >= 0 && dj1 < 8) {
                if (piece1 != null && piece1.getPlayerColor() != piece.getPlayerColor()) {
                    pathDiag.add(di + "" + dj1);
                }
            }
            if (dj2 >= 0 && dj2 < 8) {
                if (piece2 != null && piece2.getPlayerColor() != piece.getPlayerColor()) {
                    pathDiag.add(di + "" + dj2);
                }
            }

            // Check for pawn promotion
            for (String s : pathForward) {
                int newI = converter.getIindex(s);

            }
            for (String s : pathDiag) {
                int newI = converter.getIindex(s);

            }

            moves.add(pathForward);
            moves.add(pathDiag);
            return moves;
        }
    }

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Pawn;
    }
}


