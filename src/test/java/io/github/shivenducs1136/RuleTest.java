package io.github.shivenducs1136;


import io.github.shivenducs1136.abstracts.ChessCallback;
import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessEngine;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.enums.GameStateEnum;
import io.github.shivenducs1136.enums.PieceEnum;
import io.github.shivenducs1136.exceptions.InvalidMoveException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RuleTest  {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private ChessEngine game;

    @Before
    public void setup() {
        Locale.setDefault(Locale.US);
        newGame();
    }

    private void newGame() {
        game = new ChessEngine(new ChessCallback() {
            @Override
            public PieceEnum getSelectedPieceForPawnUpgrade() {
                return PieceEnum.Queen;
            }
        });
    }
    private void newGame(ColorEnum chance) {
        game = new ChessEngine(new ChessCallback() {
            @Override
            public PieceEnum getSelectedPieceForPawnUpgrade() {
                return PieceEnum.Queen;
            }
        });
        game.setCurrentChanceColor(chance);
    }

    @Test
    public void newChessGame() {
        assertThat(game.getCurrentPlayer(), equalTo(ColorEnum.White));
    }
    @Test
    public void validWhiteMove() throws Exception {
        move('e',2,'e',4);

        assertThat(game.getCurrentPlayer(), equalTo(ColorEnum.Black));
    }
    @Test
    public void validBlackMove() throws Exception {
        move('e',2,'e',4);
        move('a', 7, 'a', 6);
        move('a',2, 'a',3);
        assertThat(game.getCurrentPlayer(), equalTo(ColorEnum.Black));
    }

    @Test
    public void invalidMove() throws Exception {
        exception.expect(InvalidMoveException.class);
        move('a', 2, 'b', 3);
    }

    @Test
    public void itsNotYourTurn() throws Exception {
        exception.expect(InvalidMoveException.class);
        move('b', 7, 'b', 6);
    }

    @Test
    public void chooseWhitePiece() throws Exception {
        move('b', 2, 'b', 3);
        assertThat(game.getCurrentPlayer(), equalTo(ColorEnum.Black));
    }

    @Test
    public void chooseBlackPiece() throws Exception {
        newGame(ColorEnum.Black);
        move('a', 7, 'a', 6);
        assertThat(game.getCurrentPlayer(), equalTo(ColorEnum.White));
    }

    @Test
    public void canNotMoveTwiceSameColor() throws Exception {
        move('b', 2, 'b', 3);
        exception.expect(Exception.class);
        move('b', 2, 'b', 3);

    }

    @Test
    public void kingSideCastling() throws Exception {
        move('e',2,'e',4);
        move('a', 7, 'a', 6);
        move('a',2, 'a',3);
        move('a', 6, 'a', 5);
        move('f', 1, 'c', 4);
        move('d', 7, 'd', 5);
        move('g', 1, 'h', 3);
        move('d',8,'d',6);
        move('e',1,'g',1);
        Piece piece = game.getPieceFromBoard("g1");
        assertThat(piece.getPieceType(), equalTo(PieceEnum.King));
        Piece rook = game.getPieceFromBoard("f1");
        assertThat(rook.getPieceType(), equalTo(PieceEnum.Rook));
    }
    @Test
    public void queenSideCastling() throws Exception {
        move('e',2,'e',4);
        move('a', 7, 'a', 6);
        move('a',2, 'a',3);
        move('a', 6, 'a', 5);
        move('f', 1, 'c', 4);
        move('d', 7, 'd', 5);
        move('g', 1, 'h', 3);
        move('d',8,'d',6);
        move('e',1,'g',1);
        move('c',8,'g',4);
        move('d',1,'g',4);
        move('b',8,'a',6);
        move('d',2,'d',4);
        move('e',7,'e',6);
        move('g',4,'d',1);
        move('e',8,'c',8);

        Piece piece = game.getPieceFromBoard("c8");
        assertThat(piece.getPieceType(), equalTo(PieceEnum.King));
        Piece rook = game.getPieceFromBoard("d8");
        assertThat(rook.getPieceType(), equalTo(PieceEnum.Rook));
    }
    @Test
    public void check() throws Exception {
        String[] moves = {"e2", "e4", "e7", "e5", "d1", "h5", "d7", "d6", "h5", "e5"};
        move(moves);
        assertThat(game.getCurrentGameState(), equalTo(GameStateEnum.Check));
    }
    @Test
    public void checkMateWhiteWins() throws Exception {
        try {
            String[] moves = {
                    "e2", "e4", "e7", "e6", "g1", "f3", "g8", "f6", "e4", "e5",
                    "f6", "d5", "d2", "d4", "d7", "d6", "e5", "d6", "c7", "d6",
                    "c2", "c4", "d5", "b4", "c1", "d2", "a7", "a5", "a2", "a3",
                    "b4", "a6", "c4", "c5", "d6", "c5", "f1", "a6", "b7", "a6",
                    "d4", "c5", "f8", "c5", "b1", "c3", "f7", "f5", "e1", "g1",
                    "e8", "g8", "d1", "b3", "b8", "c6", "a1", "d1", "d8", "b6",
                    "b3", "c2", "c8", "b7", "d2", "e3", "c5", "e3", "f2", "e3",
                    "c6", "e7", "d1", "d3", "b7", "f3", "f1", "f3", "e7", "g6",
                    "c2", "d2", "g6", "e5", "d3", "d6", "b6", "c7", "f3", "f2",
                    "a8", "d8", "d6", "d8", "f8", "d8", "d2", "c2", "e5", "g4",
                    "f2", "f4", "g7", "g5", "f4", "g4", "f5", "g4", "g2", "g3",
                    "c7", "b6", "c2", "e2", "a5", "a4", "c3", "e4", "b6", "c6",
                    "e2", "g4", "c6", "c1", "g1", "g2", "c1", "c2", "e4", "f2",
                    "c2", "c6", "e3", "e4", "c6", "b5", "g4", "e6", "g8", "g7",
                    "e6", "e7", "g7", "g6", "e7", "d8", "b5", "b2", "d8", "d6",
                    "g6", "h5", "g3", "g4", "h5", "h4", "d6", "h6"
            };
            move(moves);
            assertThat(game.getCurrentGameState(),equalTo(GameStateEnum.WonByWhite));
        } catch (Exception e) {
            assertThat(e.getMessage(), equalTo("Checkmate. You won"));
        }
    }

    @Test
    public void invalidTurn() throws Exception {
        String[] moves = {
                "e2", "e3", "e7", "e6","d8","e6"
        };
        exception.expect(Exception.class);
        move(moves);


    }
    @Test
    public void checkMateBlackWins() throws Exception {

        String[] moves = {
                "e2", "e3", "e7", "e6", "d2", "d4", "d8", "h4",
                "d4", "d5", "f8", "c5", "e3", "e4", "h4", "f2"
        };
        move(moves);
        assertThat(game.getCurrentGameState(),equalTo(GameStateEnum.WonByBlack));
        assertThat(game.isGameEnded(),equalTo(true));
    }
    @Test
    public void killedPieces() throws Exception {
        String[] moves = {
                "e2", "e4", "e7", "e6", "g1", "f3", "g8", "f6", "e4", "e5",
                "f6", "d5", "d2", "d4", "d7", "d6", "e5", "d6", "c7", "d6",
                "c2", "c4", "d5", "b4", "c1", "d2", "a7", "a5", "a2", "a3",
                "b4", "a6", "c4", "c5", "d6", "c5", "f1", "a6", "b7", "a6",
                "d4", "c5", "f8", "c5", "b1", "c3", "f7", "f5", "e1", "g1",
                "e8", "g8", "d1", "b3", "b8", "c6", "a1", "d1", "d8", "b6",
                "b3", "c2", "c8", "b7", "d2", "e3", "c5", "e3", "f2", "e3",
                "c6", "e7", "d1", "d3", "b7", "f3", "f1", "f3", "e7", "g6",
                "c2", "d2", "g6", "e5", "d3", "d6", "b6", "c7", "f3", "f2",
                "a8", "d8", "d6", "d8", "f8", "d8", "d2", "c2", "e5", "g4",
                "f2", "f4", "g7", "g5", "f4", "g4", "f5", "g4", "g2", "g3",
                "c7", "b6", "c2", "e2", "a5", "a4", "c3", "e4", "b6", "c6",
                "e2", "g4", "c6", "c1", "g1", "g2", "c1", "c2", "e4", "f2",
                "c2", "c6", "e3", "e4", "c6", "b5", "g4", "e6", "g8", "g7",
                "e6", "e7", "g7", "g6", "e7", "d8", "b5", "b2", "d8", "d6",
                "g6", "h5", "g3", "g4", "h5", "h4", "d6", "h6"
        };
        move(moves);
        assertThat(game.getKilledPieces().isEmpty(), equalTo(false));

    }

    @Test
    public void pointsCommonInTwoKing() throws Exception {

        String[] moves = {
                "e2", "e4", "e7", "e5", "e1", "e2", "e8", "e7",
                "e2", "f3", "e7", "f6", "f3", "g4"
        };
        move(moves);
        var list = game.getExpectedMoves("f6");
        assertThat(game.getExpectedMoves("f6").contains("g5"),equalTo(false));
    }

    @Test
    public void blackCheckKilled5() throws Exception{
        String[] moves = {
                "e2", "e3", "d7", "d5", "g1", "f3", "h7", "h5",
                "d2", "d4", "e7", "e5", "f3", "e5", "d8", "e7",
                "f2", "f4", "f7", "f6", "e5", "g6", "h8", "h6",
                "g6", "e7", "f8", "e7", "f1", "b5", "c8", "d7",
                "d1", "e2", "d7", "b5", "e2", "b5"
        };
        move(moves);
        assertThat(game.getKilledPieces().size(),equalTo(5));
    }

    @Test
    public void knightIsInWQueenBKningPath() throws Exception{
        String[] moves = {
                "e2", "e3", "d7", "d5", "g1", "f3", "h7", "h5",
                "d2", "d4", "e7", "e5", "f3", "e5", "d8", "e7",
                "f2", "f4", "f7", "f6", "e5", "g6", "h8", "h6",
                "g6", "e7", "f8", "e7", "f1", "b5", "c8", "d7",
                "d1", "e2", "d7", "b5", "e2", "b5", "b8", "d7",
                "b5", "b7", "d7", "b8", "b7", "a8"
        };
        move(moves);
        assertThat(game.getExpectedMoves("b8").size(),equalTo(0));
    }
    @Test
    public void rookCanSaveKing() throws Exception{
        String[] moves = {
                "e2", "e3", "d7", "d5", "g1", "f3", "h7", "h5",
                "d2", "d4", "e7", "e5", "f3", "e5", "d8", "e7",
                "f2", "f4", "f7", "f6", "e5", "g6", "h8", "h6",
                "g6", "e7", "f8", "e7", "f1", "b5", "c8", "d7",
                "d1", "e2", "d7", "b5", "e2", "b5", "b8", "d7",
                "b5", "b7", "d7", "b8", "b7", "a8", "e7", "d8",
                "a8", "b8", "g8", "e7", "b8", "a7", "f6", "f5",
                "a7", "a4"
        };
        move(moves);
        assertThat(game.getExpectedMoves("h6").size(),equalTo(1));
    }
    @Test
    public void saveKingPieceD8() throws Exception{
        String[] moves = {
                "e2", "e3", "d7", "d5", "g1", "f3", "h7", "h5",
                "d2", "d4", "e7", "e5", "f3", "e5", "d8", "e7",
                "f2", "f4", "f7", "f6", "e5", "g6", "h8", "h6",
                "g6", "e7", "f8", "e7", "f1", "b5", "c8", "d7",
                "d1", "e2", "d7", "b5", "e2", "b5", "b8", "d7",
                "b5", "b7", "d7", "b8", "b7", "a8", "e7", "d8",
                "a8", "b8", "g8", "e7", "b8", "a7", "f6", "f5",
                "a7", "a4", "h6", "c6", "a4", "b5", "e7", "c8",
                "b5", "c6", "e8", "e7", "c6", "d5", "c8", "d6",
                "d5", "e5", "e7", "d7", "e1", "g1", "d6", "e4",
                "e5", "f5", "d7", "e7", "f5", "e4", "e7", "d6",
                "e4", "g6"
        };
        move(moves);
        assertThat(game.getExpectedMoves("d8").contains("f6"),equalTo(true));
    }
    @Test
    public void noOneLeftInBlack() throws Exception{
        String[] moves = {
                "e2", "e3", "d7", "d5", "g1", "f3", "h7", "h5",
                "d2", "d4", "e7", "e5", "f3", "e5", "d8", "e7",
                "f2", "f4", "f7", "f6", "e5", "g6", "h8", "h6",
                "g6", "e7", "f8", "e7", "f1", "b5", "c8", "d7",
                "d1", "e2", "d7", "b5", "e2", "b5", "b8", "d7",
                "b5", "b7", "d7", "b8", "b7", "a8", "e7", "d8",
                "a8", "b8", "g8", "e7", "b8", "a7", "f6", "f5",
                "a7", "a4", "h6", "c6", "a4", "b5", "e7", "c8",
                "b5", "c6", "e8", "e7", "c6", "d5", "c8", "d6",
                "d5", "e5", "e7", "d7", "e1", "g1", "d6", "e4",
                "e5", "f5", "d7", "e7", "f5", "e4", "e7", "d6",
                "e4", "g6", "d8", "f6", "g6", "h5", "f6", "h4",
                "h5", "h4", "g7", "g5", "h4", "g5", "c7", "c5",
                "d4", "c5"
        };
        move(moves);
        assertThat(game.getPieceFromBoard("d6").getPieceType(),equalTo(PieceEnum.King));
        assertThat(game.getKilledPieces().size(),equalTo(17));
    }

    private void move(char pos,int posNum,char destPos,int destPosNum) throws Exception {
        game.movePiece(pos+""+posNum,destPos+""+destPosNum);
    }
    private void move(String[] arr) throws Exception {
        for (int i = 0; i < arr.length; i++) {
            String pos = arr[i];
            i++;
            if(i>= arr.length){
                break;
            }
            String pos2 = arr[i];
            game.movePiece(pos,pos2);
        }
    }
}
