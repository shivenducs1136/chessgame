package chess;

import abstracts.Piece;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;
import pieces.*;

import java.util.*;

public class PieceManager {
    private static final List<Piece> whitePieces = new ArrayList<>();
    private static final List<Piece> blackPieces = new ArrayList<>();
    public Piece getPieceAtPosition(String position,List<List<Piece>> board){
        int i = position.charAt(0) - '0'; 
        int j = position.charAt(1) - '0'; 
        if(i<0 || i>7) return null; 
        if(j<0 || j>7) return null;
        return board.get(i).get(j);
    }
    public int getIindex(String position){
        return position.charAt(0) - '0';
    }
    public int getJindex(String position){
        return position.charAt(1) - '0';
    }
    public boolean isPositionEmptyInBoard(int i,int j,List<List<Piece>> board){
        return board.get(i).get(j) == null;
    }
    public boolean isIndexSafe(int i, int j){
        return i >= 0 && i < 8 && j >= 0 && j < 8;
    }
    public void seggregatePiece(Piece pc){
        if(pc.getPlayer() == PlayerEnum.White){
            whitePieces.add(pc);
        }
        else{
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
    public List<String> getPointsCommonInBothKing(List<List<Piece>> board){
        King whiteKing = getKing(whitePieces);
        King blackKing = getKing(blackPieces);
        List<String> whiteKingMoves = whiteKing.getAllValidMoves(this,board);
        List<String> blackKingMoves = blackKing.getAllValidMoves(this,board);
        Set<String> commonMoves = new HashSet<>(whiteKingMoves);
        commonMoves.retainAll(blackKingMoves);
        return new ArrayList<>(commonMoves);
    }
    public King getKing(List<Piece> pieces){
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
            List<List<String>> expectedPaths = p.expectedPaths(cg.getBoard());
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
            List<List<String>> expectedPaths = p.expectedPaths(cg.getBoard());
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

    public List<String> isCheck(PlayerEnum playerColor,String currentKingPosition,List<List<Piece>> board){
        List<Piece> oppositePlayerPieces = getPlayerPieces(getOppositePlayerColor(playerColor));
        List<String> checkPiecePath = new ArrayList<>();
        for (Piece oppPlayerPiece : oppositePlayerPieces) {
            if(oppPlayerPiece instanceof King k){
                List<String> commonPoints = getPointsCommonInBothKing(board);
                if(commonPoints.contains(currentKingPosition)){
                    checkPiecePath.add(currentKingPosition);
                }
            }
            else{
                List<List<String>> playerMoves = oppPlayerPiece.expectedPaths(board);
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
    public List<String> isCheck(PlayerEnum playerColor,List<List<Piece>> board){
        String currentKingPosition = getKing(getPlayerPieces(playerColor)).getPosition();
        return isCheck(playerColor,currentKingPosition,board);
    }
    public void createPieces(List<List<Piece>> board) {
        // white pieces
        List<Piece> whitePieces = getWhitePieces();
        seggregatePiece(whitePieces);
        board.add(whitePieces);
        List<Piece> whitePawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            whitePawns.add(new Pawn(PlayerEnum.White, "1"+i));
        }
        seggregatePiece(whitePawns);
        board.add(whitePawns);
        // empty pieces
        for(int j = 2; j<6 ; j++){
            List<Piece> emptyPlaces = new ArrayList<>();
            for(int i = 0 ; i< 8; i++){
                emptyPlaces.add(null);
            }
            board.add(emptyPlaces);
        }

        // black pawn pieces
        List<Piece> blackPawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            blackPawns.add(new Pawn(PlayerEnum.Black, "6"+i));
        }
        seggregatePiece(blackPawns);
        board.add(blackPawns);

        // Black pieces
        List<Piece> blackPieces = getBlackPieceList();
        board.add(blackPieces);
        seggregatePiece(blackPieces);
    }
    private static List<Piece> getBlackPieceList() {
        Piece blackRook1 = new Rook(PlayerEnum.Black, "70");
        Piece blackRook2 = new Rook(PlayerEnum.Black, "77");
        Piece blackKnight1 = new Knight(PlayerEnum.Black, "71");
        Piece blackKnight2 = new Knight(PlayerEnum.Black, "76");
        Piece blackBishop1 = new Bishop(PlayerEnum.Black, "72");
        Piece blackBishop2 = new Bishop(PlayerEnum.Black, "75");
        Piece blackKing = new King(PlayerEnum.Black, "73");
        Piece blackQueen = new Queen(PlayerEnum.Black, "74");
        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(blackRook1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackKing);
        blackPieces.add(blackQueen);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackRook2);
        return blackPieces;
    }
    private static List<Piece> getWhitePieces() {
        Piece whiteRook1 = new Rook(PlayerEnum.White, "00");
        Piece whiteRook2 = new Rook(PlayerEnum.White, "07");
        Piece whiteKnight1 = new Knight(PlayerEnum.White, "01");
        Piece whiteKnight2 = new Knight(PlayerEnum.White, "06");
        Piece whiteBishop1 = new Bishop(PlayerEnum.White, "02");
        Piece whiteBishop2 = new Bishop(PlayerEnum.White, "05");
        Piece whiteKing = new King(PlayerEnum.White, "03");
        Piece whiteQueen = new Queen(PlayerEnum.White, "04");
        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(whiteRook1);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteRook2);
        return whitePieces;
    }

    public void removeKilledPiece(Piece p) {
        List<Piece> pieces = getPlayerPieces(p.getPlayer());
        pieces.remove(p);
    }
    public List<List<Piece>> getBoardClone(List<List<Piece>> board) {
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
}
