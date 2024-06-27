import abstracts.ChessCallback;
import enums.PieceEnum;

import java.util.Scanner;

import java.util.Scanner;

/*
 * Implementation of ChessCallback interface to handle pawn upgrade selection.
 */
public class UserChess implements ChessCallback {
    /*
     * Prompts the user to select a piece type for pawn upgrade.
     * Returns: The PieceEnum representing the selected piece type (Queen, Rook, Bishop, Knight).
     */
    @Override
    public PieceEnum getSelectedPieceForPawnUpgrade() {
        // Prompt user for piece selection
        System.out.println("Enter piece name in small letter to upgrade:\nqueen, rook, bishop, knight");

        // Read user input
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        // Convert input to PieceEnum
        switch (input) {
            case "queen":
                return PieceEnum.Queen;
            case "rook":
                return PieceEnum.Rook;
            case "bishop":
                return PieceEnum.Bishop;
            case "knight":
                return PieceEnum.Knight;
            default:
                // Default to Queen if invalid input
                return PieceEnum.Queen;
        }
    }
}
