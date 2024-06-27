package chess;

import abstracts.Piece;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;
import io.PositionToIndexConverter;
import pieces.*;

import java.util.*;

public class PieceManager {
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final PositionToIndexConverter converter;
    private final List<List<Piece>> board;
    public PieceManager(List<List<Piece>> board){
        converter = new PositionToIndexConverter();
        this.board = board;
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
    }
    // Get a piece
    public Piece getPieceAtPosition(String position){
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        return getPieceAtPosition(i,j);
    }
    public Piece getPieceAtPosition(int i,int j){
        if(converter.isIndexSafe(i,j)){
            return board.get(i).get(j);
        }
        return null;
    }
    public boolean isPositionEmptyInBoard(int i,int j){
        return board.get(i).get(j) == null;
    }
    public void seggregatePiece(Piece pc){
        if(pc == null) return;
        if(pc.getPlayer() == PlayerEnum.White){
            whitePieces.add(pc);
        }
        else if(pc.getPlayer() == PlayerEnum.Black){
            blackPieces.add(pc);
        }
    }
    public void seggregatePiece(List<Piece> pcs){
        for (Piece pc : pcs) {
           seggregatePiece(pc);
        }
    }
    public List<Piece> getPlayerPieces(PlayerEnum playerColor){
        if(playerColor == PlayerEnum.White){
            return whitePieces;
        }
        else if(playerColor == PlayerEnum.Black){
            return blackPieces;
        }
        return null;
    }
    public void restrictPiecesOnCheck(PlayerEnum playerColor) {
        List<Piece> pieces = getPlayerPieces(playerColor);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(false);
            }
        }

    }
    public List<String> getPointsCommonInBothKing(){
        King whiteKing = getKing(PlayerEnum.White);
        King blackKing = getKing(PlayerEnum.Black);
        List<String> whiteKingMoves = whiteKing.getAllValidMoves(this);
        List<String> blackKingMoves = blackKing.getAllValidMoves(this);
        Set<String> commonMoves = new HashSet<>(whiteKingMoves);
        commonMoves.retainAll(blackKingMoves);
        return new ArrayList<>(commonMoves);
    }
    public King getKing(PlayerEnum playerEnum){
        List<Piece> pieces = getPlayerPieces(playerEnum);
        for(Piece p:pieces){
            if (p instanceof King){
                return (King)p;
            }
        }
        return null;
    }
    public PlayerEnum getOppositePlayerColor(PlayerEnum playerEnum){
        if(playerEnum == PlayerEnum.Black){
            return PlayerEnum.White;
        }
        return PlayerEnum.Black;
    }
    public boolean allowPiecesThatCanResolveCheck(List<String> checkPiecePath,PlayerEnum playerColor, ChessGame cg) {
        List<Piece> pieces = getPlayerPieces(playerColor);
        boolean allowed = false;
        for(Piece p:pieces){
            List<List<String>> expectedPaths = p.expectedPaths(this);
            for (List<String> path:expectedPaths){
                for(String move:path){
                    if(checkPiecePath.contains(move)){
                        p.setCanMove(true);
                        allowed = true;
                    }
                }
            }
        }
       return allowed;
    }
    public boolean isPlayerHaveAnyLegalMove(PlayerEnum playerChance,ChessGame cg) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for (Piece p:pieces){
            List<List<String>> expectedPaths = p.expectedPaths(this);
            for (List<String> path:expectedPaths){
                if(!path.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }
    public void allowAllPiecesToMove(PlayerEnum playerChance) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(true);
            }
        }
    }
    public List<String> isCheck(PlayerEnum playerColor,String currentKingPosition){
        List<Piece> oppositePlayerPieces = getPlayerPieces(getOppositePlayerColor(playerColor));
        List<String> checkPiecePath = new ArrayList<>();
        for (Piece oppPlayerPiece : oppositePlayerPieces) {
            if(oppPlayerPiece instanceof King k){
                List<String> commonPoints = getPointsCommonInBothKing();
                if(commonPoints.contains(currentKingPosition)){
                    checkPiecePath.add(currentKingPosition);
                }
            }
            else{
                List<List<String>> playerMoves = oppPlayerPiece.expectedPaths(this);
                for(List<String> path:playerMoves){
                    if(path.contains(currentKingPosition)){
                        checkPiecePath = path;
                        checkPiecePath.add(oppPlayerPiece.getPosition());
                        return checkPiecePath;
                    }
                }
            }
        }
        return null;
    }
    public List<String> isCheck(PlayerEnum playerColor){
        String currentKingPosition = getKing(playerColor).getPosition();
        return isCheck(playerColor,currentKingPosition);
    }

    public void removePiece(Piece p) {
        List<Piece> pieces = getPlayerPieces(p.getPlayer());
        pieces.remove(p);
    }

    public void setPieceInBoard(String position,Piece piece){
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        board.get(i).set(j,piece);
    }
    public void setPieceInBoard(int i,int j,Piece piece){
        if(converter.isIndexSafe(i,j)){
            board.get(i).set(j,piece);
        }
        else throw new IndexOutOfBoundsException();
    }
    public List<List<Piece>> getBoardClone() {
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
    public void updatePieceList(){
        for(List<Piece> row:board){
            seggregatePiece(row);
        }
    }
}
