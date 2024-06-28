package com.bitiwsor.chessgame.chess;

import com.bitiwsor.chessgame.abstracts.ChessCallback;
import com.bitiwsor.chessgame.abstracts.Piece;
import com.bitiwsor.chessgame.enums.GameStateEnum;
import com.bitiwsor.chessgame.enums.PieceEnum;
import com.bitiwsor.chessgame.enums.ColorEnum;
import com.bitiwsor.chessgame.exceptions.InvalidMoveException;
import com.bitiwsor.chessgame.exceptions.NoPieceToMoveException;
import com.bitiwsor.chessgame.io.converters.AlgebraicNotationConverter;
import com.bitiwsor.chessgame.io.converters.PositionToIndexConverter;
import com.bitiwsor.chessgame.rules.CheckRule;
import com.bitiwsor.chessgame.rules.KingRule;
import com.bitiwsor.chessgame.rules.PawnUpgradeRule;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ChessGame {
    
    private ColorEnum playerChance = ColorEnum.White;
    public GameStateEnum currentGameState;
    private List<String> checkPiecePath = null;
    private final Board board;
    private final ChessCallback chessCallback;
    private final PositionToIndexConverter converter;
    /*
     * Parameters:
     * chessCallBack: An object of a class defined by user implementing ChessCallback interface
     * Returns:
     * */
    public ChessGame(ChessCallback c){
        chessCallback = c;
        board = new Board();
        converter = new PositionToIndexConverter();
        setCurrentGameState(GameStateEnum.Initialized);
    }
    /*
     * Parameters:
     * chessCallBack: An object of a class defined by user implementing ChessCallback interface
     * board: A 2d list representing a chess board
     * Returns:
     * */
    public ChessGame(ChessCallback c, List<List<Piece>> board, ColorEnum chance) throws InvalidMoveException {
        chessCallback = c;
        this.board = new Board(board);
        this.playerChance = chance;
        converter = new PositionToIndexConverter();
        setCurrentGameState(GameStateEnum.Initialized);
        updateGameState();
    }
    /*
     * Parameters:
     * position: Piece location based on "ij" format where i is row index starting from 0 and
     *           j is column index starting from 0.
     * Returns: List of moves possible for current piece to move, or empty list.
     * */
    public List<String> getExpectedMove(String position) throws InvalidMoveException {
        List<String> moves = null;
        Piece currentPiece = board.getPieceAtPosition(position);
        if(currentPiece == null) throw new InvalidMoveException();
        if(playerChance == currentPiece.getPlayerColor()){
            if(currentPiece.getCanMove()){
                moves = RuleFactory.getRule(board,currentPiece).expectedPaths().
                        stream().flatMap(Collection::stream).collect(Collectors.toList());;
                if(currentGameState == GameStateEnum.Check && currentPiece.getPieceType()!=PieceEnum.King){
                    List<String> updatedMoves = new ArrayList<>();
                    for(String mv:moves){
                        if(checkPiecePath.contains(mv)){
                            updatedMoves.add(mv);
                        }
                    }
                    moves = updatedMoves;
                }
                if(currentPiece.getPieceType() == PieceEnum.King){
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
        throw new InvalidMoveException();
    }
    /*
     * Parameters:
     * oldPosition: The current location of the piece in internal chess notation (e.g., "01" equivalent to "g1").
     * newPosition: The target location for the piece in standard chess notation (e.g., "00" equivalent to "h1").
     * Returns: A boolean value indicating whether the move is valid and successfully executed (true) or invalid (false).
     */
    public boolean move(String oldPosition, String newPosition) throws Exception {
        Piece currentPiece = board.getPieceAtPosition(oldPosition);
        boolean isMovePerformed =  false;
        if(currentPiece == null)
        {
            throw new NoPieceToMoveException("No piece is present at "+(new AlgebraicNotationConverter()).getAlgebraicNotationFromCoordinates(oldPosition));
        }
        if(currentPiece.getPlayerColor() == playerChance){
            isMovePerformed = performMove(currentPiece,newPosition);
            if(isMovePerformed){
                setCurrentGameState(GameStateEnum.Running);
                switchChance();
                updateGameState();
            }
            else {
                throw new InvalidMoveException();
            }
        }
        else throw new InvalidMoveException();
        return isMovePerformed;
    }

    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceOnBoard(int i, int j){
        return board.getPieceAtPosition(i,j);
    }

    /*
     * Parameters:
     * position: The location of the piece in standard chess notation (e.g., "01" equivalent to "g1").
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceOnBoard(String position){
        return board.getPieceAtPosition(position);
    }
    /*
     * Parameters:
     * currentGameState: The current state of the game, represented by a value from the GameStateEnum enumeration.
     * Returns: void. This method does not return a value.
     */
    private void setCurrentGameState(GameStateEnum currentGameState) {
        this.currentGameState = currentGameState;
    }
    /*
     * Parameters:
     * Returns: The PlayerEnum value representing the player whose turn it is to move.
     */
    public ColorEnum getPlayer() {
        return playerChance;
    }

    /*
     * Resigns the game for the player whose turn it is.
     * Params:
     *   playerChance - The ColorEnum representing the player who is currently taking their turn.
     */
    public void resignGame(ColorEnum playerChance) throws InvalidMoveException {
        // If the current player is Black, set the game state to WonByWhite
        if (playerChance == ColorEnum.Black) {
            currentGameState = GameStateEnum.WonByWhite;
        }
        // If the current player is White, set the game state to WonByBlack
        else if (playerChance == ColorEnum.White) {
            currentGameState = GameStateEnum.WonByBlack;
        }
        // Update the game state to reflect the change
        updateGameState();
    }

    public List<Piece> getKilledPieces(){
        return board.getKilledPieceList();
    }

    private boolean isMoveCausingCheck(Piece currentPiece, String mv) {
        Board newBoard = board.getClone();
        boolean isCheck = false;
        Class<?> c = null;
        try {
            c = Class.forName(String.valueOf(currentPiece.getClass().getName()));
            Constructor<?> cons = c.getConstructor(PieceEnum.class,ColorEnum.class,String.class);
            Object object = cons.newInstance(new Object[]{currentPiece.getPieceType(),currentPiece.getPlayerColor(),mv});
            Piece piece = (Piece) object;
            newBoard.put(currentPiece.getPosition(),null);
            newBoard.put(mv,piece);
            newBoard.updatePieceList();
            isCheck = ((CheckRule)RuleFactory.getRule(newBoard,board.getKing(playerChance), CheckRule.class)).isCheck(piece.getPlayerColor()) != null;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)  {
            e.printStackTrace();
        }
        return isCheck;
    }
    private void endGame() {
        List<Piece> whitePieces = board.getPlayerPieces(ColorEnum.White);
        for(Piece p:whitePieces){
            p.setCanMove(false);
        }
        List<Piece> blackPieces = board.getPlayerPieces(ColorEnum.Black);
        for(Piece p:blackPieces){
            p.setCanMove(false);
        }
        playerChance = ColorEnum.None;
    }
    private List<String> getCastleMovesIfPossible(Piece kingPiece) {
        if(currentGameState == GameStateEnum.Check)
            return new ArrayList<>();
        KingRule rule = (KingRule) RuleFactory.getRule(board,kingPiece);
        return rule.getCastleMovesIfPossible();
    }
    private Piece upgradePawn(String oldPosition, PieceEnum upgradePawnTo){
        if(upgradePawnTo.canUpgradeByPawn){
            Piece newPiece = new ChessPiece(upgradePawnTo,playerChance,oldPosition);
            return newPiece;
        }
        return null;
    }
    private boolean performMove(Piece currentPiece, String newPosition) throws InvalidMoveException {
        String oldPosition = currentPiece.getPosition();
        var moves = getExpectedMove(oldPosition);
        if(moves.contains(newPosition)){
            Piece p = board.getPieceAtPosition(newPosition);
            if(p!=null){
                p.setIsKilled(true);
                board.addKilledPieceToList(p);
                board.clear(p);
            }
            if((currentPiece.getPieceType() == PieceEnum.King) &&
                    ((KingRule)RuleFactory.getRule(board,currentPiece)).getCastleMovesIfPossible().contains(newPosition) &&
                    currentGameState!=GameStateEnum.Check){
                performCastleMove(currentPiece,newPosition);
            }
            else{
                if((currentPiece.getPieceType()== PieceEnum.Pawn) &&
                        ((PawnUpgradeRule)RuleFactory.getRule(board,currentPiece, PawnUpgradeRule.class)).isPawnUpgradable(newPosition)){

                    PieceEnum pieceEnum = chessCallback.getSelectedPieceForPawnUpgrade();
                    Piece pp = upgradePawn(oldPosition,pieceEnum);
                    if(pp !=null){
                        board.clear(currentPiece);
                        currentPiece = pp;
                        board.seggregatePiece(currentPiece);
                    }
                }
                board.put(newPosition,currentPiece);
                board.put(oldPosition,null);
                currentPiece.setPosition(newPosition);
            }
            currentPiece.setFirstMoveToFalse();
            return true;
        }
        return false;
    }
    private void performCastleMove(Piece kingPiece, String newPosition) {
        int j = converter.getJindex(newPosition);
        if(j == 1){
            //LeftCastle
            board.put(newPosition,kingPiece);
            board.put(kingPiece.getPosition(),null);
            kingPiece.setPosition(newPosition);
            Piece rook =  board.getPieceAtPosition(converter.getIindex(kingPiece.getPosition())+"0");
            board.put(converter.getIindex(kingPiece.getPosition())+"2",rook);
            board.put(converter.getIindex(rook.getPosition())+"0",null);
            rook.setPosition(converter.getIindex(kingPiece.getPosition())+"2");
        }
        else{
            //Right Castle
            board.put(newPosition,kingPiece);
            board.put(kingPiece.getPosition(),null);
            kingPiece.setPosition(newPosition);
            Piece rook =  board.getPieceAtPosition(converter.getIindex(kingPiece.getPosition())+"7");
            board.put(converter.getIindex(kingPiece.getPosition())+"4",rook);
            board.put(converter.getIindex(rook.getPosition())+"7",null);
            rook.setPosition(converter.getIindex(kingPiece.getPosition())+"4");
        }
    }
    private void updateGameState() throws InvalidMoveException {
        checkPiecePath = ((CheckRule)RuleFactory.getRule(board,board.getKing(playerChance), CheckRule.class)).isCheck(playerChance);
        if(checkPiecePath!=null){
            currentGameState = GameStateEnum.Check;
            restrictPiecesOnCheck(playerChance);
            boolean isAllowedAny = board.allowPiecesThatCanResolveCheck(checkPiecePath,playerChance,this);
            if(!isAllowedAny && getExpectedMove(board.getKing(playerChance).getPosition()).isEmpty()){
                if(board.getOppositePlayerColor(playerChance) == ColorEnum.White){
                    currentGameState = GameStateEnum.WonByWhite;
                }
                else{
                    currentGameState = GameStateEnum.WonByBlack;
                }
            }
        }
        else{
            currentGameState = GameStateEnum.Running;
            board.allowAllPiecesToMove(playerChance);
            if(!board.isPlayerHaveAnyLegalMove(playerChance)){
                currentGameState = GameStateEnum.StaleMate;
            }
        }
        if(currentGameState == GameStateEnum.WonByBlack ||
                currentGameState == GameStateEnum.WonByWhite ||
                currentGameState == GameStateEnum.StaleMate )   {
            endGame();
        }
    }
    private void switchChance(){
        if(playerChance == ColorEnum.White){
            playerChance = ColorEnum.Black;
        }
        else{
            playerChance = ColorEnum.White;
        }
    }
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose pieces are to be restricted due to a check condition.
     * Returns: void. This method does not return a value.
     */
    private void restrictPiecesOnCheck(ColorEnum playerColor) {
        List<Piece> pieces = board.getPlayerPieces(playerColor);
        for(Piece piece:pieces){
            if(!(piece.getPieceType() == PieceEnum.King)){
                piece.setCanMove(false);
            }
        }

    }
    public void setCurrentChance(ColorEnum color) {
        playerChance =color;
    }
}
