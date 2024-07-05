package io.github.shivenducs1136.io.converters;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessPiece;
import io.github.shivenducs1136.enums.PieceEnum;
import io.github.shivenducs1136.enums.ColorEnum;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
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
            assertEquals(PieceEnum.Queen, (board.get(3).get(4)).getPieceType());
            assertEquals(ColorEnum.Black, (board.get(3).get(4)).getPlayerColor());
        }
    public void testConvertBoardToString_EmptyBoard() {
        List<List<Piece>> board = createEmptyBoard();
        String boardString = BoardConverter.convertBoardToString(board);
        assertEquals("........,........,........,........,........,........,........,........", boardString);
    }


    public void testConvertBoardToString_InitialSetup() {
        List<List<Piece>> board = createInitialBoard();
        String boardString = BoardConverter.convertBoardToString(board);
        assertEquals("rnbqkbnr,pppppppp,........,........,........,........,PPPPPPPP,RNBQKBNR", boardString);
    }


    public void testConvertBoardToString_PartialSetup() {
        List<List<Piece>> board = createPartialBoard();
        String boardString = BoardConverter.convertBoardToString(board);
        assertEquals("........,........,........,....q...,........,........,........,........", boardString);
    }


    private List<List<Piece>> createEmptyBoard() {
        List<List<Piece>> board = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            List<Piece> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                row.add(null);
            }
            board.add(row);
        }
        return board;
    }


    private List<List<Piece>> createInitialBoard() {
        List<List<Piece>> board = createEmptyBoard();

        // Add pawns
        for (int i = 0; i < 8; i++) {
            board.get(1).set(i, new ChessPiece(PieceEnum.Pawn, ColorEnum.Black, "1" + i));
            board.get(6).set(i, new ChessPiece(PieceEnum.Pawn, ColorEnum.White, "6" + i));
        }

        // Add rooks
        board.get(0).set(0, new ChessPiece(PieceEnum.Rook, ColorEnum.Black, "00"));
        board.get(0).set(7, new ChessPiece(PieceEnum.Rook, ColorEnum.Black, "07"));
        board.get(7).set(0, new ChessPiece(PieceEnum.Rook, ColorEnum.White, "70"));
        board.get(7).set(7, new ChessPiece(PieceEnum.Rook, ColorEnum.White, "77"));

        // Add King

        board.get(7).set(4, new ChessPiece(PieceEnum.King, ColorEnum.White, "74"));
        board.get(0).set(4, new ChessPiece(PieceEnum.King, ColorEnum.Black, "04"));

        //Add Queen

        board.get(7).set(3, new ChessPiece(PieceEnum.Queen, ColorEnum.White, "73"));
        board.get(0).set(3, new ChessPiece(PieceEnum.Queen, ColorEnum.Black, "04"));

        //Add Bishop

        board.get(7).set(2, new ChessPiece(PieceEnum.Bishop, ColorEnum.White, "72"));
        board.get(7).set(5, new ChessPiece(PieceEnum.Bishop, ColorEnum.White, "75"));
        board.get(0).set(2, new ChessPiece(PieceEnum.Bishop, ColorEnum.Black, "02"));
        board.get(0).set(5, new ChessPiece(PieceEnum.Bishop, ColorEnum.Black, "05"));


        //Add Knight

        board.get(7).set(1, new ChessPiece(PieceEnum.Knight, ColorEnum.White, "71"));
        board.get(7).set(6, new ChessPiece(PieceEnum.Knight, ColorEnum.White, "76"));
        board.get(0).set(1, new ChessPiece(PieceEnum.Knight, ColorEnum.Black, "01"));
        board.get(0).set(6, new ChessPiece(PieceEnum.Knight, ColorEnum.Black, "06"));


        return board;
    }

    private List<List<Piece>> createPartialBoard() {
        List<List<Piece>> board = createEmptyBoard();
        board.get(3).set(4, new ChessPiece(PieceEnum.Queen, ColorEnum.Black, "34"));
        return board;
    }


}