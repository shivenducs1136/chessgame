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

    public List<String> GetExpectedMoves(String algebraicNotation){
        if(IsGameEnded()) return new ArrayList<>();
        String position = converter.GetCoordinatesFromAlgebraicNotation(algebraicNotation);
        if(position.length()>1){
            List<String> movesInCoordinate = game.GetExpectedMove(position);
            return converter.GetAlgebraicNotationFromCoordinates(movesInCoordinate);
        }
        return new ArrayList<>();
    }

    public boolean MovePiece(String oldAlgebraicPosition,String newAlgebraicPosition){
        if(IsGameEnded()) return false;
        String oldPosition =converter.GetCoordinatesFromAlgebraicNotation(oldAlgebraicPosition);
        String newPosition = converter.GetCoordinatesFromAlgebraicNotation(newAlgebraicPosition);
        return game.Move(oldPosition,newPosition);
    }
    public GameStateEnum GetCurrentGameState(){
        return game.currentGameState;
    }
    public PlayerEnum GetCurrentPlayer(){
        return game.getPlayer();
    }
    public Piece GetPieceFromBoard(int i,int j){
        return game.GetPieceOnBoard(i,j);
    }
    public String GetPiecePosition(Piece p){
        return converter.GetAlgebraicNotationFromCoordinates(p.getPosition());
    }
    public boolean IsGameEnded(){
        GameStateEnum g =GetCurrentGameState();
        return (g == GameStateEnum.StaleMate || g == GameStateEnum.WonByBlack || g == GameStateEnum.WonByWhite);
    }
}
