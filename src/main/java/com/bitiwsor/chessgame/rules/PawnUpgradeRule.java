package com.bitiwsor.chessgame.rules;

import com.bitiwsor.chessgame.abstracts.Piece;
import com.bitiwsor.chessgame.abstracts.Rule;
import com.bitiwsor.chessgame.chess.Board;
import com.bitiwsor.chessgame.enums.ColorEnum;
import com.bitiwsor.chessgame.enums.PieceEnum;

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
