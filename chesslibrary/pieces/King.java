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
    private boolean isCastleAllowed =false;
    private List<String> possibleCastleMoves = null;
    public King( PlayerEnum playerEnum,String position) {
        player = playerEnum;
        this.position = position;
        isKilled =false;
        canMove = true;
    }

    public List<List<String>> expectedPaths(PieceManager pieceManager){
        this.pieceManager =pieceManager;
        List<List<String>> moves = new ArrayList<>();
        List<String> updatedMoves = removePathsThatCanHarmKing(getAllValidMoves(pieceManager));
        moves.add(updatedMoves);
       return moves; 
    }
    private List<String> removePathsThatCanHarmKing(List<String> moves) {
        List<String> updatedPath = new ArrayList<>();
        for(String expectedPosition:moves){
            if(checkIfExpectedPositionValid(expectedPosition)){
                updatedPath.add(expectedPosition);
            }
        }
        return updatedPath;
    }

    private boolean checkIfExpectedPositionValid(String expectedPosition) {
        List<List<Piece>> newBoard = pieceManager.getBoardClone();
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        newBoard.get(i).set(j,null);
        PieceManager newPm = new PieceManager(newBoard);
        newBoard.get(converter.getIindex(expectedPosition)).set(converter.getJindex(expectedPosition),new King(player,expectedPosition));
        newPm.updatePieceList();
        return newPm.isCheck(player, expectedPosition) == null;
    }
    public List<String> getAllValidMoves(PieceManager pieceManager) {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1,1,-1,0,1,0,-1,-1};
        int[] jIndexs = new int[]{1,-1,-1,1,0,-1,0,1};
        int idx = converter.getIindex(position);
        int jdx = converter.getJindex(position);
        for(int x = 0; x<8;x++ ){
            int newIdx = idx+iIndexs[x];
            int newJdx = jdx+jIndexs[x];

            if(converter.isIndexSafe(newIdx, newJdx)){
                if((pieceManager.getPieceAtPosition(newIdx,newJdx)==null))
                    moves.add(newIdx+""+newJdx);
                else{
                    if((pieceManager.getPieceAtPosition(newIdx,newJdx).getPlayer()!=player)){
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


            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pieceManager.getPlayerPieces(player);
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2)){
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    Piece p3 = pieceManager.getPieceAtPosition(position3);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2) ){
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


            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pieceManager.getPlayerPieces(player);
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    if(p == null && p2 == null){
                        isKingLeftPositionEmpty = true;
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2)){
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    Piece p3 = pieceManager.getPieceAtPosition(position3);

                    if(p == null && p2 == null && p3 == null){
                        isKingRightPositionEmpty = true;
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2) ){
                            isKingRightPositionNoThreats = true;
                            // Castling is allowed here
                            moves.add(position2) ;
                        }
                    }
                }
            }
        }
        if(moves.isEmpty()){
            isCastleAllowed = false;
        }
        else{
            isCastleAllowed = true;
        }
        possibleCastleMoves=moves;
        return moves;
    }
}
