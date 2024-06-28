package chess;

import abstracts.Piece;
import enums.ColorEnum;
import enums.PieceEnum;
import io.PositionToIndexConverter;
import pieces.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
    private List<List<Piece>> board;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final PositionToIndexConverter converter;
    public Board(){
        initializeNewBoard();
        converter  = new PositionToIndexConverter();
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        updatePieceList();
    }
    private Board(List<List<Piece>> board){
        this.board = board;
        converter  = new PositionToIndexConverter();
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
        updatePieceList();
    }
    /*
     * Parameters:
     * position: The location of the piece in internal chess notation (e.g., "01" stands for "g1").
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceAtPosition(String position){
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        return getPieceAtPosition(i,j);
    }
    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * Returns: The Piece object located at the specified position on the board, or null if no piece is present.
     */
    public Piece getPieceAtPosition(int i,int j){
        if(converter.isIndexSafe(i,j)){
            return board.get(i).get(j);
        }
        return null;
    }
    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * Returns: A boolean value indicating whether the specified position on the board is empty (true) or occupied (false).
     */
    public boolean isPositionEmptyInBoard(int i,int j){
        if(converter.isIndexSafe(i,j)){
            return board.get(i).get(j) == null;
        }
        return false;
    }
    /*
     * Returns: An object of Board class representing a clone of the current state of the board.
     */

    /*
     * Returns: void. This method does not return a value.
     * Description: Updates the list of pieces on the board to reflect the current state of the game.
     */
    public void updatePieceList(){
        whitePieces.clear();
        blackPieces.clear();
        for(List<Piece> row:board){
            seggregatePiece(row);
        }
    }
    /*
     * Parameters:
     * playerEnum: The player (PlayerEnum) whose king piece is to be retrieved.
     * Returns: The King object representing the specified player's king.
     */
    public Piece getKing(ColorEnum colorEnum){
        List<Piece> pieces = getPlayerPieces(colorEnum);
        for(Piece p:pieces){
            if (p.getPieceType() == PieceEnum.King){
                return p;
            }
        }
        return null;
    }
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose pieces are to be retrieved.
     * Returns: A list of Piece objects representing all the pieces belonging to the specified player.
     */
    public List<Piece> getPlayerPieces(ColorEnum playerColor){
        if(playerColor == ColorEnum.White){
            return whitePieces;
        }
        else if(playerColor == ColorEnum.Black){
            return blackPieces;
        }
        return null;
    }
    /*
     * Parameters:
     * p: The Piece object to be removed from the board.
     * Returns: void. This method does not return a value.
     */
    public void clear(Piece p) {
        List<Piece> pieces = getPlayerPieces(p.getPlayerColor());
        pieces.remove(p);
        put(p.getPosition(),null);
        updatePieceList();
    }
    /*
     * Parameters:
     * position: The location on the board where the piece is to be set, in internal chess notation (e.g., "01" "g1").
     * piece: The Piece object to be placed on the board at the specified position.
     * Returns: void. This method does not return a value.
     */
    public void put(String position,Piece piece){
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        board.get(i).set(j,piece);
        updatePieceList();
    }
    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * piece: The Piece object to be placed on the board at the specified position.
     * Returns: void. This method does not return a value.
     */
    public void put(int i,int j,Piece piece){
        if(converter.isIndexSafe(i,j)){
            board.get(i).set(j,piece);
        }
        else throw new IndexOutOfBoundsException();
    }
    /*
     * Parameters:
     * pc: The Piece object to be segregated into the appropriate player's collection.
     * Returns: void. This method does not return a value.
     */
    public void seggregatePiece(Piece pc){
        if(pc == null) return;
        if(pc.getPlayerColor() == ColorEnum.White){
            whitePieces.add(pc);
        }
        else if(pc.getPlayerColor() == ColorEnum.Black){
            blackPieces.add(pc);
        }
    }
    /*
     * Parameters:
     * pcs: A list of Piece objects to be segregated into the appropriate player's collection.
     * Returns: void. This method does not return a value.
     */
    public void seggregatePiece(List<Piece> pcs){
        for (Piece pc : pcs) {
            seggregatePiece(pc);
        }
    }
    private List<List<Piece>> getBoardListClone() {
        List<List<Piece>> newBoard = new ArrayList<>();
        for(List<Piece> row:board){
            List<Piece> list = new ArrayList<>();
            for(Piece p : row){
                list.add(p);
            }
            newBoard.add(list);
        }
        return newBoard;
    }
    private void initializeNewBoard() {
        board = new ArrayList<>();
        // white pieces
        List<Piece> whitePieces = getWhitePieces();
        board.add(whitePieces);
        List<Piece> whitePawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            whitePawns.add(new ChessPiece(PieceEnum.Pawn,ColorEnum.White, "1"+i));
        }
        board.add(whitePawns);
        // empty pieces
        for(int j = 2; j<6 ; j++){
            List<Piece> emptyPlaces = new ArrayList<>();
            for(int i = 0 ; i< 8; i++){
                emptyPlaces.add(null);
            }
            board.add(emptyPlaces);
        }
        // black pawn pieces
        List<Piece> blackPawns = new ArrayList<>();
        for(int i = 0; i<8 ; i++){
            blackPawns.add(new ChessPiece(PieceEnum.Pawn,ColorEnum.Black, "6"+i));
        }
        board.add(blackPawns);
        // Black pieces
        List<Piece> blackPieces = getBlackPieceList();
        board.add(blackPieces);
    }
    private List<Piece> getBlackPieceList() {
        Piece blackRook1 = new ChessPiece(PieceEnum.Rook,ColorEnum.Black, "70");
        Piece blackRook2 = new ChessPiece(PieceEnum.Rook,ColorEnum.Black, "77");
        Piece blackKnight1 = new ChessPiece(PieceEnum.Knight,ColorEnum.Black, "71");
        Piece blackKnight2 = new ChessPiece(PieceEnum.Knight,ColorEnum.Black, "76");
        Piece blackBishop1 = new ChessPiece(PieceEnum.Bishop,ColorEnum.Black, "72");
        Piece blackBishop2 = new ChessPiece(PieceEnum.Bishop,ColorEnum.Black, "75");
        Piece blackKing = new ChessPiece(PieceEnum.King,ColorEnum.Black, "73");
        Piece blackQueen = new ChessPiece(PieceEnum.Queen,ColorEnum.Black, "74");
        List<Piece> blackPieces = new ArrayList<>();
        blackPieces.add(blackRook1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackKing);
        blackPieces.add(blackQueen);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackRook2);
        return blackPieces;
    }
    private List<Piece> getWhitePieces() {
        Piece whiteRook1 = new ChessPiece(PieceEnum.Rook,ColorEnum.White, "00");
        Piece whiteRook2 = new ChessPiece(PieceEnum.Rook,ColorEnum.White, "07");
        Piece whiteKnight1 = new ChessPiece(PieceEnum.Knight,ColorEnum.White, "01");
        Piece whiteKnight2 = new ChessPiece(PieceEnum.Knight,ColorEnum.White, "06");
        Piece whiteBishop1 = new ChessPiece(PieceEnum.Bishop,ColorEnum.White, "02");
        Piece whiteBishop2 = new ChessPiece(PieceEnum.Bishop,ColorEnum.White, "05");
        Piece whiteKing = new ChessPiece(PieceEnum.King,ColorEnum.White, "03");
        Piece whiteQueen = new ChessPiece(PieceEnum.Queen,ColorEnum.White, "04");
        List<Piece> whitePieces = new ArrayList<>();
        whitePieces.add(whiteRook1);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteRook2);
        return whitePieces;
    }
    public List<List<Piece>> getBoard() {
        return board;
    }
    public Board getClone(){
        Board b = new Board(getBoardListClone());
        return b;
    }
    /*
     * Returns: A list of strings representing the positions that are common between both kings on the board.
     */
    public List<String> getPointsCommonInBothKing(){
        Piece whiteKing = getKing(ColorEnum.White);
        Piece blackKing = getKing(ColorEnum.Black);
        KingRule whiteKingrule = (KingRule)RuleFactory.getRule(this,whiteKing);
        KingRule blackKingRule = (KingRule)RuleFactory.getRule(this,whiteKing);

        List<String> whiteKingMoves = whiteKingrule.getAllValidMoves();
        List<String> blackKingMoves = blackKingRule.getAllValidMoves();
        Set<String> commonMoves = new HashSet<>(whiteKingMoves);
        commonMoves.retainAll(blackKingMoves);
        return new ArrayList<>(commonMoves);
    }

    /*
     * Parameters:
     * playerEnum: The player (PlayerEnum) whose opposite player color is to be retrieved.
     * Returns: The PlayerEnum value representing the opposite player color.
     */
    public ColorEnum getOppositePlayerColor(ColorEnum colorEnum){
        if(colorEnum == ColorEnum.Black){
            return ColorEnum.White;
        }
        return ColorEnum.Black;
    }
    /*
     * Parameters:
     * checkPiecePath: A list of strings representing the path of the checking piece.
     * playerColor: The color of the player (PlayerEnum) whose pieces are being checked.
     *
     * Returns: A boolean value indicating whether any pieces can resolve the check condition (true) or not (false).
     */
    public boolean allowPiecesThatCanResolveCheck(List<String> checkPiecePath, ColorEnum playerColor, ChessGame cg) {
        List<Piece> pieces = getPlayerPieces(playerColor);
        boolean allowed = false;
        for(Piece p:pieces){
            List<List<String>> expectedPaths = RuleFactory.getRule(this,p).expectedPaths();
            for (List<String> path:expectedPaths){
                for(String move:path){
                    if(checkPiecePath.contains(move)){
                        p.setCanMove(true);
                        allowed = true;
                    }
                }
            }
        }
        return allowed;
    }
    /*
     * Parameters:
     * playerChance: The color of the player (PlayerEnum) whose legal moves are to be checked.
     * cg: The ChessGame object representing the current state of the game.
     * Returns: A boolean value indicating whether the specified player has any legal moves (true) or not (false).
     */
    public boolean isPlayerHaveAnyLegalMove(ColorEnum playerChance) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for (Piece p:pieces){
            List<List<String>> expectedPaths = RuleFactory.getRule(this,p).expectedPaths();
            for (List<String> path:expectedPaths){
                if(!path.isEmpty()){
                    return true;
                }
            }
        }
        return false;
    }
    /*
     * Parameters:
     * playerChance: The color of the player (PlayerEnum) whose pieces are to be allowed to move.
     * Returns: void. This method does not return a value.
     */
    public void allowAllPiecesToMove(ColorEnum playerChance) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for(Piece piece:pieces){
            if(!(piece.getPieceType() == PieceEnum.King)){
                piece.setCanMove(true);
            }
        }
    }

}
