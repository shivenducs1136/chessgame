package chess;

import abstracts.*;
import enums.GameStateEnum;
import enums.PlayerEnum;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

import java.util.*;

public class ChessGame {
    
    public static final List<List<Piece>> board = new ArrayList<>();
    private PlayerEnum playerChance = PlayerEnum.White; 
    public GameStateEnum currentGameState;
    private List<String> checkMatePieceMoves = null;
    final PieceManager pieceManager; 

    public ChessGame(){
        pieceManager = new PieceManager(); 
         CreatePieces();
    }
    public List<String> GetExpectedMove(String position){
        List<String> moves = null;
        Piece currentPiece = pieceManager.GetPieceAtPosition(position); 
        if(currentPiece.getCanMove()){
            moves = currentPiece.ExpectedMove();
            if(currentGameState == GameStateEnum.Check){
                List<String> updatedMoves = new ArrayList<>();
                for(String mv:moves){
                    if(checkMatePieceMoves.contains(mv)){
                        updatedMoves.add(mv);
                    }
                }
                return updatedMoves;
            }
            return moves;
        }
        return null;
    }
    public void Move(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.GetPieceAtPosition(oldPosition);
        if(currentPiece.getPlayer() == playerChance){
            PerformMove(currentPiece,newPosition);
            setCurrentGameState(GameStateEnum.Running);
            UpdateGameState();
            SwitchChance();
        }
    }
    public void setCurrentGameState(GameStateEnum currentGameState) {
        this.currentGameState = currentGameState;
    }
    public PlayerEnum getPlayer() {
        return playerChance;
    }

    private void PerformMove(Piece currentPiece,String newPosition) {
        String oldPosition = currentPiece.getPosition();
        var moves = GetExpectedMove(oldPosition);
        if(moves.contains(newPosition)){
            Piece p = pieceManager.GetPieceAtPosition(newPosition);
            if(p!=null){
                p.setIsKilled(true);
            }
            board.get(pieceManager.GetIindex(newPosition)).set(pieceManager.GetJindex(newPosition), currentPiece);
            board.get(pieceManager.GetIindex(oldPosition)).set(pieceManager.GetJindex(oldPosition), null);
            currentPiece.setPosition(newPosition);
            if(currentPiece instanceof Pawn pw){
                pw.FirstMoveDone();
            }
        }
    }
    private void UpdateGameState() {
        if(IsCheck(pieceManager.GetOppositePlayerColor(playerChance))){
            currentGameState = GameStateEnum.Check;
        }
    }
    private boolean IsCheck(PlayerEnum playerColor){
        List<Piece> oppositePlayerPieces = pieceManager.GetPlayerPieces(pieceManager.GetOppositePlayerColor(playerColor));
        String currentPlayerKingPosition = pieceManager.GetKing(pieceManager.GetPlayerPieces(playerColor)).getPosition();
        for (Piece oppositePlayerPiece : oppositePlayerPieces) {
            List<String> playerMoves = oppositePlayerPiece.ExpectedMove();
            // King
            if (playerMoves.contains(currentPlayerKingPosition)) {
                pieceManager.RestrictPlayersOnCheck(playerColor);
                List<Piece> currentPlayerPieces = pieceManager.GetPlayerPieces(playerColor);
                checkMatePieceMoves = playerMoves;
                for (Piece pc : currentPlayerPieces) {
                    List<String> expectedMoves = pc.ExpectedMove();
                    for (String mv : expectedMoves) {
                        if (playerMoves.contains(mv)) {
                            pc.setCanMove(true);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
    private void CreatePieces() {
        // white pieces
        List<Piece> whitePieces = getPieces();
        pieceManager.SeggregatePiece(whitePieces);
        board.add(whitePieces);
        List<Piece> whitePawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            whitePawns.add(new Pawn(PlayerEnum.White, "1"+i));
        }
        pieceManager.SeggregatePiece(whitePawns);
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
        pieceManager.SeggregatePiece(blackPawns);
        board.add(blackPawns);

        // Black pieces
        List<Piece> blackPieces = getBlackPieceList();
        board.add(blackPieces);
        pieceManager.SeggregatePiece(blackPieces);
        setCurrentGameState(GameStateEnum.Initialized);
    }
    private static List<Piece> getBlackPieceList() {
        Piece blackRook1 = new Rook(PlayerEnum.Black, "70");
        Piece blackRook2 = new Rook(PlayerEnum.Black, "77");
        Piece blackKnight1 = new Knight(PlayerEnum.Black, "71");
        Piece blackKnight2 = new Knight(PlayerEnum.Black, "76");
        Piece blackBishop1 = new Bishop(PlayerEnum.Black, "72");
        Piece blackBishop2 = new Bishop(PlayerEnum.Black, "75");
        Piece blackQueen = new Queen(PlayerEnum.Black, "73");
        Piece blackKing = new King(PlayerEnum.Black, "74");
        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(blackRook1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackRook2);
        return blackPieces;
    }
    private static List<Piece> getPieces() {
        Piece whiteRook1 = new Rook(PlayerEnum.White, "00");
        Piece whiteRook2 = new Rook(PlayerEnum.White, "70");
        Piece whiteKnight1 = new Knight(PlayerEnum.White, "01");
        Piece whiteKnight2 = new Knight(PlayerEnum.White, "06");
        Piece whiteBishop1 = new Bishop(PlayerEnum.White, "02");
        Piece whiteBishop2 = new Bishop(PlayerEnum.White, "05");
        Piece whiteQueen = new Queen(PlayerEnum.White, "03");
        Piece whiteKing = new King(PlayerEnum.White, "04");
        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(whiteRook1);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteRook2);
        return whitePieces;
    }
    private void SwitchChance(){
        if(playerChance == PlayerEnum.White){
            playerChance = PlayerEnum.Black; 
        }
        else{
            playerChance = PlayerEnum.White;
        }
    }


/*
    Below code is used for testing purpose.
    public void CreateSampleChessBoard(){
        board.clear();
        for(int i =0; i<8;i++){
            List<Piece> pc = new ArrayList<>(); 
            for(int j = 0; j< 8; j++){
                pc.add(null); 
            }
            board.add(pc);
        }
    }

    public void AddSamplePieceToBoard(Piece pc){
        int i = pieceManager.GetIindex(pc.getPosition());
        int j = pieceManager.GetJindex(pc.getPosition()); 
        pieceManager.SeggregatePiece(pc);
        board.get(i).set(j, pc); 
    }

    public boolean SampleMove(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.GetPieceAtPosition(oldPosition);

            var moves = GetExpectedMove(oldPosition);
            if(moves.contains(newPosition)){
                Piece p = pieceManager.GetPieceAtPosition(newPosition);
                if(p!=null){
                    p.setIsKilled(true);
                }
                board.get(pieceManager.GetIindex(newPosition)).set(pieceManager.GetJindex(newPosition), currentPiece);
                board.get(pieceManager.GetIindex(oldPosition)).set(pieceManager.GetJindex(oldPosition), null);
                currentPiece.setPosition(newPosition);
                if(currentPiece instanceof Pawn pw){
                    pw.FirstMoveDone();
                }
                setCurrentGameState(GameStateEnum.Running);
                UpdateGameState();
                SwitchChance();
                return true;
            }
            return false;
    }
*/
   
}
