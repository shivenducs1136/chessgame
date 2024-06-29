package io.github.shivenducs1136.rules;


import java.util.ArrayList;
import java.util.List;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.abstracts.Rule;
import io.github.shivenducs1136.chess.Board;
import io.github.shivenducs1136.enums.PieceEnum;

/*
 * Represents a Bishop piece in a chess game, extending from the Piece class.
 */
public class BishopRule extends Rule {

    /*
     * Constructor to initialize a Bishop piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Bishop belongs (White or Black).
     * position: The initial position of the Bishop in algebraic notation (e.g., "e2").
     */
    public BishopRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Bishop piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Bishop.
     */
    public List<List<String>> expectedPaths() {
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

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Bishop;
    }
}


