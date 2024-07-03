package io.github.shivenducs1136.io.converters;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessPiece;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.PieceEnum;
import java.util.ArrayList;
import java.util.List;

public class BoardConverter {
    static String board = "RNBKQBNR,PPPPPPPP,........,........,........,........,pppppppp,rnbkqbnr";

    public List<List<Piece>> convertStringToBoard(String boardString) {
        List<List<Piece>> newBoard = new ArrayList<>();
        List<String> newBoardString = List.of(board.split(","));
        for (int i = 0; i < newBoardString.size(); i++) {
            List<Piece> tempBoard = new ArrayList<>();
            for (int j = 0; j < newBoardString.get(i).length(); j++) {
                char currentChar = newBoardString.get(i).charAt(j);
                Piece piece = null;
                switch (currentChar) {
                    case '.' -> piece = null;
                    case 'R' -> piece = new ChessPiece(PieceEnum.Rook, ColorEnum.White, i + "" + j);
                    case 'N' -> piece = new ChessPiece(PieceEnum.Knight, ColorEnum.White, i + "" + j);
                    case 'B' -> piece = new ChessPiece(PieceEnum.Bishop, ColorEnum.White, i + "" + j);
                    case 'Q' -> piece = new ChessPiece(PieceEnum.Queen, ColorEnum.White, i + "" + j);
                    case 'K' -> piece = new ChessPiece(PieceEnum.King, ColorEnum.White, i + "" + j);
                    case 'P' -> piece = new ChessPiece(PieceEnum.Pawn, ColorEnum.White, i + "" + j);
                    case 'r' -> piece = new ChessPiece(PieceEnum.Rook, ColorEnum.Black, i + "" + j);
                    case 'n' -> piece = new ChessPiece(PieceEnum.Knight, ColorEnum.Black, i + "" + j);
                    case 'b' -> piece = new ChessPiece(PieceEnum.Bishop, ColorEnum.Black, i + "" + j);
                    case 'q' -> piece = new ChessPiece(PieceEnum.Queen, ColorEnum.Black, i + "" + j);
                    case 'k' -> piece = new ChessPiece(PieceEnum.King, ColorEnum.Black, i + "" + j);
                    case 'p' -> piece = new ChessPiece(PieceEnum.Pawn, ColorEnum.Black, i + "" + j);
                    default -> {
                    }
                }
                tempBoard.add(piece);
            }
            newBoard.add(tempBoard);
        }
        return newBoard;
    }

    public String ConvertBoardToString(List<List<Piece>> board)
    {

        String newBoard="";


        for(int i=0;i< board.size();i++)
        {

            for(int j=0;j<board.size();j++)
            {
                Piece currentPiece = board.get(i).get(j);
                if(currentPiece != null)
                {
                    switch(currentPiece.getPieceType())
                    {
                        case Rook:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+='R';
                                    break;
                                case Black:
                                    newBoard+='r';
                                    break;
                                default:
                                    break;
                            }
                        case King:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+="K";
                                    break;
                                case Black:
                                    newBoard+="k";
                                    break;
                                default:
                                    break;
                            }
                        case Queen:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+="Q";
                                    break;
                                case Black:
                                    newBoard+="q";
                                    break;
                                default:
                                    break;
                            }
                        case Bishop:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+="B";
                                    break;
                                case Black:
                                    newBoard+="b";
                                    break;
                                default:
                                    break;
                            }
                        case Pawn:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+="P";
                                    break;
                                case Black:
                                    newBoard+="p";
                                    break;
                                default:
                                    break;
                            }
                        case Knight:
                            switch(currentPiece.getPlayerColor())
                            {
                                case White:
                                    newBoard+="N";
                                    break;
                                case Black:
                                    newBoard+="n";
                                    break;
                                default:
                                    break;
                            }
                    }
                }
                else
                    newBoard+=".";
            }
        }

    return newBoard;
    }
}
