package abstracts; 

import java.util.ArrayList;
import java.util.List;

import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public abstract class Piece {
    protected PlayerEnum player;
    protected boolean isKilled;
    protected boolean canMove;
    protected String position;
    protected boolean isFirstMove = true;
    abstract public List<List<String>> expectedPaths(List<List<Piece>> board);
    abstract public PlayerEnum getPlayer();
    abstract public boolean isKilled();
    abstract public String getPosition();
    abstract public void setIsKilled(boolean isKilled);
    abstract public void setCanMove(boolean canMove);
    abstract public boolean getCanMove();
    abstract public void setPosition(String newPosition);
    protected List<String> getValidMovesInDirection(PieceManager pm, int i_direction,int j_direction, List<List<Piece>> board){
        List<String> moves = new ArrayList<>();
        int idx = pm.getIindex(position);
        int jdx = pm.getJindex(position);
        int i = idx + i_direction;
        int j = jdx + j_direction;
        while(
                (pm.isIndexSafe(i,j) && (pm.getPieceAtPosition(i +""+j,board) == null)) ||
                        pm.isIndexSafe(i,j) && (pm.getPieceAtPosition(i +""+j,board).getPlayer() != player)
        ){

            moves.add(i+""+j);
            if(pm.getPieceAtPosition(i +""+j,board) != null){
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
