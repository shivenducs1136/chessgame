package io.github.shivenducs1136.chess;


import io.github.shivenducs1136.abstracts.Piece;
import io.github.shivenducs1136.abstracts.Rule;
import io.github.shivenducs1136.rules.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * This class serves as a factory for creating rule objects for chess pieces.
 * It provides methods to get the appropriate rule for a given piece based on
 * its type and board state.
 */
public class RuleFactory {

    // List of rule classes corresponding to different chess pieces
    private static final List<Class<? extends Rule>> RULES = Arrays.asList(
            PawnRule.class,
            KnightRule.class,
            KingRule.class,
            RookRule.class,
            BishopRule.class,
            QueenRule.class,
            CheckRule.class);

    /**
     * Creates and returns the appropriate rule for the given piece based on its type.
     * It iterates through the list of rule classes, tries to instantiate each rule,
     * and returns the rule that matches the piece type.
     *
     * @param board the chess board on which the piece is located
     * @param forPiece the piece for which the rule is to be created
     * @return the appropriate rule for the given piece
     * @throws RuntimeException if no matching rule is found or instantiation fails
     */
    public static Rule getRule(Board board, Piece forPiece) {
        for (Class<? extends Rule> aClass : RULES) {
            try {
                // Attempt to instantiate the rule using reflection
                Rule rule = aClass
                        .getDeclaredConstructor(Board.class, Piece.class)
                        .newInstance(board, forPiece);

                // Return the rule if it matches the piece type
                if (rule.pieceType() == forPiece.getPieceType()) {
                    return rule;
                }
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                     NoSuchMethodException | SecurityException | InvocationTargetException e) {
                // Exception handling omitted for simplicity
            }
        }

        // Throw runtime exception if no matching rule is found
        throw new RuntimeException();
    }

    /**
     * Creates and returns the specified rule for the given piece and board.
     * It tries to instantiate the provided rule class and returns the rule
     * if it matches the piece type.
     *
     * @param board the chess board on which the piece is located
     * @param forPiece the piece for which the rule is to be created
     * @param ruleClass the class of the rule to be created
     * @return the specified rule for the given piece
     * @throws RuntimeException if instantiation fails or the rule does not match the piece type
     */
    public static Rule getRule(Board board, Piece forPiece, Class<? extends Rule> ruleClass) {
        try {
            // Attempt to instantiate the rule using reflection
            Rule rule = ruleClass
                    .getDeclaredConstructor(Board.class, Piece.class)
                    .newInstance(board, forPiece);

            // Return the rule if it matches the piece type
            if (rule.pieceType() == forPiece.getPieceType()) {
                return rule;
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 NoSuchMethodException | SecurityException | InvocationTargetException e) {
            // Exception handling omitted for simplicity
        }

        // Throw runtime exception if the rule does not match the piece type
        throw new RuntimeException();
    }
}
