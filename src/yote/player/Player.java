package yote.player;

import javafx.util.Pair;
import yote.Board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by David et Monireh on 15/11/2016.
 */
public abstract class Player implements Playable {

    public static class Move {

        public Move(MoveType type, Board.Case... cases) {
            this.type = type;
            this.cases = cases;
        }

        public enum MoveType implements Comparable<MoveType> {
            JUMP,
            SLIDE,
            ADD;
        }

        public final MoveType type;

        public final Board.Case[] cases;

        @Override
        public String toString() {
            switch (type){
                case ADD:
                    return type + " : " + cases[0].line + "," + cases[0].column;
                default:
                    return type + " : " + cases[0].line + "," + cases[0].column + " -> " + cases[1].line + "," + cases[1].column;
            }
        }
    }

    public static final int NB_BLOTS = 12;

    private final Board.Blot.BlotColor color;

    private final Deque<Board.Blot> blots = new ArrayDeque<>(NB_BLOTS);

    public Player(Board.Blot.BlotColor color) {
        this.color = color;
        init();
    }

    @Override
    public void init() {
        this.blots.clear();
        for (int nbBlot = 0; nbBlot < NB_BLOTS; nbBlot++) {
            blots.add(new Board.Blot(this.color));
        }
    }

    public Board.Blot takeBlot(){
        return blots.pop();
    }

    public int blotsLeft() {
        return blots.size();
    }

    /**
     * Lists all the legal moves that can be :
     * - add a blot to the given case
     * - move a blot from a given case to an adjacent case
     * - jump over an opponent blot, and taking another opponent's blot (one move by other blot)
     *
     * @param board
     * @return all legal moves for the given player and board
     */
    public List<Move> legalMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (Board.Case aCase : board) {
            // Try to add a ADD Move
            if (aCase.isEmpty() && !blots.isEmpty()) {
                moves.add(new Move(Move.MoveType.ADD, aCase));
            } else if (aCase.isSameColor(color)) {
                for (Board.Direction direction : Board.Direction.values()) {
                    // Try to add a SLIDE Move
                    Board.Case slidingCase = board.moveTo(aCase, direction);
                    if (slidingCase != null) {
                        moves.add(new Move(Move.MoveType.SLIDE, aCase, slidingCase));
                    }
                    // Try to add a JUMP Move
                    Pair<Board.Case, Board.Case> newCase = board.jumpTo(aCase, direction);
                    if (newCase != null) {
                        final Board.Case opponentCase = newCase.getKey();
                        final Board.Case jumpedCase = newCase.getValue();
                        for (Board.Case otherOpponentCase : board) {
                            if(otherOpponentCase.isOpponentColor(color) && otherOpponentCase != opponentCase){
                                moves.add(new Move(Move.MoveType.JUMP, aCase, opponentCase, jumpedCase, otherOpponentCase));
                            }
                        }
                        // The opponent other blot is taken from his own stack
                        moves.add(new Move(Move.MoveType.JUMP, aCase, opponentCase, jumpedCase, null));
                    }
                }
            }
        }
        return moves;
    }

    public boolean hasLost() {
        return blots.isEmpty();
    }
}
