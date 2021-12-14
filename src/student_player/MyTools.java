package student_player;

import boardgame.Board;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoBoardState.Piece;

public class MyTools {

    private static Tuple<Integer, Integer> computeScore(Piece p, Piece n, int s, int pc) {
        if (p == Piece.WHITE && n == Piece.WHITE) {
            s += Math.pow(2, pc);
            pc++;
        } else if (p == Piece.BLACK && n == Piece.BLACK) {
            s -= Math.pow(2, pc);
            pc++;
        } else pc = 0;
        return new Tuple<>(s, pc);
    }

    public static int getScore(PentagoBoardState pbs, int player, int alpha, int beta) {
        int score;
        if (player == 0)
            score = alpha;
        else score = beta;
        int horizontalPieceCount = 0, verticalPieceCount = 0, rightDiagonalPieceCount = 0, leftDiagonalPieceCount = 0;

        if (pbs.getWinner() == Board.NOBODY) {

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    // |
                    Tuple<Integer, Integer> verticalPair = computeScore(pbs.getPieceAt(j, i), pbs.getPieceAt(j + 1, i), score, verticalPieceCount);
                    score = verticalPair.key;
                    verticalPieceCount = verticalPair.value;
                    // --
                    Tuple<Integer, Integer> horizontalPair = computeScore(pbs.getPieceAt(i, j), pbs.getPieceAt(i, j + 1), score, horizontalPieceCount);
                    score = horizontalPair.key;
                    horizontalPieceCount = horizontalPair.value;
                }
            }

            for (int diagonalIndex = 0; diagonalIndex < 5; diagonalIndex++) {
                // /
                Tuple<Integer, Integer> rightDiagonalPair = computeScore(pbs.getPieceAt(diagonalIndex, diagonalIndex), pbs.getPieceAt(diagonalIndex + 1, diagonalIndex + 1), score, rightDiagonalPieceCount);
                score = rightDiagonalPair.key;
                rightDiagonalPieceCount = rightDiagonalPair.value;
                // \
                Tuple<Integer, Integer> leftDiagonalPair = computeScore(pbs.getPieceAt(diagonalIndex, 5 - diagonalIndex), pbs.getPieceAt(diagonalIndex + 1, 4 - diagonalIndex), score, leftDiagonalPieceCount);
                score = leftDiagonalPair.key;
                leftDiagonalPieceCount = leftDiagonalPair.value;
            }
        } else {
            if (pbs.getWinner() == PentagoBoardState.WHITE) {
                score = Integer.MAX_VALUE;
            } else {
                score = Integer.MIN_VALUE;
            }
        }

        if (player == PentagoBoardState.BLACK) {
            score = -score;
        }

        return score;
    }

}