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

    abstract public List<List<String>> ExpectedMove();
    abstract public PlayerEnum getPlayer();
    abstract public boolean isKilled();
    abstract public String getPosition();
    abstract public void setIsKilled(boolean isKilled);
    abstract public void setCanMove(boolean canMove);
    abstract public boolean getCanMove();
    abstract public void setPosition(String newPosition);
    protected List<String> GetValidMovesInDirection(PieceManager pm, int i_direction,int j_direction){
        List<String> moves = new ArrayList<>();
        int idx = pm.GetIindex(position);
        int jdx = pm.GetJindex(position);
        int i = idx + i_direction;
        int j = jdx + j_direction;
        while(
                (pm.IsIndexSafe(i,j) && (pm.GetPieceAtPosition(i +""+j) == null)) ||
                        (pm.IsIndexSafe(i,j) && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
            ){

            moves.add(i+""+j);
            if(pm.GetPieceAtPosition(i +""+j) != null){
                break;
            }
            i+=i_direction;
            j+=j_direction;

        }
        return moves;
    }
}
