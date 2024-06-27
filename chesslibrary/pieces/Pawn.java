package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class Pawn extends Piece{

    public List<String> upgradableMoves = new ArrayList<>();
    public Pawn( PlayerEnum playerEnum,String position) {
        this.player = playerEnum;
        this.position = position;
         isKilled =false;
         canMove = true;
    }
    
    public List<List<String>> expectedPaths(PieceManager pieceManager){
        this.pieceManager =pieceManager;
        List<List<String>> moves = new ArrayList<>();
       int i = converter.getIindex(position);
       int j = converter.getJindex(position);
        List<String> pathForward = new ArrayList<>();
        List<String> pathDiag = new ArrayList<>();
        int idx = i;


        if(player == PlayerEnum.White){
        if(isFirstMove){
            int count = 0;
                while(count<2){
                    idx++; 
                    if(pieceManager.isPositionEmptyInBoard(idx, j)){
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
            if(pieceManager.isPositionEmptyInBoard(idx,j)){
                pathForward.add(idx+""+j);
            }
        }
            int dj1 = j-1;
            int di = i+1; 
            int dj2 = j+1; 
            
            Piece piece1 = pieceManager.getPieceAtPosition(di,dj1);
            Piece piece2 = pieceManager.getPieceAtPosition(di,dj2);
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
            for(String s:pathForward){
                int newI = converter.getIindex(s);
                if(newI == 7){
                    upgradableMoves.add(s);
                }
            }
            for(String s:pathDiag){
                int newI = converter.getIindex(s);
                if(newI == 7){
                    upgradableMoves.add(s);
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
                if(pieceManager.isPositionEmptyInBoard(idx, j)){
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
            if(pieceManager.isPositionEmptyInBoard(idx,j)){
                pathForward.add(idx+""+j);
            }
           
        }

        int dj1 = j-1;
        int di = i-1; 
        int dj2 = j+1; 
        Piece piece1 = pieceManager.getPieceAtPosition(di,dj1);
        Piece piece2 = pieceManager.getPieceAtPosition(di,dj2);
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
            for(String s:pathForward){
                int newI = converter.getIindex(s);
                if(newI == 0){
                    upgradableMoves.add(s);
                }
            }
            for(String s:pathDiag){
                int newI = converter.getIindex(s);
                if(newI == 0){
                    upgradableMoves.add(s);
                }
            }
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
    public void firstMoveDone(){
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


