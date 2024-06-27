package abstracts; 

import java.util.ArrayList;
import java.util.List;

import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;
import io.PositionToIndexConverter;

public abstract class Piece {
    protected PlayerEnum player;
    protected boolean isKilled;
    protected boolean canMove;
    protected String position = "";
    protected boolean isFirstMove = true;
    protected final PositionToIndexConverter converter;
    protected PieceManager pieceManager;
    protected Piece(){
        converter = new PositionToIndexConverter();
    }
    /*
     * Parameters:
     * pieceManager: The PieceManager object responsible for managing the pieces on the board.
     * Returns: A two-dimensional list of strings representing the expected paths for the current piece.
     */
    abstract public List<List<String>> expectedPaths(PieceManager pieceManager);
    /*
     * Returns: The PlayerEnum value representing the player to which the piece belongs.
     */
    abstract public PlayerEnum getPlayer();
    /*
     * Returns: A boolean value indicating whether the piece has been killed (true) or not (false).
     */
    abstract public boolean isKilled();
    /*
     * Returns: The current position of the piece in internal chess notation (e.g., "01" means "g1").
     */
    abstract public String getPosition();
    /*
     * Parameters:
     * isKilled: A boolean value indicating whether the piece is to be marked as killed (true) or not (false).
     * Returns: void. This method does not return a value.
     */
    abstract public void setIsKilled(boolean isKilled);
    /*
     * Parameters:
     * canMove: A boolean value indicating whether the piece is allowed to move (true) or not (false).
     * Returns: void. This method does not return a value.
     */
    abstract public void setCanMove(boolean canMove);
    /*
     * Returns: A boolean value indicating whether the piece is allowed to move (true) or not (false).
     */
    abstract public boolean getCanMove();
    /*
     * Parameters:
     * newPosition: The new position of the piece in standard chess notation (e.g., "e4").
     * Returns: void. This method does not return a value.
     */
    abstract public void setPosition(String newPosition);
    /*
     * Parameters:
     * i_direction: The row direction in which to check for valid moves.
     * j_direction: The column direction in which to check for valid moves.
     * Returns: A list of strings representing the valid moves in the specified direction.
     */
    protected List<String> getValidMovesInDirection(int i_direction,int j_direction){
        List<String> moves = new ArrayList<>();
        int idx = converter.getIindex(position);
        int jdx = converter.getJindex(position);
        int i = idx + i_direction;
        int j = jdx + j_direction;
        while(
                (converter.isIndexSafe(i,j) && (pieceManager.getPieceAtPosition(i,j) == null)) ||
                        converter.isIndexSafe(i,j) && (pieceManager.getPieceAtPosition(i,j).getPlayer() != player)
        ){

            moves.add(i+""+j);
            if(pieceManager.getPieceAtPosition(i,j) != null){
                break;
            }
            i+=i_direction;
            j+=j_direction;

        }
        return moves;
    }
    /*
     * Returns: void. This method does not return a value.
     * Description: Sets the piece's first move flag to false, indicating that it has moved at least once.
     */
    public void setFirstMoveToFalse(){
        this.isFirstMove = false;
    }
    /*
     * Returns: A boolean value indicating whether the piece is making its first move (true) or not (false).
     */
    public boolean isFirstMove(){return isFirstMove;}
}
