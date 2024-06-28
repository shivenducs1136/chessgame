package abstracts;

import chess.Board;
import enums.PieceEnum;
import io.converters.PositionToIndexConverter;

import java.util.ArrayList;
import java.util.List;

public abstract class Rule {

    protected PositionToIndexConverter converter;
    protected Board board;
    protected Piece piece;

    protected Rule(Board board,Piece piece){
        converter = new PositionToIndexConverter();
        this.board = board;
        this.piece = piece;
    }

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

    abstract public PieceEnum pieceType();
}
