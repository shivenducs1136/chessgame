package chess;

import abstracts.*;
import enums.GameStateEnum;
import enums.PieceEnum;
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
    private final ChessCallback chess;
    public ChessGame(ChessCallback c){
        chess = c;
        pieceManager.createPieces(board);
        setCurrentGameState(GameStateEnum.Initialized);
    }
    public ChessGame(ChessCallback c, List<List<Piece>> boardState){
        chess = c;
        board = boardState;
        setCurrentGameState(GameStateEnum.Initialized);
    }
    public List<String> getExpectedMove(String position){
        List<String> moves = null;
        Piece currentPiece = pieceManager.getPieceAtPosition(position,board);
        if(currentPiece == null) return new ArrayList<>();
        if(playerChance == currentPiece.getPlayer()){
            if(currentPiece.getCanMove()){
                moves = currentPiece.expectedPaths(board).
                        stream().flatMap(Collection::stream).collect(Collectors.toList());;
                if(currentGameState == GameStateEnum.Check && !(currentPiece instanceof King)){
                    List<String> updatedMoves = new ArrayList<>();
                    for(String mv:moves){
                        if(checkPiecePath.contains(mv)){
                            updatedMoves.add(mv);
                        }
                    }
                    moves = updatedMoves;
                }
                if(currentPiece instanceof King){
                    var mvs=getCastleMovesIfPossible(currentPiece);
                    if(!mvs.isEmpty()){
                        moves.addAll(mvs);
                    }
                }
                for(String mv:moves){
                    if (isMoveCausingCheck(currentPiece,mv)){
                        moves.remove(mv);
                    }
                }
                return moves;
            }
        }
        return new ArrayList<>();
    }

    private boolean isMoveCausingCheck(Piece currentPiece,String mv) {
        var newBoard = pieceManager.getBoardClone(board);
        newBoard.get(pieceManager.getIindex(currentPiece.getPosition())).set(pieceManager.getJindex(currentPiece.getPosition()),null);
        newBoard.get(pieceManager.getIindex(mv)).set(pieceManager.getJindex(mv),currentPiece);

        return false;
    }

    public boolean move(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.getPieceAtPosition(oldPosition,board);
        boolean isMovePerformed =  false;
        if(currentPiece.getPlayer() == playerChance){
            isMovePerformed = performMove(currentPiece,newPosition,board);
            if(isMovePerformed){
                setCurrentGameState(GameStateEnum.Running);
                switchChance();
                updateGameState();
                if(currentGameState == GameStateEnum.WonByBlack ||
                        currentGameState == GameStateEnum.WonByWhite ||
                        currentGameState == GameStateEnum.StaleMate )   {
                    endGame();
                }
            }
        }
        return isMovePerformed;
    }

    private void endGame() {
        List<Piece> whitePieces = pieceManager.getPlayerPieces(PlayerEnum.White);
        for(Piece p:whitePieces){
            p.setCanMove(false);
        }
        List<Piece> blackPieces = pieceManager.getPlayerPieces(PlayerEnum.Black);
        for(Piece p:blackPieces){
            p.setCanMove(false);
        }
        playerChance = PlayerEnum.None;
    }

    public Piece getPieceOnBoard(int i,int j){
        return board.get(i).get(j);
    }
    public void setCurrentGameState(GameStateEnum currentGameState) {
        this.currentGameState = currentGameState;
    }
    public PlayerEnum getPlayer() {
        return playerChance;
    }

    private List<String> getCastleMovesIfPossible(Piece piece) {
        King k = (King)piece;
        if(currentGameState == GameStateEnum.Check)
            return new ArrayList<>();
        return k.getCastleMovesIfPossible(board);
    }
    private Piece upgradePawn(String oldPosition, PieceEnum upgradePawnTo){
        if(upgradePawnTo.canUpgradeByPawn){
            int i = pieceManager.getIindex(oldPosition);
            int j = pieceManager.getJindex(oldPosition);
            Piece newPiece;
            switch (upgradePawnTo){
                case Rook ->{
                    newPiece = new Rook(playerChance,oldPosition);
                    break;
                }
                case Bishop -> {
                    newPiece = new Bishop(playerChance,oldPosition);
                    break;
                }
                case Knight -> {
                    newPiece = new Knight(playerChance,oldPosition);
                    break;
                }
                default -> {
                    newPiece = new Queen(playerChance,oldPosition);
                    break;
                }
            }
            return newPiece;
        }
        return null;
    }
    private boolean performMove(Piece currentPiece,String newPosition,List<List<Piece>> board) {
        String oldPosition = currentPiece.getPosition();
        var moves = getExpectedMove(oldPosition);
        if(moves.contains(newPosition)){
            Piece p = pieceManager.getPieceAtPosition(newPosition,board);
            if(p!=null){
                p.setIsKilled(true);
                pieceManager.removeKilledPiece(p);
            }
            if((currentPiece instanceof King k) && k.getCastleMovesIfPossible(board).contains(newPosition) && currentGameState!=GameStateEnum.Check){
                performCastleMove(k,newPosition);
            }
            else{
                if((currentPiece instanceof Pawn pwn) && !pwn.upgradableMoves.isEmpty()){
                    PieceEnum pieceEnum = chess.getSelectedPieceForPawnUpgrade();
                    Piece pp = upgradePawn(oldPosition,pieceEnum);
                    if(pp !=null){
                        currentPiece = pp;
                        pieceManager.seggregatePiece(currentPiece);
                    }
                }
                board.get(pieceManager.getIindex(newPosition)).set(pieceManager.getJindex(newPosition), currentPiece);
                board.get(pieceManager.getIindex(oldPosition)).set(pieceManager.getJindex(oldPosition), null);
                currentPiece.setPosition(newPosition);
            }
            currentPiece.setFirstMoveToFalse();
            return true;
        }
        return false;
    }


    private void performCastleMove(King k,String newPosition) {
        int j = pieceManager.getJindex(newPosition);
        if(j == 1){
            //LeftCastle
            board.get(pieceManager.getIindex(newPosition)).set(pieceManager.getJindex(newPosition), k);
            board.get(pieceManager.getIindex(k.getPosition())).set(pieceManager.getJindex(k.getPosition()), null);
            k.setPosition(newPosition);
            Rook r = (Rook)board.get(pieceManager.getIindex(k.getPosition())).get(0);
            board.get(pieceManager.getIindex(k.getPosition())).set(2,r);
            board.get(pieceManager.getIindex(r.getPosition())).set(0, null);
            r.setPosition(pieceManager.getIindex(k.getPosition())+"2");
        }
        else{
            //Right Castle
            board.get(pieceManager.getIindex(newPosition)).set(pieceManager.getJindex(newPosition), k);
            board.get(pieceManager.getIindex(k.getPosition())).set(pieceManager.getJindex(k.getPosition()), null);
            k.setPosition(newPosition);
            Rook r = (Rook)board.get(pieceManager.getIindex(k.getPosition())).get(0);
            board.get(pieceManager.getIindex(k.getPosition())).set(4,r);
            board.get(pieceManager.getIindex(r.getPosition())).set(7, null);
            r.setPosition(pieceManager.getIindex(k.getPosition())+"4");
        }
    }
    private void updateGameState() {
        checkPiecePath = pieceManager.isCheck(playerChance,board);
        if(checkPiecePath!=null){
            currentGameState = GameStateEnum.Check;
            pieceManager.restrictPiecesOnCheck(playerChance);
            boolean isAllowedAny = pieceManager.allowPiecesThatCanResolveCheck(checkPiecePath,playerChance,this);
            if(!isAllowedAny && getExpectedMove(pieceManager.getKing(pieceManager.getPlayerPieces(playerChance)).getPosition()).isEmpty()){
                if(pieceManager.getOppositePlayerColor(playerChance) == PlayerEnum.White){
                    currentGameState = GameStateEnum.WonByWhite;
                }
                else{
                    currentGameState = GameStateEnum.WonByBlack;
                }
            }
        }
        else{
            currentGameState = GameStateEnum.Running;
            pieceManager.allowAllPiecesToMove(playerChance);
            if(!pieceManager.isPlayerHaveAnyLegalMove(playerChance,this)){
                currentGameState = GameStateEnum.StaleMate;
            }
        }
    }
    private void switchChance(){
        if(playerChance == PlayerEnum.White){
            playerChance = PlayerEnum.Black; 
        }
        else{
            playerChance = PlayerEnum.White;
        }
    }

    public List<List<Piece>> getBoard() {
            return board;
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
        int i = pieceManager.getIindex(pc.getPosition());
        int j = pieceManager.getJindex(pc.getPosition()); 
        pieceManager.SeggregatePiece(pc);
        board.get(i).set(j, pc); 
    }

    public boolean SampleMove(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.GetPieceAtPosition(oldPosition);

            var moves = getExpectedMove(oldPosition);
            if(moves.contains(newPosition)){
                Piece p = pieceManager.GetPieceAtPosition(newPosition);
                if(p!=null){
                    p.setIsKilled(true);
                }
                board.get(pieceManager.getIindex(newPosition)).set(pieceManager.getJindex(newPosition), currentPiece);
                board.get(pieceManager.getIindex(oldPosition)).set(pieceManager.getJindex(oldPosition), null);
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
