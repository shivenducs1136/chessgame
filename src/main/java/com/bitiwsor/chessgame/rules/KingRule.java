package com.bitiwsor.chessgame.rules;

import java.util.ArrayList;
import java.util.List;

import com.bitiwsor.chessgame.abstracts.Piece;
import com.bitiwsor.chessgame.abstracts.Rule;
import com.bitiwsor.chessgame.chess.Board;
import com.bitiwsor.chessgame.chess.ChessPiece;
import com.bitiwsor.chessgame.chess.RuleFactory;
import com.bitiwsor.chessgame.enums.ColorEnum;
import com.bitiwsor.chessgame.enums.PieceEnum;

/*
 * Represents a King piece in a chess game, extending from the Piece class.
 */
public class KingRule extends Rule {
    private List<String> possibleCastleMoves = null;

    /*
     * Constructor to initialize a King piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the King belongs (White or Black).
     * position: The initial position of the King in algebraic notation (e.g., "e1").
     */
    public KingRule(Board board, Piece piece) {
        super(board,piece);
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the King piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the King.
     */
    public List<List<String>> expectedPaths() {
        List<List<String>> moves = new ArrayList<>();
        List<String> updatedMoves = removePathsThatCanHarmKing(getAllValidMoves());
        moves.add(updatedMoves);
        return moves;
    }

    @Override
    public PieceEnum pieceType() {
        return PieceEnum.King;
    }

    /*
     * Removes paths (moves) from the list that would leave the King in check.
     * Parameters:
     * moves: The list of all valid moves for the King.
     * Returns: A filtered list of moves that do not put the King in check.
     */
    private List<String> removePathsThatCanHarmKing(List<String> moves) {
        List<String> updatedPath = new ArrayList<>();
        for (String expectedPosition : moves) {
            if (checkIfExpectedPositionValid(expectedPosition)) {
                updatedPath.add(expectedPosition);
            }
        }
        return updatedPath;
    }

    /*
     * Checks if moving the King to a specific position would leave it in a safe state (not in check).
     * Parameters:
     * expectedPosition: The position to which the King is expected to move.
     * Returns: true if the King would not be in check at the expected position, false otherwise.
     */
    private boolean checkIfExpectedPositionValid(String expectedPosition) {
        Board newBoard = board.getClone();
        int i = converter.getIindex(piece.getPosition());
        int j = converter.getJindex(piece.getPosition());
        newBoard.put(i, j, null);
        Piece pc = new ChessPiece(PieceEnum.King,piece.getPlayerColor(),expectedPosition);
        if(newBoard.getPieceAtPosition(expectedPosition)!=null){
            newBoard.clear(newBoard.getPieceAtPosition(expectedPosition));
        }
        newBoard.put(expectedPosition, pc);
        return ((CheckRule)RuleFactory.getRule(newBoard,pc, CheckRule.class)).isCheck(pc.getPlayerColor(),expectedPosition) == null;
    }
    /*
     * Retrieves all valid moves for the King based on its current position.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of strings representing all valid move positions in algebraic notation for the King.
     */
    public List<String> getAllValidMoves() {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[] { 1, 1, -1, 0, 1, 0, -1, -1 };
        int[] jIndexs = new int[] { 1, -1, -1, 1, 0, -1, 0, 1 };
        int idx = converter.getIindex(piece.getPosition());
        int jdx = converter.getJindex(piece.getPosition());
        for (int x = 0; x < 8; x++) {
            int newIdx = idx + iIndexs[x];
            int newJdx = jdx + jIndexs[x];

            if (converter.isIndexSafe(newIdx, newJdx)) {
                if ((board.getPieceAtPosition(newIdx, newJdx) == null))
                    moves.add(newIdx + "" + newJdx);
                else {
                    if ((board.getPieceAtPosition(newIdx, newJdx).getPlayerColor() != piece.getPlayerColor())) {
                        moves.add(newIdx + "" + newJdx);
                    }
                }
            }
        }
        return moves;
    }

    public List<String> getCastleMovesIfPossible() {
        List<String> moves = new ArrayList<>();

        if(piece.getPlayerColor() == ColorEnum.Black){

            boolean isRook1FirstMove = false;
            boolean isRook2FirstMove = false;
            boolean isKingFirstMove = piece.isFirstMove();

            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = board.getPlayerPieces(piece.getPlayerColor());
                boolean b = true;
                for (Piece p:pieces){
                    if((p.getPieceType() == PieceEnum.Rook) && p.isFirstMove()){
                        if(b){
                            isRook1FirstMove = true;
                            b = false;
                        }
                        else isRook2FirstMove = true;
                    }
                }
                if(isRook1FirstMove){

                    String position1 = "71";
                    String position2 = "72";
                    Piece p = board.getPieceAtPosition(position1);
                    Piece p2 = board.getPieceAtPosition(position2);
                    if(p == null && p2 == null){
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2)){
                            // Castling is allowed here
                            moves.add(position1) ;
                        }
                    }

                }
                if(isRook2FirstMove){
                    String position1 = "74";
                    String position2 = "75";
                    String position3 = "76";
                    Piece p = board.getPieceAtPosition(position1);
                    Piece p2 = board.getPieceAtPosition(position2);
                    Piece p3 = board.getPieceAtPosition(position3);

                    if(p == null && p2 == null && p3 == null){
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2) ){
                            // Castling is allowed here
                            moves.add(position2) ;
                        }
                    }
                }
            }
        }
        else{
            boolean isRook1FirstMove = false;
            boolean isRook2FirstMove = false;
            boolean isKingFirstMove = piece.isFirstMove();


            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = board.getPlayerPieces(piece.getPlayerColor());
                boolean b = true;
                for (Piece p:pieces){
                    if((p.getPieceType() == PieceEnum.Rook) && p.isFirstMove()){
                        if(b){
                            isRook1FirstMove = true;
                            b = false;
                        }
                        else isRook2FirstMove = true;
                    }
                }
                if(isRook1FirstMove){

                    String position1 = "01";
                    String position2 = "02";
                    Piece p = board.getPieceAtPosition(position1);
                    Piece p2 = board.getPieceAtPosition(position2);
                    if(p == null && p2 == null){
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2)){
                            // Castling is allowed here
                            moves.add(position1) ;
                        }
                    }

                }
                if(isRook2FirstMove){
                    String position1 = "04";
                    String position2 = "05";
                    String position3 = "06";
                    Piece p = board.getPieceAtPosition(position1);
                    Piece p2 = board.getPieceAtPosition(position2);
                    Piece p3 = board.getPieceAtPosition(position3);

                    if(p == null && p2 == null && p3 == null){
                        if(checkIfExpectedPositionValid(position1) && checkIfExpectedPositionValid(position2) ){
                            // Castling is allowed here
                            moves.add(position2) ;
                        }
                    }
                }
            }
        }
        possibleCastleMoves=moves;
        return moves;
    }
}
