package io.github.shivenducs1136.chess;

import io.github.shivenducs1136.abstracts.ChessCallback;
import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.enums.GameStateEnum;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.exceptions.InvalidMoveException;
import io.github.shivenducs1136.io.converters.AlgebraicNotationConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the chess engine, responsible for managing the state
 * of the chess game, handling moves, and interacting with the game components.
 * It uses an AlgebraicNotationConverter for move notation and a ChessGame instance
 * to manage the game logic.
 */
public class ChessEngine {

    // Converter to handle algebraic notation for moves
    private final AlgebraicNotationConverter converter;

    // Instance of the ChessGame to manage the game state and logic
    private final ChessGame game;

    /**
     * Constructor to initialize the chess engine with a callback for pawn promotion.
     * Creates an algebraic notation converter and a new chess game.
     *
     * @param c the callback to handle pawn promotion
     */
    public ChessEngine(ChessCallback c) {
        converter = new AlgebraicNotationConverter(); // Initialize the notation converter
        game = new ChessGame(c); // Initialize the chess game with the callback
    }

    /**
     * Constructor to initialize the chess engine with a callback, a specific board configuration,
     * and the current player's turn. Creates an algebraic notation converter and a new chess game
     * with the provided state.
     *
     * @param c the callback to handle pawn promotion
     * @param board the 2D list representing the chess board with pieces
     * @param currentPlayerChance the color of the player whose turn it is
     * @throws InvalidMoveException if the board configuration is invalid
     */
    public ChessEngine(ChessCallback c, List<List<Piece>> board, ColorEnum currentPlayerChance) throws InvalidMoveException {
        converter = new AlgebraicNotationConverter(); // Initialize the notation converter
        game = new ChessGame(c, board, currentPlayerChance); // Initialize the chess game with the provided state
    }

    /**
     * Retrieves the list of pieces that have been captured during the game.
     *
     * @return the list of killed pieces
     */
    public List<Piece> getKilledPieces() {
        return game.getKilledPieces();
    }

    /**
     * Sets the color of the player who has the current turn.
     *
     * @param color the color of the player to set as having the current turn
     */
    public void setCurrentChanceColor(ColorEnum color) {
        game.setCurrentChance(color);
    }

    /*
    * Parameters:
    * algebraicNotation: Piece location based on standard chess board algebraic notation.
    * Returns: List of moves possible for current piece to move, or empty list.
    * */
    public List<String> getExpectedMoves(String algebraicNotation) throws InvalidMoveException {
        if(isGameEnded()) return new ArrayList<>();
        String position = converter.getCoordinatesFromAlgebraicNotation(algebraicNotation);
        if(position.length()>1){
            List<String> movesInCoordinate = game.getExpectedMove(position);
            return converter.getAlgebraicNotationFromCoordinates(movesInCoordinate);
        }
        throw new InvalidMoveException();
    }
    /*
     * Parameters:
     * oldAlgebraicPosition: Piece location based on standard chess board algebraic notation. Where piece is currently present.
     * newAlgebraicPosition: Piece location based on standard chess board algebraic notation. Where piece is going to move.
     * Returns: true when movePiece is successful, otherwise false.
     * */
    public boolean movePiece(String currentAlgebraicPosition,String newAlgebraicPosition) throws Exception {
        if(isGameEnded()) return false;
        String oldPosition =converter.getCoordinatesFromAlgebraicNotation(currentAlgebraicPosition);
        String newPosition = converter.getCoordinatesFromAlgebraicNotation(newAlgebraicPosition);
        return game.move(oldPosition,newPosition);
    }
    /*
     * Parameters:
     * Returns: Current chess game state.
     * */
    public GameStateEnum getCurrentGameState(){
        return game.currentGameState;
    }
    /*
     * Parameters:
     * Returns: Current player color, White when the current chance is of White player,
     *          Black when the current chance is of Black player,
     *          None when game is over
     * */
    public ColorEnum getCurrentPlayer(){
        return game.getPlayer();
    }
    /*
     * Parameters:
     * i: Represent a row index in chess board starting from 0, maximum valid value is 7
     * j: Represent a column index in chess board starting from 0, maximum valid value is 7
     * Returns: A piece present at currently provided indices,
     *          if indices are within the board range it returns
     *          the piece present at this position otherwise null.
     * */
    public Piece getPieceFromBoard(int i,int j){
        return game.getPieceOnBoard(i,j);
    }
    /*
     * Parameters:
     * algebraicNotation: Piece location based on standard chess board algebraic notation.
     *
     * Returns: A piece present at currently provided indices,
     *          if indices are within the board range it returns
     *          the piece present at this position otherwise null.
     * */
    public Piece getPieceFromBoard(String algebraicNotation){
        String currentNotation = converter.getCoordinatesFromAlgebraicNotation(algebraicNotation);
        return game.getPieceOnBoard(currentNotation);
    }
    /*
     * Parameters:
     * piece: A piece on a chess board.
     *
     * Returns: Piece location based on standard chess board algebraic notation.
     *          if piece is null then it will return null.
     * */
    public String getPiecePosition(Piece piece){
        return converter.getAlgebraicNotationFromCoordinates(piece.getPosition());
    }
    /*
     * Parameters:
     * Returns: true if game if finished i.e. Either Stalemate or WonByWhite or WonByBlack
     * */
     public boolean isGameEnded(){
        GameStateEnum g =getCurrentGameState();
        return (g == GameStateEnum.StaleMate || g == GameStateEnum.WonByBlack || g == GameStateEnum.WonByWhite);
    }
    /*
     * Resigns the game for the player whose turn it is.
     * Params:
     *   playerChance - The ColorEnum representing the player who is currently taking their turn.
     */
    public void resignGame(ColorEnum playerColor) throws InvalidMoveException {
        game.resignGame(playerColor);
    }
}
