package pieces;

import java.util.ArrayList;
import java.util.List;

import abstracts.*;
import chess.ChessGame;
import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public class King extends Piece{

    final PieceEnum pieceEnum = PieceEnum.King;
    PlayerEnum playerEnum;
    boolean isKilled =false;
    boolean canMove = true;
    String position; 

    public King( PlayerEnum playerEnum,String position) {
        this.playerEnum = playerEnum;
        this.position = position; 
    }
    
    public List<String> ExpectedMove(){
       PieceManager pm = new PieceManager();
        List<String> moves = new ArrayList<>(GetAllValidMoves(pm));
        List<String> oppositePlayerPieceMovesThatCanCheckKing = GetOppositePlayerPieceMovesThatCanHarmKing(pm);
        for (String mv : oppositePlayerPieceMovesThatCanCheckKing) {
            if(moves.contains(mv)){
                moves.remove(mv);
            }
        }
       return moves; 
    }

    public List<String> GetAllValidMoves(PieceManager pm) {
        List<String> moves = new ArrayList<>();
        int[] iIndexs = new int[]{1,1,-1,0,1,0,-1,-1};
        int[] jIndexs = new int[]{1,-1,-1,1,0,-1,0,1};
        int idx = pm.GetIindex(position);
        int jdx = pm.GetJindex(position);
        for(int x = 0; x<8;x++ ){
            int newIdx = idx+iIndexs[x];
            int newJdx = jdx+jIndexs[x];

            if(pm.IsIndexSafe(newIdx, newJdx)){
                if((pm.GetPieceAtPosition(newIdx+""+newJdx)==null))
                    moves.add(newIdx+""+newJdx);
                else{
                    if((pm.GetPieceAtPosition(newIdx+""+newJdx).getPlayer()!=playerEnum)){
                        moves.add(newIdx+""+newJdx);
                    }
                }
            }
        }
        return moves;
    }

    private List<String> GetOppositePlayerPieceMovesThatCanHarmKing(PieceManager pm) {
        List<String> moves = new ArrayList<>();
        List<Piece> oppositeColorPieces = pm.GetOppositePlayerPieces(playerEnum);

        for (Piece piece : oppositeColorPieces) {
            List<String> pcMoves;
            if(piece instanceof King){
                pcMoves = pm.GetPointsCommonInBothKing();

            }
            else{
                pcMoves = piece.ExpectedMove();
            }
            for (String mv : pcMoves) {
                if(!moves.contains(mv)){
                    moves.add(mv);
                }
            }
        }
        return moves;
    }


    public PieceEnum getPieceEnum() {
        return pieceEnum;
    }
    public PlayerEnum getPlayerEnum() {
        return playerEnum;
    }
    public void setPlayerEnum(PlayerEnum playerEnum) {
        this.playerEnum = playerEnum;
    } 

    public boolean getIsKilled() {
        return isKilled;
    }
    public boolean getCanMove() {
        return canMove;
    }
    public String getPosition() {
        return position;
    }
    public void setKilled(boolean isKilled) {
        this.isKilled = isKilled;
    }
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    @Override
    public PlayerEnum getPlayer() {
        return playerEnum;
    }

    @Override
    public PieceEnum getPiece() {
        return pieceEnum;
    }

    @Override
    public boolean isKilled() {
        return isKilled;
    }

    @Override
    public void setIsKilled(boolean isKilled) {
       this.isKilled = isKilled;
    }
}
