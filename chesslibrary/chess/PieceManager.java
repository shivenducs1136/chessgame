package chess;

import abstracts.Piece;
import enums.PieceEnum;
import enums.PlayerEnum;
import pieces.King;

import java.util.*;

public class PieceManager {
    private static final List<Piece> whitePieces = new ArrayList<>();
    private static final List<Piece> blackPieces = new ArrayList<>();
    public Piece GetPieceAtPosition(String position){
        int i = position.charAt(0) - '0'; 
        int j = position.charAt(1) - '0'; 
        if(i<0 || i>7) return null; 
        if(j<0 || j>7) return null;
        return ChessGame.board.get(i).get(j); 
    }
    public int GetIindex(String position){
        return position.charAt(0) - '0';
    }
    public int GetJindex(String position){
        return position.charAt(1) - '0';
    }
    public boolean IsPositionEmptyInBoard(int i,int j){
        return ChessGame.board.get(i).get(j) == null;
    }
    public boolean IsIndexSafe(int i, int j){
        return i >= 0 && i < 8 && j >= 0 && j < 8;
    }
    public void SeggregatePiece(Piece pc){
        if(pc.getPlayer() == PlayerEnum.White){
            whitePieces.add(pc);
        }
        else{
            blackPieces.add(pc);
            }
    }
    public void SeggregatePiece(List<Piece> pcs){
        for (Piece pc : pcs) {
           SeggregatePiece(pc);
        }
    }
    public List<Piece> GetPlayerPieces(PlayerEnum playerColor){
        if(playerColor == PlayerEnum.White){
            return whitePieces;
        }
        else if(playerColor == PlayerEnum.Black){
            return blackPieces;
        }
        return null;
    }
    public void RestrictPlayersOnCheck(PlayerEnum playerColor) {
        List<Piece> pieces = GetPlayerPieces(playerColor);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(false);
            }
        }

    }
    public List<String> GetPointsCommonInBothKing(){
        King whiteKing = GetKing(whitePieces);
        King blackKing = GetKing(blackPieces);
        List<String> whiteKingMoves = whiteKing.GetAllValidMoves(this);
        List<String> blackKingMoves = blackKing.GetAllValidMoves(this);
        Set<String> commonMoves = new HashSet<>(whiteKingMoves);
        commonMoves.retainAll(blackKingMoves);
        return new ArrayList<>(commonMoves);
    }
    public King GetKing(List<Piece> pieces){
        for(Piece p:pieces){
            if (p instanceof King){
                return (King)p;
            }
        }
        return null;
    }
    public PlayerEnum GetOppositePlayerColor(PlayerEnum playerEnum){
        if(playerEnum == PlayerEnum.Black){
            return PlayerEnum.White;
        }
        return PlayerEnum.Black;
    }
}
