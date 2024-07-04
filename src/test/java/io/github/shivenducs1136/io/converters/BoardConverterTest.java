package io.github.shivenducs1136.io.converters;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessPiece;
import io.github.shivenducs1136.enums.PieceEnum;
import io.github.shivenducs1136.enums.ColorEnum;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class BoardConverterTest extends TestCase {



        public void testConvertStringToBoard_EmptyBoard() {
            String boardString = "........,........,........,........,........,........,........,........";
            List<List<Piece>> board = BoardConverter.convertStringToBoard(boardString);

            for (List<Piece> row : board) {
                for (Piece piece : row) {
                    assertNull(piece);
                }
            }
        }


        public void testConvertStringToBoard_InitialSetup() {
            String boardString = "rnbqkbnr,pppppppp,........,........,........,........,PPPPPPPP,RNBQKBNR";
            List<List<Piece>> board = BoardConverter.convertStringToBoard(boardString);

            // Test a few specific pieces to ensure correct parsing
            assertTrue(board.get(0).get(0) instanceof ChessPiece); // r
            assertEquals(PieceEnum.Rook, (board.get(0).get(0)).getPieceType());
            assertEquals(ColorEnum.Black, (board.get(0).get(0)).getPlayerColor());

            assertTrue(board.get(7).get(7) instanceof ChessPiece); // R
            assertEquals(PieceEnum.Rook, (board.get(7).get(7)).getPieceType());
            assertEquals(ColorEnum.White, (board.get(7).get(7)).getPlayerColor());

            assertTrue(board.get(1).get(0) instanceof ChessPiece); // p
            assertEquals(PieceEnum.Pawn, (board.get(1).get(0)).getPieceType());
            assertEquals(ColorEnum.Black, (board.get(1).get(0)).getPlayerColor());

            assertTrue(board.get(6).get(0) instanceof ChessPiece); // P
            assertEquals(PieceEnum.Pawn, (board.get(6).get(0)).getPieceType());
            assertEquals(ColorEnum.White, (board.get(6).get(0)).getPlayerColor());

        }


        public void testConvertStringToBoard_PartialSetup() {
            String boardString = "........,........,........,....q...,........,........,........,........";
            List<List<Piece>> board = BoardConverter.convertStringToBoard(boardString);

            // Test specific piece
            assertNull(board.get(0).get(0));
            assertTrue(board.get(3).get(4) instanceof ChessPiece); // q
            assertEquals(PieceEnum.Queen, ((ChessPiece) board.get(3).get(4)).getPieceType());
            assertEquals(ColorEnum.Black, ((ChessPiece) board.get(3).get(4)).getPlayerColor());
        }


//    public void testConvertBoardToString() {
//    }
}