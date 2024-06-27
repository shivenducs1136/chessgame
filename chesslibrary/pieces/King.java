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
    private boolean IsCastleAllowed =false;
    private List<String> possibleCastleMoves = null;
    public King( PlayerEnum playerEnum,String position) {
        player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true;
    }

    public List<List<String>> expectedPaths(List<List<Piece>> board){
       PieceManager pm = new PieceManager();
        List<List<String>> moves = new ArrayList<>();
        List<String> updatedMoves = removePathsThatCanHarmKing(pm,getAllValidMoves(pm,board),board);
        moves.add(updatedMoves);
       return moves; 
    }
    private List<String> removePathsThatCanHarmKing(PieceManager pm, List<String> moves,List<List<Piece>> board) {
        List<String> updatedPath = new ArrayList<>();
        for(String expectedPosition:moves){
            if(checkIfExpectedPositionValid(pm,expectedPosition,board)){
                updatedPath.add(expectedPosition);
            }
        }
        return updatedPath;
    }

    private boolean checkIfExpectedPositionValid(PieceManager pm, String expectedPosition,List<List<Piece>> board) {
        List<List<Piece>> newBoard = new ArrayList<>(pm.getBoardClone(board));
        int i = pm.getIindex(expectedPosition);
        int j = pm.getJindex(expectedPosition);
        newBoard.get(pm.getIindex(position)).set(pm.getJindex(position),null);
        newBoard.get(i).set(j,this);
        return pm.isCheck(player, expectedPosition, newBoard) == null;
    }



    public List<String> getAllValidMoves(PieceManager pm,List<List<Piece>> board) {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1,1,-1,0,1,0,-1,-1};
        int[] jIndexs = new int[]{1,-1,-1,1,0,-1,0,1};
        int idx = pm.getIindex(position);
        int jdx = pm.getJindex(position);
        for(int x = 0; x<8;x++ ){
            int newIdx = idx+iIndexs[x];
            int newJdx = jdx+jIndexs[x];

            if(pm.isIndexSafe(newIdx, newJdx)){
                if((pm.getPieceAtPosition(newIdx+""+newJdx,board)==null))
                    moves.add(newIdx+""+newJdx);
                else{
                    if((pm.getPieceAtPosition(newIdx+""+newJdx,board).getPlayer()!=player)){
                        moves.add(newIdx+""+newJdx);
                    }
                }
            }
        }
        return moves;
    }

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
    public List<String> getCastleMoves(){
        return possibleCastleMoves;
    }

    public List<String> getCastleMovesIfPossible(List<List<Piece>> board) {
        List<String> moves = new ArrayList<>();

        if(player == PlayerEnum.Black){

            boolean isRook1FirstMove = false;
            boolean isRook2FirstMove = false;
            boolean isKingFirstMove = isFirstMove;
            boolean isKingLeftPositionEmpty = false;
            boolean isKingRightPositionEmpty = false;
            boolean isKingLeftPositionNoThreats = false;
            boolean isKingRightPositionNoThreats = false;

            PieceManager pm = new PieceManager();

            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pm.getPlayerPieces(player);
                boolean b = true;
                for (Piece p:pieces){
                    if((p instanceof Rook) && p.isFirstMove()){
                        if(b){
                            isRook1FirstMove = true;
                            b = false;
                        }
                        else isRook2FirstMove = true;
                    }
                }
                if(isRook1FirstMove){

                    String position1 = "71";
                    String position2 = "72";
                    Piece p = pm.getPieceAtPosition(position1,board);
                    Piece p2 = pm.getPieceAtPosition(position2,board);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(checkIfExpectedPositionValid(pm,position1,board) && checkIfExpectedPositionValid(pm,position2,board)){
                            isKingLeftPositionNoThreats = true;
                            // Castling is allowed here
                            moves.add(position1) ;
                        }
                    }

                }
                if(isRook2FirstMove){
                    String position1 = "74";
                    String position2 = "75";
                    String position3 = "76";
                    Piece p = pm.getPieceAtPosition(position1,board);
                    Piece p2 = pm.getPieceAtPosition(position2,board);
                    Piece p3 = pm.getPieceAtPosition(position3,board);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(checkIfExpectedPositionValid(pm,position1,board) && checkIfExpectedPositionValid(pm,position2,board) ){
                            isKingRightPositionNoThreats = true;
                            // Castling is allowed here
                            moves.add(position2) ;
                        }
                    }
                }
            }
        }
        else{
            boolean isRook1FirstMove = false;
            boolean isRook2FirstMove = false;
            boolean isKingFirstMove = isFirstMove;
            boolean isKingLeftPositionEmpty = false;
            boolean isKingRightPositionEmpty = false;
            boolean isKingLeftPositionNoThreats = false;
            boolean isKingRightPositionNoThreats = false;

            PieceManager pm = new PieceManager();

            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pm.getPlayerPieces(player);
                boolean b = true;
                for (Piece p:pieces){
                    if((p instanceof Rook) && p.isFirstMove()){
                        if(b){
                            isRook1FirstMove = true;
                            b = false;
                        }
                        else isRook2FirstMove = true;
                    }
                }
                if(isRook1FirstMove){

                    String position1 = "01";
                    String position2 = "02";
                    Piece p = pm.getPieceAtPosition(position1,board);
                    Piece p2 = pm.getPieceAtPosition(position2,board);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(checkIfExpectedPositionValid(pm,position1,board) && checkIfExpectedPositionValid(pm,position2,board)){
                            isKingLeftPositionNoThreats = true;
                            // Castling is allowed here
                            moves.add(position1) ;
                        }
                    }

                }
                if(isRook2FirstMove){
                    String position1 = "04";
                    String position2 = "05";
                    String position3 = "06";
                    Piece p = pm.getPieceAtPosition(position1,board);
                    Piece p2 = pm.getPieceAtPosition(position2,board);
                    Piece p3 = pm.getPieceAtPosition(position3,board);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(checkIfExpectedPositionValid(pm,position1,board) && checkIfExpectedPositionValid(pm,position2,board) ){
                            isKingRightPositionNoThreats = true;
                            // Castling is allowed here
                            moves.add(position2) ;
                        }
                    }
                }
            }
        }
        if(moves.isEmpty()){
            IsCastleAllowed = false;
        }
        else{
            IsCastleAllowed = true;
        }
        possibleCastleMoves=moves;
        return moves;
    }
}
