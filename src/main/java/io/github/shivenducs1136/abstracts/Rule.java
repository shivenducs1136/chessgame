package io.github.shivenducs1136.abstracts;

import io.github.shivenducs1136.chess.Board;
import io.github.shivenducs1136.enums.PieceEnum;
import io.github.shivenducs1136.io.converters.PositionToIndexConverter;

import java.util.ArrayList;
import java.util.List;
/**
 * This abstract class defines the basic structure and common behavior
 * for all specific chess rules. Each rule is associated with a particular
 * piece on the board and provides common properties and methods for
 * rule implementation.
 */
public abstract class Rule {

    // Converter to convert board positions to indices
    protected PositionToIndexConverter converter;

    // Reference to the board on which the piece is located
    protected Board board;

    // Reference to the piece to which this rule applies
    protected Piece piece;

    /**
     * Constructor to initialize the Rule with the board and piece.
     * Initializes the PositionToIndexConverter.
     *
     * @param board the board on which the piece is located
     * @param piece the piece to which this rule applies
     */
    protected Rule(Board board, Piece piece) {
        // Initialize the position to index converter
        converter = new PositionToIndexConverter();

        // Set the board and piece references
        this.board = board;
        this.piece = piece;
    }

    /**
     * Abstract method to be implemented by subclasses to return
     * the type of the piece associated with this rule.
     *
     * @return the type of the piece as a PieceEnum.
     */
    abstract public PieceEnum pieceType();
    /*
     * Parameters:
     * pieceManager: The PieceManager object responsible for managing the pieces on the board.
     * Returns: A two-dimensional list of strings representing the expected paths for the current piece.
     */
    abstract public List<List<String>> expectedPaths();

    /*
     * Parameters:
     * i_direction: The row direction in which to check for valid moves.
     * j_direction: The column direction in which to check for valid moves.
     * Returns: A list of strings representing the valid moves in the specified direction.
     */
    protected List<String> getValidMovesInDirection(int i_direction, int j_direction){
        List<String> moves = new ArrayList<>();
        int idx = converter.getIindex(piece.getPosition());
        int jdx = converter.getJindex(piece.getPosition());
        int i = idx + i_direction;
        int j = jdx + j_direction;
        while(
                (converter.isIndexSafe(i,j) && (board.getPieceAtPosition(i,j) == null)) ||
                        converter.isIndexSafe(i,j) && (board.getPieceAtPosition(i,j).getPlayerColor() != piece.getPlayerColor())
        ){

            moves.add(i+""+j);
            if(board.getPieceAtPosition(i,j) != null){
                break;
            }
            i+=i_direction;
            j+=j_direction;

        }
        return moves;
    }

}
