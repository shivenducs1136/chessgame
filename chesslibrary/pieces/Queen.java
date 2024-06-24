package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class Queen extends Piece{

    public Queen( PlayerEnum playerEnum,String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true;
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
            (i>=0 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
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
            (i<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
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
            (j>=0 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
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
            ( j<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                j++; 
             }
                 // reset i and j 
        i = idx; j =jdx; 

              // decreasing i decreasing j
        i--; j--; 
        while(
            (i>=0 && j>=0 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (i>=0 && j>=0 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                i--; 
                j--; 
             }
        // reset i and j 
        i = idx; j =jdx; 
       
        // increasing i increasing j
        i++; 
        j++; 
        while(
            (i<8 && j<8 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (i<8 && j<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                i++; 
                j++; 
             }
        // reset i and j 
        i = idx; j =jdx; 
         
        // increasing i  decreasing j
        j--; 
        i++; 
        while(
            (j>=0 && i<8 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            (j>=0 && i<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                j--; 
                i++; 
             }
        // reset i and j 
        i = idx; j =jdx; 

        // decreasing i  increasing j
        j++; 
        i--; 
        while(
            ( i>=0 && j<8 && (pm.GetPieceAtPosition(i +""+j) == null)) ||
            ( i>=0 && j<8 && (pm.GetPieceAtPosition(i +""+j).getPlayer() != player))
             ){
                moves.add(i+""+j); 
                if(pm.GetPieceAtPosition(i +""+j) != null){
                    break; 
                }
                j++; 
                i--; 
             }

        return moves; 
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
    @Override
    public PlayerEnum getPlayer() {
        return player;
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

