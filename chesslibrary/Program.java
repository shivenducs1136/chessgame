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
        chessGame.Move("14","34");
        printBoard(chessGame);
        printList(chessGame.GetExpectedMove("64"));
        chessGame.Move("64","44");
        printBoard(chessGame);
        printList(chessGame.GetExpectedMove("03"));
        chessGame.Move("03","47");
        printBoard(chessGame);
        chessGame.Move("62","42");
        printBoard(chessGame);
        chessGame.Move("47","44");
        printBoard(chessGame);
        printList(chessGame.GetExpectedMove("42"));
    }

    private static void printList(List<String> list){
        if(list == null){
            System.out.println("null");
            return;
        }
        if(list.isEmpty()){
            System.out.println("empty");
        }
        
        for (String str : list) {
            System.out.println(str);
        }

    }
    private static void printBoard(ChessGame chessGame){
        System.out.println();
        System.out.print("  ");
        for(int i = 0; i< 8 ; i++){
            System.out.print(" "+i+"  ");
        }
        System.out.println();
        for(int i = 0; i< 8; i++){
            System.out.print(i + " ");
            for(int j= 0; j<8; j++){
                Piece piece = ChessGame.board.get(i).get(j)  ;
                if(piece != null){
                    if(piece instanceof Rook r){
                        System.out.print( "R" +r.getPosition()+" ");
                    }
                    else if(piece instanceof Bishop r){
                        System.out.print( "B" +r.getPosition()+" ");
                    }
                    else if(piece instanceof Knight r){
                        System.out.print("N" +r.getPosition()+" ");
                    }
                    else if(piece instanceof King r){
                        System.out.print("K" +r.getPosition()+" ");
                    }
                    else if(piece instanceof Queen r){
                        System.out.print( "Q" +r.getPosition()+" ");
                    }
                    else if(piece instanceof Pawn r){
                        System.out.print( "P" +r.getPosition()+" ");
                    }
                }
                else{
                    System.out.print (" -  ");
                }
            }
            System.out.println();
        }
    }
}