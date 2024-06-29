package io.github.shivenducs1136.rules;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.abstracts.Rule;
import io.github.shivenducs1136.chess.Board;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;

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
