package chess;

import abstracts.Piece;
import abstracts.Rule;
import pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class RuleFactory {

    private static final List<Class<? extends Rule>> RULES = Arrays.asList(
            PawnRule.class,
            KnightRule.class,
            KingRule.class,
            RookRule.class,
            BishopRule.class,
            QueenRule.class,
            CheckRule.class);

    public static Rule getRule(Board board, Piece forPiece) {
        for(Class<? extends Rule> aClass : RULES)
            try {
                Rule rule = aClass.
                        getDeclaredConstructor(Board.class, Piece.class).
                        newInstance(board, forPiece);
                if(rule.pieceType() == forPiece.getPieceType())
                    return rule;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                     NoSuchMethodException | SecurityException | InvocationTargetException e) {}

        throw new RuntimeException();
    }
    public static Rule getRule(Board board, Piece forPiece,Class<? extends Rule> ruleClass) {
            try {
                Rule rule = ruleClass.
                        getDeclaredConstructor(Board.class, Piece.class).
                        newInstance(board, forPiece);
                if(rule.pieceType() == forPiece.getPieceType())
                    return rule;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                     NoSuchMethodException | SecurityException | InvocationTargetException e) {}

        throw new RuntimeException();
    }
}