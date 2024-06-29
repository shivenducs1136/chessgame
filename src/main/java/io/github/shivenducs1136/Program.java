package io.github.shivenducs1136;

import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.chess.ChessEngine;
import io.github.shivenducs1136.enums.ColorEnum;
import io.github.shivenducs1136.exceptions.InvalidMoveException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class Program{
    public static void main(String[] args) throws Exception {
        ChessEngine chessEngine = new ChessEngine(new UserChess());
        Scanner sc = new Scanner(System.in);
        String idx;
        List<String> expectedMoves = null;
        File myObj = new File("moves.txt");
        boolean b = false;
        do{
            printBoard(chessEngine);

            if(chessEngine.isGameEnded()){
                System.out.println("Game is ended and It is " + chessEngine.getCurrentGameState());
                return;
            }
            String position;
            String changePiece;
            do{
                System.out.println("Enter position of piece to move without any space -");
                position   = sc.nextLine();
                if(!b){
                    if (myObj.exists()) {
                        myObj.delete();
                    }
                    myObj.createNewFile();
                    b = true;
                }
                try {
                    System.out.println("Expected Moves of piece at: "+position);
                    expectedMoves = chessEngine.getExpectedMoves(position);
                }
                catch (InvalidMoveException e){

                }
                try {
                    printList(expectedMoves);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }while(expectedMoves == null || expectedMoves.isEmpty() );
            String newPosition= "";
            do{
                if(!newPosition.isEmpty()){
                        System.out.println("Entered position is invalid, Please enter from the Expected Move list.");
                }
                System.out.println("Enter new position where you want to move piece");
                newPosition = sc.nextLine();
            }while (!chessEngine.movePiece(position, newPosition));

            Files.write(Paths.get("moves.txt"), position.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("moves.txt"), System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("moves.txt"), newPosition.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("moves.txt"), System.lineSeparator().getBytes(), StandardOpenOption.APPEND);

        }while(Objects.equals("0", "0"));
    }

    private static void printList(List<String> list) throws Exception {
        if(list == null){
            throw new InvalidMoveException("Piece can't move Enter piece to move");
        }
        if(list.isEmpty()){
            throw new InvalidMoveException("Piece can't move Enter piece to move");
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
                Piece piece = chessEngine.getPieceFromBoard(i,j)  ;
                if(piece != null){
                        System.out.print( GetWorB(((Piece) piece).getPlayerColor()) +piece.getPieceType() +chessEngine.getPiecePosition(piece).toUpperCase()+"   ");
                }
                else{
                    System.out.print ("    -     ");
                }
            }
            System.out.println();
        }
        System.out.println("Current Chance    - " + chessEngine.getCurrentPlayer());
        System.out.println("Current EngineState - " + chessEngine.getCurrentGameState());
    }
    private static String GetWorB(ColorEnum colorEnum){
        if(colorEnum == ColorEnum.White){
            return "W";
        }
        return "B";
    }
}