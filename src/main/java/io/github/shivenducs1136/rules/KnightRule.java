package io.github.shivenducs1136.rules;



import java.util.ArrayList;
import java.util.List;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.abstracts.Rule;
import io.github.shivenducs1136.chess.Board;
import io.github.shivenducs1136.enums.PieceEnum;

/*
 * Represents a Knight piece in a chess game, extending from the Piece class.
 */
public class KnightRule extends Rule {

    /*
     * Constructor to initialize a Knight piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Knight belongs (White or Black).
     * position: The initial position of the Knight in algebraic notation (e.g., "e1").
     */
    public KnightRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Knight piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Knight.
     */
    public List<List<String>> expectedPaths() {
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1, -1, -2, -2, -1, 1, 2, 2};
        int[] jIndexs = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
        int idx = converter.getIindex(piece.getPosition());
        int jdx = converter.getJindex(piece.getPosition());

        for (int x = 0; x < 8; x++) {
            List<String> path = new ArrayList<>();
            int newIdx = idx + iIndexs[x];
            int newJdx = jdx + jIndexs[x];

            if (converter.isIndexSafe(newIdx, newJdx)) {
                if ((board.getPieceAtPosition(newIdx, newJdx) == null))
                    path.add(newIdx + "" + newJdx);
                else {
                    if ((board.getPieceAtPosition(newIdx, newJdx).getPlayerColor() != piece.getPlayerColor()))
                        path.add(newIdx + "" + newJdx);
                }
            }
            moves.add(path);
        }
        return moves;
    }

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Knight;
    }
}

