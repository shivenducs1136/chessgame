package chess;

import abstracts.ChessCallback;
import abstracts.Piece;
import enums.GameStateEnum;
import enums.ColorEnum;
import io.converters.AlgebraicNotationConverter;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {
    private final AlgebraicNotationConverter converter;
    private final ChessGame game;

    public ChessEngine(ChessCallback c){
        converter = new AlgebraicNotationConverter();
        game = new ChessGame(c);
    }
    public ChessEngine(ChessCallback c,List<List<Piece>> board,ColorEnum currentPlayerChance){
        converter = new AlgebraicNotationConverter();
        game = new ChessGame(c,board,currentPlayerChance);
    }
    /*
    * Parameters:
    * algebraicNotation: Piece location based on standard chess board algebraic notation.
    * Returns: List of moves possible for current piece to move, or empty list.
    * */
    public List<String> getExpectedMoves(String algebraicNotation){
        if(isGameEnded()) return new ArrayList<>();
        String position = converter.getCoordinatesFromAlgebraicNotation(algebraicNotation);
        if(position.length()>1){
            List<String> movesInCoordinate = game.getExpectedMove(position);
            return converter.getAlgebraicNotationFromCoordinates(movesInCoordinate);
        }
        return new ArrayList<>();
    }
    /*
     * Parameters:
     * oldAlgebraicPosition: Piece location based on standard chess board algebraic notation. Where piece is currently present.
     * newAlgebraicPosition: Piece location based on standard chess board algebraic notation. Where piece is going to move.
     * Returns: true when movePiece is successful, otherwise false.
     * */
    public boolean movePiece(String currentAlgebraicPosition,String newAlgebraicPosition){
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
    public void resignGame(ColorEnum playerColor){
        game.resignGame(playerColor);
    }
    public List<Piece> getKilledPieces(){
        return game.getKilledPieces();
    }
}
