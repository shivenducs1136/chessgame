package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class King extends Piece{
    public King( PlayerEnum playerEnum,String position) {
        player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true;
    }
    
    public List<List<String>> ExpectedPaths(List<List<Piece>> board){
       PieceManager pm = new PieceManager();
        List<List<String>> moves = new ArrayList<>();
        List<String> updatedMoves = RemovePathsThatCanHarmKing(pm,GetAllValidMoves(pm,board),board);
        moves.add(updatedMoves);
       return moves; 
    }
    private List<String> RemovePathsThatCanHarmKing(PieceManager pm, List<String> moves,List<List<Piece>> board) {
        List<String> updatedPath = new ArrayList<>();
        for(String expectedPosition:moves){
            if(CheckIfExpectedPositionValid(pm,expectedPosition,board)){
                updatedPath.add(expectedPosition);
            }
        }
        return updatedPath;
    }

    private boolean CheckIfExpectedPositionValid(PieceManager pm, String expectedPosition,List<List<Piece>> board) {
        List<List<Piece>> newBoard = new ArrayList<>(GetNewBoard(board));
        int i = pm.GetIindex(expectedPosition);
        int j = pm.GetJindex(expectedPosition);
        newBoard.get(i).set(j,this);
        return pm.IsCheck(player, expectedPosition, newBoard) == null;
    }

    private List<List<Piece>> GetNewBoard(List<List<Piece>> board) {
        List<List<Piece>> newBoard = new ArrayList<>();
        for(List<Piece> row:board){
            List<Piece> list = new ArrayList<>();
            for(Piece p : row){
                list.add(p);
            }
            newBoard.add(list);
        }
        return newBoard;
    }

    public List<String> GetAllValidMoves(PieceManager pm,List<List<Piece>> board) {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1,1,-1,0,1,0,-1,-1};
        int[] jIndexs = new int[]{1,-1,-1,1,0,-1,0,1};
        int idx = pm.GetIindex(position);
        int jdx = pm.GetJindex(position);
        for(int x = 0; x<8;x++ ){
            int newIdx = idx+iIndexs[x];
            int newJdx = jdx+jIndexs[x];

            if(pm.IsIndexSafe(newIdx, newJdx)){
                if((pm.GetPieceAtPosition(newIdx+""+newJdx,board)==null))
                    moves.add(newIdx+""+newJdx);
                else{
                    if((pm.GetPieceAtPosition(newIdx+""+newJdx,board).getPlayer()!=player)){
                        moves.add(newIdx+""+newJdx);
                    }
                }
            }
        }
        return moves;
    }

//    private List<String> GetOppositePlayerPieceMovesThatCanHarmKing(PieceManager pm) {
//        List<String> moves = new ArrayList<>();
//        List<Piece> oppositeColorPieces = pm.GetPlayerPieces(pm.GetOppositePlayerColor(player));
//
//        for (Piece piece : oppositeColorPieces) {
//            List<String> pcMoves;
//            if(piece instanceof King){
//                pcMoves = pm.GetPointsCommonInBothKing(board);
//
//            }
//            else{
//                var expectedMoves = piece.ExpectedPaths();
//                pcMoves = new ArrayList<>();
//                for(List<String> exMoves:expectedMoves){
//                    pcMoves.addAll(exMoves);
//                }
//            }
//            for (String mv : pcMoves) {
//                if(!moves.contains(mv)){
//                    moves.add(mv);
//                }
//            }
//        }
//        return moves;
//    }


    public boolean getCanMove() {
        return canMove;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
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
