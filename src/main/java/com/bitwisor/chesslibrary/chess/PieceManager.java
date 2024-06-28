package chess;

import abstracts.Piece;
import enums.GameStateEnum;
import enums.PieceEnum;
import enums.PlayerEnum;
import io.PositionToIndexConverter;
import pieces.*;

import java.util.*;

public class PieceManager {
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private final PositionToIndexConverter converter;
    private final List<List<Piece>> board;
    public PieceManager(List<List<Piece>> board){
        converter = new PositionToIndexConverter();
        this.board = board;
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();
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
        return board.get(i).get(j) == null;
    }
    /*
     * Parameters:
     * pc: The Piece object to be segregated into the appropriate player's collection.
     * Returns: void. This method does not return a value.
     */
    public void seggregatePiece(Piece pc){
        if(pc == null) return;
        if(pc.getPlayer() == PlayerEnum.White){
            whitePieces.add(pc);
        }
        else if(pc.getPlayer() == PlayerEnum.Black){
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
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose pieces are to be retrieved.
     * Returns: A list of Piece objects representing all the pieces belonging to the specified player.
     */
    public List<Piece> getPlayerPieces(PlayerEnum playerColor){
        if(playerColor == PlayerEnum.White){
            return whitePieces;
        }
        else if(playerColor == PlayerEnum.Black){
            return blackPieces;
        }
        return null;
    }
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose pieces are to be restricted due to a check condition.
     * Returns: void. This method does not return a value.
     */
    public void restrictPiecesOnCheck(PlayerEnum playerColor) {
        List<Piece> pieces = getPlayerPieces(playerColor);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(false);
            }
        }

    }
    /*
     * Returns: A list of strings representing the positions that are common between both kings on the board.
     */
    public List<String> getPointsCommonInBothKing(){
        King whiteKing = getKing(PlayerEnum.White);
        King blackKing = getKing(PlayerEnum.Black);
        List<String> whiteKingMoves = whiteKing.getAllValidMoves(this);
        List<String> blackKingMoves = blackKing.getAllValidMoves(this);
        Set<String> commonMoves = new HashSet<>(whiteKingMoves);
        commonMoves.retainAll(blackKingMoves);
        return new ArrayList<>(commonMoves);
    }
    /*
     * Parameters:
     * playerEnum: The player (PlayerEnum) whose king piece is to be retrieved.
     * Returns: The King object representing the specified player's king.
     */
    public King getKing(PlayerEnum playerEnum){
        List<Piece> pieces = getPlayerPieces(playerEnum);
        for(Piece p:pieces){
            if (p instanceof King){
                return (King)p;
            }
        }
        return null;
    }
    /*
     * Parameters:
     * playerEnum: The player (PlayerEnum) whose opposite player color is to be retrieved.
     * Returns: The PlayerEnum value representing the opposite player color.
     */
    public PlayerEnum getOppositePlayerColor(PlayerEnum playerEnum){
        if(playerEnum == PlayerEnum.Black){
            return PlayerEnum.White;
        }
        return PlayerEnum.Black;
    }
    /*
     * Parameters:
     * checkPiecePath: A list of strings representing the path of the checking piece.
     * playerColor: The color of the player (PlayerEnum) whose pieces are being checked.
     *
     * Returns: A boolean value indicating whether any pieces can resolve the check condition (true) or not (false).
     */
    public boolean allowPiecesThatCanResolveCheck(List<String> checkPiecePath,PlayerEnum playerColor, ChessGame cg) {
        List<Piece> pieces = getPlayerPieces(playerColor);
        boolean allowed = false;
        for(Piece p:pieces){
            List<List<String>> expectedPaths = p.expectedPaths(this);
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
    public boolean isPlayerHaveAnyLegalMove(PlayerEnum playerChance) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for (Piece p:pieces){
            List<List<String>> expectedPaths = p.expectedPaths(this);
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
    public void allowAllPiecesToMove(PlayerEnum playerChance) {
        List<Piece> pieces = getPlayerPieces(playerChance);
        for(Piece piece:pieces){
            if(!(piece instanceof King)){
                piece.setCanMove(true);
            }
        }
    }
    /*
     * Parameters:
     * playerColor: The color of the player (PlayerEnum) whose check condition is to be evaluated.
     * currentKingPosition: The current position of the king in standard chess notation (e.g., "e1").
     * Returns: A list of strings representing the positions that put the specified player's king in check.
     */
    public List<String> isCheck(PlayerEnum playerColor,String currentKingPosition){
        List<Piece> oppositePlayerPieces = getPlayerPieces(getOppositePlayerColor(playerColor));
        List<String> checkPiecePath = new ArrayList<>();
        for (Piece oppPlayerPiece : oppositePlayerPieces) {
            if(oppPlayerPiece instanceof King k){
                List<String> commonPoints = getPointsCommonInBothKing();
                if(commonPoints.contains(currentKingPosition)){
                    checkPiecePath.add(currentKingPosition);
                }
            }
            else{
                List<List<String>> playerMoves = oppPlayerPiece.expectedPaths(this);
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
    public List<String> isCheck(PlayerEnum playerColor){
        String currentKingPosition = getKing(playerColor).getPosition();
        return isCheck(playerColor,currentKingPosition);
    }
    /*
     * Parameters:
     * p: The Piece object to be removed from the board.
     * Returns: void. This method does not return a value.
     */
    public void removePiece(Piece p) {
        List<Piece> pieces = getPlayerPieces(p.getPlayer());
        pieces.remove(p);
    }
    /*
     * Parameters:
     * position: The location on the board where the piece is to be set, in internal chess notation (e.g., "01" "g1").
     * piece: The Piece object to be placed on the board at the specified position.
     * Returns: void. This method does not return a value.
     */
    public void setPieceInBoard(String position,Piece piece){
        int i = converter.getIindex(position);
        int j = converter.getJindex(position);
        board.get(i).set(j,piece);
    }
    /*
     * Parameters:
     * i: The row index of the board, starting from 0.
     * j: The column index of the board, starting from 0.
     * piece: The Piece object to be placed on the board at the specified position.
     * Returns: void. This method does not return a value.
     */
    public void setPieceInBoard(int i,int j,Piece piece){
        if(converter.isIndexSafe(i,j)){
            board.get(i).set(j,piece);
        }
        else throw new IndexOutOfBoundsException();
    }
    /*
     * Returns: A two-dimensional list representing a clone of the current state of the board.
     */
    public List<List<Piece>> getBoardClone() {
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
    /*
     * Returns: void. This method does not return a value.
     * Description: Updates the list of pieces on the board to reflect the current state of the game.
     */
    public void updatePieceList(){
        for(List<Piece> row:board){
            seggregatePiece(row);
        }
    }
}
