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


    public Rook( PlayerEnum playerEnum,String position) {
        this.player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true; }
    
    public List<List<String>> expectedPaths(PieceManager pieceManager){
        this.pieceManager =pieceManager;
        List<List<String>> moves = new ArrayList<>();
        int[] iIndexes = new int[]{-1,1,0,0};
        int[] jIndexes = new int[]{0,0,-1,1};
        // keeping decreasing i and j constant   -> -1,0
        // keeping increasing i and j constant   -> 1,0
        // keeping i constant and  decreasing j  -> 0,-1
        // keeping i constant and  increasing j  -> 0,1
        for(int x = 0; x<4;x++){
            moves.add(getValidMovesInDirection(iIndexes[x],jIndexes[x]));
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
