package student_player;

import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

import java.util.ArrayList;

class AlphaBetaPruning {

    public static Tuple<Integer, PentagoMove> alphaBetaPrune(PentagoBoardState pbs, int player, int alpha, int beta, int depth) {

        Tuple<Integer, PentagoMove> tuple;
        long endTime = System.currentTimeMillis() + 1950;
        ArrayList<PentagoMove> legalMoves = pbs.getAllLegalMoves();
        PentagoMove bestMove = legalMoves.get(0);
        int bestScore = 0;

        if (depth == 0) {
            bestScore = MyTools.getScore(pbs, player, alpha, beta);
            tuple = new Tuple<>(bestScore, bestMove);
            return tuple;
        }

        for (PentagoMove move : legalMoves) {
            PentagoBoardState clone = (PentagoBoardState) pbs.clone();
            clone.processMove(move);
            if (System.currentTimeMillis() > endTime || alpha >= beta) {
                return new Tuple<>(bestScore, bestMove);
            }
            if (player == 0) {
                bestScore = alphaBetaPrune(clone, 1, alpha, beta, depth-1).key;

                if (bestScore > alpha) {
                    alpha = bestScore;
                    bestMove = move;
                }
            } else {
                bestScore = alphaBetaPrune(clone, 0, alpha, beta, depth-1).key;

                if (bestScore < beta) {
                    beta = bestScore;
                    bestMove = move;
                }
            }
        }

        if (player == 0)
            return new Tuple<>(alpha, bestMove);
        else {
            return new Tuple<>(beta, bestMove);
        }
    }
}
