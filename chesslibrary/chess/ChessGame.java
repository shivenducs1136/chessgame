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
import java.util.stream.Collectors;

public class ChessGame {
    
    private List<List<Piece>> board = new ArrayList<>();
    private PlayerEnum playerChance = PlayerEnum.White; 
    public GameStateEnum currentGameState;
    private List<String> checkPiecePath = null;
    private final PieceManager pieceManager = new PieceManager();

    public ChessGame(){
        pieceManager.CreatePieces(board);
        setCurrentGameState(GameStateEnum.Initialized);
    }
    public ChessGame(List<List<Piece>> boardState){
        board = boardState;
        setCurrentGameState(GameStateEnum.Initialized);
    }
    public List<String> GetExpectedMove(String position){
        List<String> moves = null;
        Piece currentPiece = pieceManager.GetPieceAtPosition(position,board);
        if(currentPiece == null) return null;
        if(playerChance == currentPiece.getPlayer()){
            if(currentPiece.getCanMove()){
                moves = currentPiece.ExpectedPaths(board).
                        stream().flatMap(Collection::stream).collect(Collectors.toList());;
                if(currentGameState == GameStateEnum.Check && !(currentPiece instanceof King)){
                    List<String> updatedMoves = new ArrayList<>();
                    for(String mv:moves){
                        if(checkPiecePath.contains(mv)){
                            updatedMoves.add(mv);
                        }
                    }
                    return updatedMoves;
                }
                return moves;
            }
        }
        return new ArrayList<>();
    }
    public void Move(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.GetPieceAtPosition(oldPosition,board);
        if(currentPiece.getPlayer() == playerChance){
            PerformMove(currentPiece,newPosition);
            setCurrentGameState(GameStateEnum.Running);
            SwitchChance();
            UpdateGameState();
        }
    }
    public List<List<Piece>> GetBoard(){
        return board;
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
            Piece p = pieceManager.GetPieceAtPosition(newPosition,board);
            if(p!=null){
                p.setIsKilled(true);
                pieceManager.RemoveKilledPiece(p);
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
        checkPiecePath = pieceManager.IsCheck(playerChance,board);
        if(checkPiecePath!=null){
            currentGameState = GameStateEnum.Check;
            pieceManager.RestrictPiecesOnCheck(playerChance);
            boolean isAllowedAny = pieceManager.AllowPiecesThatCanResolveCheck(checkPiecePath,playerChance,this);
            if(!isAllowedAny && GetExpectedMove(pieceManager.GetKing(pieceManager.GetPlayerPieces(playerChance)).getPosition()).isEmpty()){
                if(pieceManager.GetOppositePlayerColor(playerChance) == PlayerEnum.White){
                    currentGameState = GameStateEnum.WonByWhite;
                }
                else{
                    currentGameState = GameStateEnum.WonByBlack;
                }
            }
        }
        else{
            currentGameState = GameStateEnum.Running;
            pieceManager.AllowAllPiecesToMove(playerChance);
            if(!pieceManager.IsPlayerHaveAnyLegalMove(playerChance,this)){
                currentGameState = GameStateEnum.StaleMate;
            }
        }
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
