package pieces;

import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.Board;
import enums.PieceEnum;

/*
 * Represents a Rook piece in a chess game, extending from the Piece class.
 */
public class RookRule extends Rule {

    /*
     * Constructor to initialize a Rook piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the Rook belongs (White or Black).
     * position: The initial position of the Rook in algebraic notation (e.g., "a1" or "h8").
     */
    public RookRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the Rook piece.
     * The Rook can move vertically or horizontally across the board.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the Rook.
     */
    public List<List<String>> expectedPaths() {
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

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Rook;
    }
}
