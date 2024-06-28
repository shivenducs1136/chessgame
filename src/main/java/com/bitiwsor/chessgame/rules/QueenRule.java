package com.bitiwsor.chessgame.rules;


import java.util.ArrayList;
import java.util.List;

import com.bitiwsor.chessgame.abstracts.Piece;
import com.bitiwsor.chessgame.abstracts.Rule;
import com.bitiwsor.chessgame.chess.Board;
import com.bitiwsor.chessgame.enums.PieceEnum;

/*
 * Represents a Queen piece in a chess game, extending from the Piece class.
 */
public class QueenRule extends Rule {

    /*
     * Constructor to initialize a Queen piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Queen belongs (White or Black).
     * position: The initial position of the Queen in algebraic notation (e.g., "d1" or "d8").
     */
    public QueenRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Queen piece.
     * The Queen can move in any direction: horizontally, vertically, or diagonally.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Queen.
     */
    public List<List<String>> expectedPaths() {
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

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Queen;
    }
}


