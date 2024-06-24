package abstracts; 

import java.util.List;

import chess.PieceManager;
import enums.PieceEnum;
import enums.PlayerEnum;

public abstract class Piece {
    protected PlayerEnum player;
    protected boolean isKilled;
    protected boolean canMove;
    protected String position;

    abstract public List<String> ExpectedMove();
    abstract public PlayerEnum getPlayer();
    abstract public boolean isKilled();
    abstract public String getPosition();
    abstract public void setIsKilled(boolean isKilled);
    abstract public void setCanMove(boolean canMove);
    abstract public boolean getCanMove();
    abstract public void setPosition(String newPosition);
}
