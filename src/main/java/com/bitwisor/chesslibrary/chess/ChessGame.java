package chess;

import abstracts.*;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;
import io.PositionToIndexConverter;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ChessGame {
    
    private List<List<Piece>> board = new ArrayList<>();
    private PlayerEnum playerChance = PlayerEnum.White; 
    public GameStateEnum currentGameState;
    private List<String> checkPiecePath = null;
    private final PieceManager pieceManager;
    private final ChessCallback chessCallback;
    private final PositionToIndexConverter converter;
    /*
     * Parameters:
     * chessCallBack: An object of a class defined by user implementing ChessCallback interface
     * Returns:
     * */
    public ChessGame(ChessCallback c){
        chessCallback = c;
        pieceManager = new PieceManager(board);
        initializeBoard();
        pieceManager.updatePieceList();
        converter = new PositionToIndexConverter();
        setCurrentGameState(GameStateEnum.Initialized);
    }
    /*
     * Parameters:
     * position: Piece location based on "ij" format where i is row index starting from 0 and
     *           j is column index starting from 0.
     * Returns: List of moves possible for current piece to move, or empty list.
     * */
    public List<String> getExpectedMove(String position){
        List<String> moves = null;
        Piece currentPiece = pieceManager.getPieceAtPosition(position);
        if(currentPiece == null) return new ArrayList<>();
        if(playerChance == currentPiece.getPlayer()){
            if(currentPiece.getCanMove()){
                moves = currentPiece.expectedPaths(pieceManager).
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
                List<String> updatedMoveList = new ArrayList<>();
                for(String mv:moves){
                    if (!isMoveCausingCheck(currentPiece,mv)){
                        updatedMoveList.add(mv);
                    }
                }
                return updatedMoveList;
            }
        }
        return new ArrayList<>();
    }
    /*
     * Parameters:
     * oldPosition: The current location of the piece in internal chess notation (e.g., "01" equivalent to "g1").
     * newPosition: The target location for the piece in standard chess notation (e.g., "00" equivalent to "h1").
     * Returns: A boolean value indicating whether the move is valid and successfully executed (true) or invalid (false).
     */
    public boolean move(String oldPosition, String newPosition){
        Piece currentPiece = pieceManager.getPieceAtPosition(oldPosition);
        boolean isMovePerformed =  false;
        if(currentPiece.getPlayer() == playerChance){
            isMovePerformed = performMove(currentPiece,newPosition);
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

    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceOnBoard(int i,int j){
        return pieceManager.getPieceAtPosition(i,j);
    }

    /*
     * Parameters:
     * position: The location of the piece in standard chess notation (e.g., "01" equivalent to "g1").
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceOnBoard(String position){
        return pieceManager.getPieceAtPosition(position);
    }
    /*
     * Parameters:
     * currentGameState: The current state of the game, represented by a value from the GameStateEnum enumeration.
     * Returns: void. This method does not return a value.
     */
    public void setCurrentGameState(GameStateEnum currentGameState) {
        this.currentGameState = currentGameState;
    }
    /*
     * Parameters:
     * Returns: The PlayerEnum value representing the player whose turn it is to move.
     */
    public PlayerEnum getPlayer() {
        return playerChance;
    }

    private boolean isMoveCausingCheck(Piece currentPiece,String mv) {
        var newBoard = pieceManager.getBoardClone();
        boolean isCheck = false;
        Class<?> c = null;
        try {
            c = Class.forName(String.valueOf(currentPiece.getClass().getName()));
            Constructor<?> cons = c.getConstructor(PlayerEnum.class,String.class);
            Object object = cons.newInstance(new Object[]{currentPiece.getPlayer(),mv});
            Piece piece = (Piece) object;
            PieceManager pm = new PieceManager(newBoard);
            pm.setPieceInBoard(currentPiece.getPosition(),null);
            pm.setPieceInBoard(mv,piece);
            pm.updatePieceList();
            isCheck = pm.isCheck(piece.getPlayer()) != null;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)  {
            e.printStackTrace();
        }
        return isCheck;
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
    private List<String> getCastleMovesIfPossible(Piece piece) {
        King k = (King)piece;
        if(currentGameState == GameStateEnum.Check)
            return new ArrayList<>();
        return k.getCastleMovesIfPossible();
    }
    private Piece upgradePawn(String oldPosition, PieceEnum upgradePawnTo){
        if(upgradePawnTo.canUpgradeByPawn){
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
    private boolean performMove(Piece currentPiece,String newPosition) {
        String oldPosition = currentPiece.getPosition();
        var moves = getExpectedMove(oldPosition);
        if(moves.contains(newPosition)){
            Piece p = pieceManager.getPieceAtPosition(newPosition);
            if(p!=null){
                p.setIsKilled(true);
                pieceManager.removePiece(p);
            }
            if((currentPiece instanceof King k) && k.getCastleMovesIfPossible().contains(newPosition) && currentGameState!=GameStateEnum.Check){
                performCastleMove(k,newPosition);
            }
            else{
                if((currentPiece instanceof Pawn pwn) && !pwn.upgradableMoves.isEmpty()){
                    PieceEnum pieceEnum = chessCallback.getSelectedPieceForPawnUpgrade();
                    Piece pp = upgradePawn(oldPosition,pieceEnum);
                    if(pp !=null){
                        pieceManager.removePiece(currentPiece);
                        currentPiece = pp;
                        pieceManager.seggregatePiece(currentPiece);
                    }
                }
                pieceManager.setPieceInBoard(newPosition,currentPiece);
                pieceManager.setPieceInBoard(oldPosition,null);
                currentPiece.setPosition(newPosition);
            }
            currentPiece.setFirstMoveToFalse();
            return true;
        }
        return false;
    }
    private void performCastleMove(King k,String newPosition) {
        int j = converter.getJindex(newPosition);
        if(j == 1){
            //LeftCastle
            pieceManager.setPieceInBoard(newPosition,k);
            pieceManager.setPieceInBoard(k.getPosition(),null);
            k.setPosition(newPosition);
            Rook r = (Rook) pieceManager.getPieceAtPosition(converter.getIindex(k.getPosition())+"0");
            pieceManager.setPieceInBoard(converter.getIindex(k.getPosition())+"2",r);
            pieceManager.setPieceInBoard(converter.getIindex(r.getPosition())+"0",null);
            r.setPosition(converter.getIindex(k.getPosition())+"2");
        }
        else{
            //Right Castle
            pieceManager.setPieceInBoard(newPosition,k);
            pieceManager.setPieceInBoard(k.getPosition(),null);
            k.setPosition(newPosition);
            Rook r = (Rook) pieceManager.getPieceAtPosition(converter.getIindex(k.getPosition())+"0");
            pieceManager.setPieceInBoard(converter.getIindex(k.getPosition())+"4",r);
            pieceManager.setPieceInBoard(converter.getIindex(r.getPosition())+"7",null);
            r.setPosition(converter.getIindex(k.getPosition())+"4");
        }
    }
    private void updateGameState() {
        checkPiecePath = pieceManager.isCheck(playerChance);
        if(checkPiecePath!=null){
            currentGameState = GameStateEnum.Check;
            pieceManager.restrictPiecesOnCheck(playerChance);
            boolean isAllowedAny = pieceManager.allowPiecesThatCanResolveCheck(checkPiecePath,playerChance,this);
            if(!isAllowedAny && getExpectedMove(pieceManager.getKing(playerChance).getPosition()).isEmpty()){
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
            if(!pieceManager.isPlayerHaveAnyLegalMove(playerChance)){
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
    private void initializeBoard() {
        // white pieces
        List<Piece> whitePieces = getWhitePieces();
        board.add(whitePieces);
        List<Piece> whitePawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            whitePawns.add(new Pawn(PlayerEnum.White, "1"+i));
        }
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
        board.add(blackPawns);
        // Black pieces
        List<Piece> blackPieces = getBlackPieceList();
        board.add(blackPieces);
    }
    private List<Piece> getBlackPieceList() {
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
    private List<Piece> getWhitePieces() {
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

}