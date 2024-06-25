package pieces;


import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class Bishop extends Piece{

    public Bishop(PlayerEnum playerEnum,String position) {
        isKilled =false;
        canMove = true;
        player = playerEnum;
        this.position = position;
    }
    public List<List<String>> ExpectedPaths(List<List<Piece>> board){
        List<List<String>> moves =  new ArrayList<>();
        PieceManager pm = new PieceManager();
        int[] iIndexes = new int[]{-1,1,1,-1};
        int[] jIndexes = new int[]{-1,1,-1,1};
        // decreasing i and decreasing j -> -1,-1
        // increasing i and increasing j -> 1,1
        // increasing i and decreasing j -> 1,-1
        // decreasing i and increasing j -> -1,1
        for(int x = 0; x<4;x++){
            moves.add(GetValidMovesInDirection(pm,iIndexes[x],jIndexes[x],board));
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

