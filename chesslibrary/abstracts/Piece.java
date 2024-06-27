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
    protected String position;
    protected boolean isFirstMove = true;
    protected final PositionToIndexConverter converter;
    protected PieceManager pieceManager;
    protected Piece(){
        converter = new PositionToIndexConverter();
    }
    abstract public List<List<String>> expectedPaths(PieceManager pieceManager);
    abstract public PlayerEnum getPlayer();
    abstract public boolean isKilled();
    abstract public String getPosition();
    abstract public void setIsKilled(boolean isKilled);
    abstract public void setCanMove(boolean canMove);
    abstract public boolean getCanMove();
    abstract public void setPosition(String newPosition);
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
    public void setFirstMoveToFalse(){
        this.isFirstMove = false;
    }
    public boolean isFirstMove(){return isFirstMove;}
}
