package io.github.shivenducs1136.io.converters;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessPiece;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;
import java.util.ArrayList;
import java.util.List;

public class BoardConverter {

    /*
    * Your tasks
    *  1-> Write Unit test cases for these functions
    *  2-> Add comments
    *  3-> Integrate these functions in ChessEngine class
    *  4-> Change function parameters as discussed.
    * */




    public List<List<Piece>> convertStringToBoard(String boardString) {

        List<List<Piece>> newBoard = new ArrayList<>();
        List<String> newBoardString = List.of(boardString.split(","));
        for (int i = 0; i < newBoardString.size(); i++) {
            List<Piece> tempBoard = new ArrayList<>();
            for (int j = 0; j < newBoardString.get(i).length(); j++) {
                char currentChar = newBoardString.get(i).charAt(j);
                Piece piece = null;
                var pieceEnums = PieceEnum.values();

                for(PieceEnum p : pieceEnums){
                    if(p.getPieceChar == currentChar){
                        if(Character.isUpperCase(currentChar)){
                            // white
                            piece = new ChessPiece(p,ColorEnum.White,i+""+j);
                        }
                        else{
                            // black
                            piece = new ChessPiece(p,ColorEnum.Black, i+""+j);
                        }
                    }
                }
                tempBoard.add(piece);
            }
            newBoard.add(tempBoard);
        }
        return newBoard;
    }
    public String convertBoardToString(List<List<Piece>> board)
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
        }

    return newBoard;
    }
}
