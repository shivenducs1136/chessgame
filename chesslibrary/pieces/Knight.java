package pieces;



import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class  Knight extends Piece{

    String position;

    public Knight( PlayerEnum playerEnum,String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true; }
    
    public List<List<String>> expectedPaths(PieceManager pieceManager){
        this.pieceManager =pieceManager;
        List<List<String>> moves = new ArrayList<>();
       int[] iIndexs = new int[]{1,-1,-2,-2,-1,1,2,2};
       int[] jIndexs = new int[]{-2,-2,-1,1,2,2,1,-1};
       int idx = converter.getIindex(position);
       int jdx = converter.getJindex(position);
       for(int x = 0; x<8;x++ ){
           List<String> path = new ArrayList<>();
            int newIdx = idx+iIndexs[x];
            int newJdx = jdx+jIndexs[x]; 

            if(converter.isIndexSafe(newIdx, newJdx)){
                if((pieceManager.getPieceAtPosition(newIdx,newJdx)==null))
                path.add(newIdx+""+newJdx);
                else{
                    if((pieceManager.getPieceAtPosition(newIdx,newJdx).getPlayer()!=player)){
                        path.add(newIdx+""+newJdx);
                    }
                }
            }
            moves.add(path);
       }
       return moves; 
    }

    public PlayerEnum getPlayerEnum() {
        return player;
    }
    public boolean getCanMove() {
        return canMove;
    }
    public String getPosition() {
        return position;
    }
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public PlayerEnum getPlayer() {
        return player;
    }
    public boolean isKilled() {
        return isKilled;
    }
    public void setIsKilled(boolean isKilled) {
       this.isKilled = isKilled;
    }

}

