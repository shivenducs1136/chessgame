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
    
    public List<List<String>> ExpectedPaths(List<List<Piece>> board){
        List<List<String>> moves =  new ArrayList<>();
        PieceManager pm = new PieceManager();
        int[] iIndexes = new int[]{-1,1,0,0,-1,1,1,-1};
        int[] jIndexes = new int[]{0,0,-1,1,-1,1,-1,1};

        // keeping decreasing i and j constant   -> -1,0
        // keeping increasing i and j constant   -> 1,0
        // keeping i constant and  decreasing j  -> 0,-1
        // keeping i constant and  increasing j  -> 0,1
        // decreasing i and decreasing j -> -1,-1
        // increasing i and increasing j -> 1,1
        // increasing i and decreasing j -> 1,-1
        // decreasing i and increasing j -> -1,1

        for(int x = 0; x<8;x++){
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

