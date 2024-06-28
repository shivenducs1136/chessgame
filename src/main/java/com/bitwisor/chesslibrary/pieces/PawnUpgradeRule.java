package pieces;

import abstracts.Piece;
import abstracts.Rule;
import chess.Board;
import enums.ColorEnum;
import enums.PieceEnum;

import java.util.List;

public class PawnUpgradeRule extends Rule {

    public PawnUpgradeRule(Board board, Piece piece) {
        super(board, piece);
    }

    @Override
    public List<List<String>> expectedPaths() {
        return List.of();
    }

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.Pawn;
    }


    public boolean isPawnUpgradable(String newPosition){
        int i = converter.getIindex(newPosition);
        if(piece.getPlayerColor() == ColorEnum.White){
            return i == 7;
        } else if (piece.getPlayerColor() == ColorEnum.Black) {
            return i == 0;
        }
        return false;
    }
}
