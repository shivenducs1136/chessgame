import abstracts.Piece;
import chess.ChessEngine;
import chess.ChessEngine;
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
        ChessEngine chessEngine = new ChessEngine(new UserChess());
        Scanner sc = new Scanner(System.in);
        String idx;
        List<String> expectedMoves = null;
        do{
            printBoard(chessEngine);
            if(chessEngine.IsGameEnded()){
                System.out.println("Game is ended and It is " + chessEngine.GetCurrentGameState());
                return;
            }
            String position;
            String changePiece;
            do{
                System.out.println("Enter position of piece to move without any space -");
                position   = sc.nextLine();
                System.out.println("Expected Moves of piece at: "+position);
                expectedMoves = chessEngine.GetExpectedMoves(position);
                printList(expectedMoves);
            }while(expectedMoves == null || expectedMoves.isEmpty() );
            String newPosition= "";
            do{
                if(!newPosition.isEmpty()){
                        System.out.println("Entered position is invalid, Please enter from the Expected Move list.");
                }
                System.out.println("Enter new position where you want to move piece");
                newPosition = sc.nextLine();
            }while (!chessEngine.MovePiece(position, newPosition));

        }while(Objects.equals("0", "0"));
    }

    private static void printList(List<String> list){
        if(list == null){
            System.out.println("Piece can't move Enter any other piece");
            return;
        }
        if(list.isEmpty()){
            System.out.println("Piece can't move Enter any other piece");
        }
        for (String str : list) {
            System.out.println(str);
        }

    }
    private static void printBoard(ChessEngine chessEngine){
        System.out.println();
        System.out.print("  ");
        for(int i = 7; i>=0 ; i--){
            System.out.print("  "+(char)('a'+i)+"       ");
        }
        System.out.println();
        for(int i = 0; i< 8; i++){
            System.out.print(i+1 + " ");
            for(int j= 0; j<8; j++){
                Piece piece = chessEngine.GetPieceFromBoard(i,j)  ;
                if(piece != null){
                    if(piece instanceof Rook r){
                        System.out.print( GetWorB(r.getPlayer()) +"Rook" +chessEngine.GetPiecePosition(piece).toUpperCase()+"   ");
                    }
                    else if(piece instanceof Bishop r){
                        System.out.print(  GetWorB(r.getPlayer()) +"Bishop" +chessEngine.GetPiecePosition(piece).toUpperCase()+" ");
                    }
                    else if(piece instanceof Knight r){
                        System.out.print( GetWorB(r.getPlayer()) +"Knight" +chessEngine.GetPiecePosition(piece).toUpperCase()+" ");
                    }
                    else if(piece instanceof King r){
                        System.out.print( GetWorB(r.getPlayer()) +"King" +chessEngine.GetPiecePosition(piece).toUpperCase()+"   ");
                    }
                    else if(piece instanceof Queen r){
                        System.out.print(  GetWorB(r.getPlayer()) +"Queen" +chessEngine.GetPiecePosition(piece).toUpperCase()+"  ");
                    }
                    else if(piece instanceof Pawn r){
                        System.out.print(  GetWorB(r.getPlayer()) +"Pawn" +chessEngine.GetPiecePosition(piece).toUpperCase()+"   ");
                    }
                }
                else{
                    System.out.print ("    -     ");
                }
            }
            System.out.println();
        }
        System.out.println("Current Chance    - " + chessEngine.GetCurrentPlayer());
        System.out.println("Current EngineState - " + chessEngine.GetCurrentGameState());
    }
    private static String GetWorB(PlayerEnum playerEnum){
        if(playerEnum == PlayerEnum.White){
            return "W";
        }
        return "B";
    }
}