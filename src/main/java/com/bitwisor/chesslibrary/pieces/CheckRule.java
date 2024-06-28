package pieces;

import abstracts.Piece;
import abstracts.Rule;
import chess.Board;
import chess.RuleFactory;
import enums.ColorEnum;
import enums.PieceEnum;

import java.util.ArrayList;
import java.util.List;

public class CheckRule extends Rule {

    public CheckRule(Board board, Piece piece) {
        super(board, piece);
    }

    @Override
    public List<List<String>> expectedPaths() {
        return null;
    }

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.King;
    }
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose check condition is to be evaluated.
     * currentKingPosition: The current position of the king in standard chess notation (e.g., "e1").
     * Returns: A list of strings representing the positions that put the specified player's king in check.
     */
    public List<String> isCheck(ColorEnum playerColor, String currentKingPosition){
        List<Piece> oppositePlayerPieces = board.getPlayerPieces(board.getOppositePlayerColor(playerColor));
        List<String> checkPiecePath = new ArrayList<>();
        for (Piece oppPlayerPiece : oppositePlayerPieces) {
            if(oppPlayerPiece.getPieceType() == PieceEnum.King){
                List<String> commonPoints = board.getPointsCommonInBothKing();
                if(commonPoints.contains(currentKingPosition)){
                    checkPiecePath.add(currentKingPosition);
                }
            }
            else{
                List<List<String>> playerMoves = RuleFactory.getRule(board,oppPlayerPiece).expectedPaths();
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
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose check condition is to be evaluated.
     * Returns: A list of strings representing the positions that put the specified player's king in check.
     */
    public List<String> isCheck(ColorEnum playerColor){
        String currentKingPosition = board.getKing(playerColor).getPosition();
        return isCheck(playerColor,currentKingPosition);
    }
}
