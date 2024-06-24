package pieces;

import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;

public class Rook extends Piece{

    final PieceEnum pieceEnum = PieceEnum.Rook;
    PlayerEnum playerEnum;
    boolean isKilled =false;
    boolean canMove = true;
    String position; 

    public Rook( PlayerEnum playerEnum,String position) {
        this.playerEnum = playerEnum;
        this.position = position; 
    }
    
    public List<String> ExpectedMove(){
        List<String> moves =  new ArrayList<String>(); 
        PieceManager pm = new PieceManager(); 
        int idx = pm.GetIindex(position); 
        int jdx = pm.GetJindex(position);
        int i = idx; 
        int j = jdx; 

        // keeping j constant and  decreasing i
        i--; 
        while(
            (i>=0 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (i>=0 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != playerEnum))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                i--; 
             }
        // reset i and j 
        i = idx; j =jdx; 
       
        // keeping j constant and  increasing i
        i++; 
        while(
            (i<8 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (i<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != playerEnum))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                i++; 
             }
        // reset i and j 
        i = idx; j =jdx; 
         
        // keeping i constant and  decreasing j
        j--; 
        while(
            (j>=0 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (j>=0 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != playerEnum))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                j--; 
             }
        // reset i and j 
        i = idx; j =jdx; 

        // keeping i constant and  increasing j
        j++; 
        while(
            ( j<8 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            ( j<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != playerEnum))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                j++; 
             }
        return moves;
    }
    public PieceEnum getPieceEnum() {
        return pieceEnum;
    }
    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
    public void setPlayerEnum(PlayerEnum playerEnum) {
        this.playerEnum = playerEnum;
    } 

    public boolean getIsKilled() {
        return isKilled;
    }
    public boolean getCanMove() {
        return canMove;
    }
    public String getPosition() {
        return position;
    }
    public void setKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public PlayerEnum getPlayer() {
        return playerEnum;
    }

    @Override
    public PieceEnum getPiece() {
        return pieceEnum;
    }

    @Override
    public boolean isKilled() {
        return isKilled;
    }

    @Override
    public void setIsKilled(boolean isKilled) {
       this.isKilled = isKilled;
    }
}
