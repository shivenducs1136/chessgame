import abstracts.Piece;
import chess.ChessGame;
import enums.PlayerEnum;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import java.util.*;;
class Program{
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame(); 
        printBoard(chessGame);

        chessGame.Move("14", "34"); // White Pawn from e2 to e4
        printBoard(chessGame);
        chessGame.Move("64", "54"); // Black Pawn from e7 to e5
        printBoard(chessGame);
        chessGame.Move("06", "25"); // White Knight from g1 to f3
        printBoard(chessGame);
        chessGame.Move("10", "22"); // Black Knight from b8 to c6
        printBoard(chessGame);
        chessGame.Move("05", "32"); // White Bishop from f1 to c4
        printBoard(chessGame);

    }

    private static void printList(List<String> list){
        if(list.isEmpty() || list == null){
            System.out.println("null");
        }
        
        for (String str : list) {
            System.out.println(str);
        }

    }
    private static void printBoard(ChessGame chessGame){
        System.out.println();
        for(int i = 0; i< 8; i++){
            for(int j= 0; j<8; j++){
                Piece piece = chessGame.board.get(i).get(j)  ;
                if(piece != null){
                    if(piece instanceof Rook){
                        Rook r = (Rook) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() + ") ");
                    }
                    else if(piece instanceof Bishop){
                        Bishop r = (Bishop) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() + ") ");
                    }
                    else if(piece instanceof Knight){
                        Knight r = (Knight) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() + ") ");
                    }
                    else if(piece instanceof King){
                        King r = (King) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() + ") ");
                    }
                    else if(piece instanceof Queen){
                        Queen r = (Queen) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() + ") ");
                    }
                    else if(piece instanceof Pawn){
                        Pawn r = (Pawn) piece;
                        System.out.print( "("+ r.getPieceEnum() + " " + r.getPlayerEnum() + r.getPosition() +  ") ");
                    }
                }
                else{
                    System.out.print (  "xxxxxxxxxxxxx ");
                }
            }
            System.out.println();
        }
    }
}