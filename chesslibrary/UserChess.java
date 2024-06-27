import abstracts.ChessCallback;
import enums.PieceEnum;

import java.util.Scanner;

public class UserChess implements ChessCallback {
    @Override
    public PieceEnum getSelectedPieceForPawnUpgrade() {
        System.out.println("Enter piece name in small letter to upgrade \n queen, rook, bishop, knight");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input){
            case "queen"->{
                return PieceEnum.Queen;
            }
            case "rook"->{
                return PieceEnum.Rook;
            }
            case "bishop"->{
                return PieceEnum.Bishop;
            }
            case "knight"->{
                return PieceEnum.Knight;
            }
        }
        return PieceEnum.Queen;
    }
}
