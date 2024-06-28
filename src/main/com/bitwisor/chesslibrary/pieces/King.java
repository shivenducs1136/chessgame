package pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

/*
 * Represents a King piece in a chess game, extending from the Piece class.
 */
public class King extends Piece {
    private List<String> possibleCastleMoves = null;

    /*
     * Constructor to initialize a King piece with its player and initial position.
     * Parameters:
     * playerEnum: The player (PlayerEnum) to which the King belongs (White or Black).
     * position: The initial position of the King in algebraic notation (e.g., "e1").
     */
    public King(PlayerEnum playerEnum, String position) {
        player = playerEnum;
        this.position = position;
        isKilled = false;
        canMove = true;
    }

    /*
     * Calculates and returns the expected paths (valid moves) for the King piece.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of lists of strings representing valid move paths in algebraic notation for the King.
     */
    public List<List<String>> expectedPaths(PieceManager pieceManager) {
        this.pieceManager = pieceManager;
        List<List<String>> moves = new ArrayList<>();
        List<String> updatedMoves = removePathsThatCanHarmKing(getAllValidMoves(pieceManager));
        moves.add(updatedMoves);
        return moves;
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
        List<List<Piece>> newBoard = pieceManager.getBoardClone();
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        PieceManager newPm = new PieceManager(newBoard);
        newPm.setPieceInBoard(i, j, null);
        newPm.setPieceInBoard(expectedPosition, new King(player, expectedPosition));
        newPm.updatePieceList();
        return newPm.isCheck(player, expectedPosition) == null;
    }

    /*
     * Retrieves all valid moves for the King based on its current position.
     * Parameters:
     * pieceManager: The PieceManager object managing all pieces on the board.
     * Returns: A list of strings representing all valid move positions in algebraic notation for the King.
     */
    public List<String> getAllValidMoves(PieceManager pieceManager) {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[] { 1, 1, -1, 0, 1, 0, -1, -1 };
        int[] jIndexs = new int[] { 1, -1, -1, 1, 0, -1, 0, 1 };
        int idx = converter.getIindex(position);
        int jdx = converter.getJindex(position);
        for (int x = 0; x < 8; x++) {
            int newIdx = idx + iIndexs[x];
            int newJdx = jdx + jIndexs[x];

            if (converter.isIndexSafe(newIdx, newJdx)) {
                if ((pieceManager.getPieceAtPosition(newIdx, newJdx) == null))
                    moves.add(newIdx + "" + newJdx);
                else {
                    if ((pieceManager.getPieceAtPosition(newIdx, newJdx).getPlayer() != player)) {
                        moves.add(newIdx + "" + newJdx);
                    }
                }
            }
        }
        return moves;
    }

    /*
     * Retrieves the current movable state of the King.
     * Returns: true if the King can move, false otherwise.
     */
    public boolean getCanMove() {
        return canMove;
    }

    /*
     * Retrieves the current position of the King.
     * Returns: The position of the King in algebraic notation (e.g., "e1").
     */
    public String getPosition() {
        return position;
    }

    /*
     * Updates the position of the King on the chessboard.
     * Parameters:
     * position: The new position of the King in algebraic notation (e.g., "e1").
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /*
     * Updates the movable state of the King.
     * Parameters:
     * canMove: true to allow the King to move, false to restrict movement.
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /*
     * Retrieves the player (PlayerEnum) to which the King belongs.
     * Returns: The PlayerEnum value representing the player of the King (White or Black).
     */
    public PlayerEnum getPlayer() {
        return player;
    }

    /*
     * Checks if the King has been killed (captured).
     * Returns: true if the King has been killed, false otherwise.
     */
    public boolean isKilled() {
        return isKilled;
    }

    /*
     * Updates the killed state of the King.
     * Parameters:
     * isKilled: true if the King is killed (captured), false otherwise.
     */
    public void setIsKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }

    /*
     * Retrieves the list of castle moves available to the King.
     * Returns: A list of strings representing the positions for possible castle moves in algebraic notation.
     */
    public List<String> getCastleMoves() {
        return possibleCastleMoves;
    }

    /*
     * Determines and retrieves the castle moves available to the King based on the current board state.
     * Parameters:
     * board: The current state of the chessboard represented as a list of lists of pieces.
     * Returns: A list of strings representing the positions for possible castle moves in algebraic notation.
     */
    public List<String> getCastleMovesIfPossible() {
        List<String> moves = new ArrayList<>();

        if(player == PlayerEnum.Black){

            boolean isRook1FirstMove = false;
            boolean isRook2FirstMove = false;
            boolean isKingFirstMove = isFirstMove;

            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pieceManager.getPlayerPieces(player);
                boolean b = true;
                for (Piece p:pieces){
                    if((p instanceof Rook) && p.isFirstMove()){
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    Piece p3 = pieceManager.getPieceAtPosition(position3);

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
            boolean isKingFirstMove = isFirstMove;


            // check for roo1 and rook2 first move
            if(isKingFirstMove){
                List<Piece> pieces = pieceManager.getPlayerPieces(player);
                boolean b = true;
                for (Piece p:pieces){
                    if((p instanceof Rook) && p.isFirstMove()){
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
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
                    Piece p = pieceManager.getPieceAtPosition(position1);
                    Piece p2 = pieceManager.getPieceAtPosition(position2);
                    Piece p3 = pieceManager.getPieceAtPosition(position3);

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
