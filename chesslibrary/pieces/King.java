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
        List<List<Piece>> newBoard = new ArrayList<>(pm.GetBoardClone(board));
        int i = pm.GetIindex(expectedPosition);
        int j = pm.GetJindex(expectedPosition);
        newBoard.get(pm.GetIindex(position)).set(pm.GetJindex(position),null);
        newBoard.get(i).set(j,this);
        return pm.IsCheck(player, expectedPosition, newBoard) == null;
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
    public List<String> GetCastleMoves(){
        return possibleCastleMoves;
    }

    public List<String> GetCastleMovesIfPossible(List<List<Piece>> board) {
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
                List<Piece> pieces = pm.GetPlayerPieces(player);
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
                    Piece p = pm.GetPieceAtPosition(position1,board);
                    Piece p2 = pm.GetPieceAtPosition(position2,board);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(CheckIfExpectedPositionValid(pm,position1,board) && CheckIfExpectedPositionValid(pm,position2,board)){
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
                    Piece p = pm.GetPieceAtPosition(position1,board);
                    Piece p2 = pm.GetPieceAtPosition(position2,board);
                    Piece p3 = pm.GetPieceAtPosition(position3,board);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(CheckIfExpectedPositionValid(pm,position1,board) && CheckIfExpectedPositionValid(pm,position2,board) ){
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
                List<Piece> pieces = pm.GetPlayerPieces(player);
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
                    Piece p = pm.GetPieceAtPosition(position1,board);
                    Piece p2 = pm.GetPieceAtPosition(position2,board);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(CheckIfExpectedPositionValid(pm,position1,board) && CheckIfExpectedPositionValid(pm,position2,board)){
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
                    Piece p = pm.GetPieceAtPosition(position1,board);
                    Piece p2 = pm.GetPieceAtPosition(position2,board);
                    Piece p3 = pm.GetPieceAtPosition(position3,board);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(CheckIfExpectedPositionValid(pm,position1,board) && CheckIfExpectedPositionValid(pm,position2,board) ){
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
