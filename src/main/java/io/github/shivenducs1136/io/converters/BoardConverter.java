package io.github.shivenducs1136.io.converters;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessPiece;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;
import java.util.ArrayList;
import java.util.List;


/**
 * The BoardConverter class provides methods to convert between
 * a string representation of a chess board and a list of lists of Piece objects.
 */
public class BoardConverter {

    /*
    * Your tasks
    *  1-> Write Unit test cases for these functions -
    *  2-> Add comments -
    *  3-> Integrate these functions in ChessEngine class
    *  4-> Change function parameters as discussed.
    * */
    /**
     * Converts a string representation of a chess board to a 2D list of Piece objects.
     *
     * @param boardString the string representation of the board, with rows separated by commas
     *                    and each piece represented by a single character (e.g., 'r' for black rook,
     *                    'P' for white pawn, '.' for empty square).
     * @return a 2D list of Piece objects representing the chess board.
     */
    public static List<List<Piece>> convertStringToBoard(String boardString) {

        List<List<Piece>> newBoard = new ArrayList<>();
        List<String> newBoardString = List.of(boardString.split(","));
        for (int i = 0; i < newBoardString.size(); i++) {
            List<Piece> tempBoard = new ArrayList<>();
            for (int j = 0; j < newBoardString.get(i).length(); j++) {
                char currentChar = newBoardString.get(i).charAt(j);
                Piece piece = null;
                var pieceEnums = PieceEnum.values();

                for(PieceEnum piEnum : pieceEnums){
                    if((piEnum.getPieceChar + "").equalsIgnoreCase((currentChar + ""))){
                        if(Character.isUpperCase(currentChar)){
                            // white
                            piece = new ChessPiece(piEnum,ColorEnum.White,i+""+j);
                        }
                        else if(currentChar!= '.'){
                            // black
                            piece = new ChessPiece(piEnum,ColorEnum.Black, i+""+j);
                        }
                    }
                }
                tempBoard.add(piece);
            }
            newBoard.add(tempBoard);
        }
        return newBoard;
    }
    /**
     * Converts a 2D list of Piece objects to a string representation of a chess board.
     *
     * @param board the 2D list of Piece objects representing the chess board.
     * @return the string representation of the board, with rows separated by commas
     *         and each piece represented by a single character (e.g., 'r' for black rook,
     *         'P' for white pawn, '.' for empty square).
     */
    public static String convertBoardToString(List<List<Piece>> board)
    {

        String newBoard="";

        for(int i=0;i< board.size();i++)
        {
            for(int j=0;j<board.size();j++)
            {
                Piece currentPiece = board.get(i).get(j);
                if(currentPiece != null)
                {
                   newBoard += currentPiece.toString();
                }
                else
                {
                    newBoard += ".";
                }
            }
            if(i< board.size()-1)
                newBoard+=",";
        }

    return newBoard;
    }
}
