//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMoveGenerator implements MoveGenerator {
    private static final Logger trace = LoggerFactory.getLogger(DefaultMoveGenerator.class);
    private static final int DEFAULT_TIME_PER_MOVE = 2;
    private static final int MAX_SEARCH_DEPTH = 20;
    private final MoveSorter sorter = new MoveSorter();
    private ReversiBoard board;
    private AlphaBeta alphaBeta;
    private long gameTime = -1L;
    private long remainingTime = -1L;

    public DefaultMoveGenerator() {
    }

    public void setBoard(ReversiBoard board) {
        this.board = board;
        this.alphaBeta = new AlphaBeta(board);
        this.alphaBeta.setSorter(this.sorter);
    }

    public void setGameTime(long seconds) {
        trace.info("setGameTime to " + seconds + "ms.");
        this.gameTime = seconds;
    }

    public void setRemainingTime(long seconds) {
        trace.info("setRemainingTime to " + seconds + "ms.");
        this.remainingTime = seconds;
    }

    public int getMove(int colour) {
        int step = this.board.getStep();
        trace.info("Step " + step + ": start calculating a move for " + (colour == 1 ? "white" : "black"));
        if (this.gameTime > 0L && this.remainingTime < 0L) {
            this.remainingTime = this.gameTime;
        } else if (this.remainingTime < 0L) {
            this.remainingTime = 60000L;
        }

        trace.info("Step " + step + ": remaining time = " + this.remainingTime / 1000L + "s");
        long start = System.currentTimeMillis();
        long endTime = start + this.remainingTime / (long) ((65 - step) / 2);
        trace.info("Step " + step + ": I stop calculating in " + (endTime - start) + "ms");
        this.alphaBeta.resetCounter();
        this.alphaBeta.setTimer(endTime);
        Evaluator eval = new Evaluator(step);
        this.alphaBeta.setEvaluator(eval);
        this.sorter.clear();
        int depth = 1;
        boolean timeout = false;
        int bestVal = -65536;
        int bestMove = -1;
        int oldBestMove = -1;
        trace.info("legal moves to " + this.p(this.sorter.getMoves(this.board, colour), null));

        int alpha;
        while (depth <= Math.min(64 - step, 20) && !timeout) {
            oldBestMove = bestMove;
            bestMove = -1;
            bestVal = -65536;
            alpha = -32768;
            int[] moves = this.sorter.getMoves(this.board, colour);
            int[] values = new int[32];
            trace.debug("calculate depth " + depth);
            if (moves[0] == -1 || moves[1] == -1) {
                bestMove = moves[0];
                break;
            }

            for (int i = 0; moves[i] != -1; ++i) {
                this.board.doMove(moves[i], colour);
                if (trace.isDebugEnabled()) {
                    trace.debug("calculate value for move " + moves[i]);
                }

                values[i] = -this.alphaBeta.evaluate(depth - 1, -32768, -alpha, step + 1, -colour);
                if (trace.isDebugEnabled()) {
                    trace.debug("found val for move " + moves[i] + " is " + values[i]);
                }

                this.board.undo();
                if (values[i] > bestVal) {
                    alpha = values[i];
                    bestVal = values[i];
                    bestMove = moves[i];
                }

                if (depth > 1 && System.currentTimeMillis() > endTime) {
                    trace.info("timeout! stop calculating at loop " + i);
                    timeout = true;
                    break;
                }
            }

            trace.info("best move at dep " + depth + "; " + bestMove + ";" + this.p(moves, values));
            this.sorter.commit(this.board, moves, values);
            ++depth;
        }

        if (timeout) {
            bestMove = oldBestMove;
        }

        alpha = this.alphaBeta.getCounter();
        long calcTime = 1L + System.currentTimeMillis() - start;
        this.remainingTime -= calcTime;
        trace.info("best move " + bestMove + " with val " + bestVal);
        trace.info("searched #=" + alpha + "; nodes in " + calcTime + "ms; nodes/s=" + (1000L * alpha) / calcTime);
        trace.info("remaining time=" + this.remainingTime + "ms");
        return bestMove;
    }

    private String p(int[] m, int[] n) {
        int i = 0;

        StringBuilder strb;
        for (strb = new StringBuilder(); m[i] != -1; ++i) {
            strb.append(m[i]).append(n != null ? "(" + n[i] + ")" : "").append("; ");
        }

        return strb.toString();
    }
}
