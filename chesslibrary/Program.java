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
        Scanner sc = new Scanner(System.in);
        String idx;
        do{
            printBoard(chessGame);
            String position;
            do{
                System.out.println("Enter position of piece to move without any space -");
                position   = sc.nextLine();
                System.out.println("Expected Moves of piece at: "+position);
                printList(chessGame.GetExpectedMove(position));
            }while(chessGame.GetExpectedMove(position)==null);

            System.out.println("Enter new position where you want to move piece");
            String newPosition = sc.nextLine();
            chessGame.Move(position,newPosition);
            printBoard(chessGame);
            System.out.println("To continue press any key or press 0 to exit.");
            idx = sc.nextLine();
        }while(!Objects.equals(idx, "0"));
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
            System.out.print("  "+i+"       ");
        }
        System.out.println();
        for(int i = 0; i< 8; i++){
            System.out.print(i + " ");
            for(int j= 0; j<8; j++){
                Piece piece = ChessGame.board.get(i).get(j)  ;
                if(piece != null){
                    if(piece instanceof Rook r){
                        System.out.print( GetWorB(piece.getPlayer()) +"Rook" +r.getPosition()+"   ");
                    }
                    else if(piece instanceof Bishop r){
                        System.out.print(  GetWorB(piece.getPlayer()) +"Bishop" +r.getPosition()+" ");
                    }
                    else if(piece instanceof Knight r){
                        System.out.print( GetWorB(piece.getPlayer()) +"Knight" +r.getPosition()+" ");
                    }
                    else if(piece instanceof King r){
                        System.out.print( GetWorB(piece.getPlayer()) +"King" +r.getPosition()+"   ");
                    }
                    else if(piece instanceof Queen r){
                        System.out.print(  GetWorB(piece.getPlayer()) +"Queen" +r.getPosition()+"  ");
                    }
                    else if(piece instanceof Pawn r){
                        System.out.print(  GetWorB(piece.getPlayer()) +"Pawn" +r.getPosition()+"   ");
                    }
                }
                else{
                    System.out.print ("    -     ");
                }
            }
            System.out.println();
        }
        System.out.println("Current Chance    - " + chessGame.getPlayer());
        System.out.println("Current GameState - " + chessGame.currentGameState);
    }
    private static String GetWorB(PlayerEnum playerEnum){
        if(playerEnum == PlayerEnum.White){
            return "W";
        }
        return "B";
    }
}