package student_player;

import boardgame.Move;

import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import pentago_twist.PentagoPlayer;

import java.util.ArrayList;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260724798");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState pentagoBoardState) {

        // try for winning move
        ArrayList<PentagoMove> moves = pentagoBoardState.getAllLegalMoves();
        for (PentagoMove winningMove : moves) {
            PentagoBoardState clone = (PentagoBoardState) pentagoBoardState.clone();
            clone.processMove(winningMove);
            if (pentagoBoardState.getTurnPlayer() == clone.getWinner())
                return winningMove;
        }
        // else alpha beta prune
        return AlphaBetaPruning.alphaBetaPrune(pentagoBoardState, pentagoBoardState.getTurnPlayer(), Integer.MIN_VALUE, Integer.MAX_VALUE, 3).value;
    }
}
