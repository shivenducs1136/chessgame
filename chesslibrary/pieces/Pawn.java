package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class Pawn extends Piece{
    public boolean isFirstMove = true;
    public String position; 

    public Pawn( PlayerEnum playerEnum,String position) {
        this.player = playerEnum;
        this.position = position;
         isKilled =false;
         canMove = true;
    }
    
    public List<List<String>> ExpectedMove(){
       List<List<String>> moves = new ArrayList<>();
       PieceManager pm = new PieceManager(); 
       int i = pm.GetIindex(position);
       int j = pm.GetJindex(position);
        List<String> pathForward = new ArrayList<>();
        List<String> pathDiag = new ArrayList<>();
        int idx = i;


        if(player == PlayerEnum.White){
        if(isFirstMove){
            int count = 0;
                while(count<2){
                    idx++; 
                    if(pm.IsPositionEmptyInBoard(idx, j)){
                        pathForward.add(idx+""+j);
                    }
                    else{
                        break; 
                    }
                    count ++; 
                }
        }
        else{
            idx++;
            if(pm.IsPositionEmptyInBoard(idx,j)){
                pathForward.add(idx+""+j);
            }
        }
            int dj1 = j-1;
            int di = i+1; 
            int dj2 = j+1; 
            
            Piece piece1 = pm.GetPieceAtPosition(di+""+dj1); 
            Piece piece2 = pm.GetPieceAtPosition(di+""+dj2); 
            if(dj1>=0 && dj1<8){
                if( piece1 != null && piece1.getPlayer()!=player){
                    pathDiag.add(di+""+dj1);
                }
            }
            if(dj2>=0 && dj2<8){
                if( piece2 != null && piece2.getPlayer()!=player){
                    pathDiag.add(di+""+dj2);
                }
            }
            moves.add(pathForward);
            moves.add(pathDiag);
            return moves;
       }
       else{
        if(isFirstMove){
            int count = 0; 
            while(count<2){
                idx--; 
                if(pm.IsPositionEmptyInBoard(idx, j)){
                    pathForward.add(idx+""+j);
                }
                else{
                    break; 
                }
                count ++; 
            }
        }
        else{
            idx--; 
            if(pm.IsPositionEmptyInBoard(idx,j)){
                pathForward.add(idx+""+j);
            }
           
        }

        int dj1 = j-1;
        int di = i-1; 
        int dj2 = j+1; 
        Piece piece1 = pm.GetPieceAtPosition(di+""+dj1); 
        Piece piece2 = pm.GetPieceAtPosition(di+""+dj2); 
        if(dj1>=0 && dj1<8){
            if( piece1 != null && piece1.getPlayer()!=player){
                pathDiag.add(di+""+dj1);
            }
        }
        if(dj2>=0 && dj2<8){
            if( piece2 != null && piece2.getPlayer()!=player){
                pathDiag.add(di+""+dj2);
            }
        }
        moves.add(pathForward);
        moves.add(pathDiag);
        return moves;
       }
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
    public void FirstMoveDone(){
        isFirstMove= false;
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


