package chess;

import abstracts.ChessCallback;
import abstracts.Piece;
import enums.GameStateEnum;
import enums.PlayerEnum;
import io.AlgebraicNotationConverter;

import java.util.ArrayList;
import java.util.List;

public class ChessEngine {
    private final AlgebraicNotationConverter converter;
    private final ChessGame game;
    public ChessEngine(ChessCallback c){
        converter = new AlgebraicNotationConverter();
        game = new ChessGame(c);
    }

    public List<String> getExpectedMoves(String algebraicNotation){
        if(isGameEnded()) return new ArrayList<>();
        String position = converter.getCoordinatesFromAlgebraicNotation(algebraicNotation);
        if(position.length()>1){
            List<String> movesInCoordinate = game.getExpectedMove(position);
            return converter.getAlgebraicNotationFromCoordinates(movesInCoordinate);
        }
        return new ArrayList<>();
    }

    public boolean movePiece(String oldAlgebraicPosition,String newAlgebraicPosition){
        if(isGameEnded()) return false;
        String oldPosition =converter.getCoordinatesFromAlgebraicNotation(oldAlgebraicPosition);
        String newPosition = converter.getCoordinatesFromAlgebraicNotation(newAlgebraicPosition);
        return game.move(oldPosition,newPosition);
    }
    public GameStateEnum getCurrentGameState(){
        return game.currentGameState;
    }
    public PlayerEnum getCurrentPlayer(){
        return game.getPlayer();
    }
    public Piece getPieceFromBoard(int i,int j){
        return game.getPieceOnBoard(i,j);
    }
    public String getPiecePosition(Piece p){
        return converter.getAlgebraicNotationFromCoordinates(p.getPosition());
    }
    public boolean isGameEnded(){
        GameStateEnum g =getCurrentGameState();
        return (g == GameStateEnum.StaleMate || g == GameStateEnum.WonByBlack || g == GameStateEnum.WonByWhite);
    }
}
