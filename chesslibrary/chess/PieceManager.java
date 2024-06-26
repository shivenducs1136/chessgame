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
    public Piece GetPieceAtPosition(String position,List<List<Piece>> board){
        int i = position.charAt(0) - '0'; 
        int j = position.charAt(1) - '0'; 
        if(i<0 || i>7) return null; 
        if(j<0 || j>7) return null;
        return board.get(i).get(j);
    }
    public int GetIindex(String position){
        return position.charAt(0) - '0';
    }
    public int GetJindex(String position){
        return position.charAt(1) - '0';
    }
    public boolean IsPositionEmptyInBoard(int i,int j,List<List<Piece>> board){
        return board.get(i).get(j) == null;
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
    public void RestrictPiecesOnCheck(PlayerEnum playerColor) {
        List<Piece> pieces = GetPlayerPieces(playerColor);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(false);
            }
        }

    }
    public List<String> GetPointsCommonInBothKing(List<List<Piece>> board){
        King whiteKing = GetKing(whitePieces);
        King blackKing = GetKing(blackPieces);
        List<String> whiteKingMoves = whiteKing.GetAllValidMoves(this,board);
        List<String> blackKingMoves = blackKing.GetAllValidMoves(this,board);
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
    public boolean AllowPiecesThatCanResolveCheck(List<String> checkPiecePath,PlayerEnum playerColor, ChessGame cg) {
        List<Piece> pieces = GetPlayerPieces(playerColor);
        boolean allowed = false;
        for(Piece p:pieces){
            List<List<String>> expectedPaths = p.ExpectedPaths(cg.GetBoard());
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
    public boolean IsPlayerHaveAnyLegalMove(PlayerEnum playerChance,ChessGame cg) {
        List<Piece> pieces = GetPlayerPieces(playerChance);
        for (Piece p:pieces){
            List<List<String>> expectedPaths = p.ExpectedPaths(cg.GetBoard());
            for (List<String> path:expectedPaths){
                if(!path.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }
    public void AllowAllPiecesToMove(PlayerEnum playerChance) {
        List<Piece> pieces = GetPlayerPieces(playerChance);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(true);
            }
        }
    }

    public List<String> IsCheck(PlayerEnum playerColor,String currentKingPosition,List<List<Piece>> board){
        List<Piece> oppositePlayerPieces = GetPlayerPieces(GetOppositePlayerColor(playerColor));
        List<String> checkPiecePath = new ArrayList<>();
        for (Piece oppPlayerPiece : oppositePlayerPieces) {
            if(oppPlayerPiece instanceof King k){
                List<String> commonPoints = GetPointsCommonInBothKing(board);
                if(commonPoints.contains(currentKingPosition)){
                    checkPiecePath.add(currentKingPosition);
                }
            }
            else{
                List<List<String>> playerMoves = oppPlayerPiece.ExpectedPaths(board);
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
    public List<String> IsCheck(PlayerEnum playerColor,List<List<Piece>> board){
        String currentKingPosition = GetKing(GetPlayerPieces(playerColor)).getPosition();
        return IsCheck(playerColor,currentKingPosition,board);
    }
    public void CreatePieces(List<List<Piece>> board) {
        // white pieces
        List<Piece> whitePieces = getWhitePieces();
        SeggregatePiece(whitePieces);
        board.add(whitePieces);
        List<Piece> whitePawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            whitePawns.add(new Pawn(PlayerEnum.White, "1"+i));
        }
        SeggregatePiece(whitePawns);
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
        SeggregatePiece(blackPawns);
        board.add(blackPawns);

        // Black pieces
        List<Piece> blackPieces = getBlackPieceList();
        board.add(blackPieces);
        SeggregatePiece(blackPieces);
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

    public void RemoveKilledPiece(Piece p) {
        List<Piece> pieces = GetPlayerPieces(p.getPlayer());
        pieces.remove(p);
    }
    public List<List<Piece>> GetBoardClone(List<List<Piece>> board) {
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
