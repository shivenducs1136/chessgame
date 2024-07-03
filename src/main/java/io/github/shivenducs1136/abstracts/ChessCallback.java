package io.github.shivenducs1136.abstracts;

import io.github.shivenducs1136.enums.PieceEnum;

/**
 * This interface defines a callback for chess-related actions.
 * It is primarily used to handle scenarios where a specific piece
 * selection is required, such as when a pawn is promoted.
 */
public interface ChessCallback {

    /**
     * This method is called when a pawn reaches the end of the board
     * and needs to be upgraded. The method should return the type of
     * piece (e.g., Queen, Rook, Bishop, Knight) that the pawn will be
     * promoted to.
     *
     * @return the selected piece for pawn promotion as a PieceEnum.
     */
    public PieceEnum getSelectedPieceForPawnUpgrade();
}
