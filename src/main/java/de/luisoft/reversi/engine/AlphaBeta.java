//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlphaBeta {
    private static final Logger trace = LoggerFactory.getLogger(AlphaBeta.class);
    private final ReversiBoard board;
    private MoveSorter sorter;
    private int counter;
    private long timer;
    private Evaluator evaluator;

    public AlphaBeta(ReversiBoard board) {
        this.board = board;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public void setEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setSorter(MoveSorter sorter) {
        this.sorter = sorter;
    }

    public int getCounter() {
        return this.counter;
    }

    public int evaluate(int depth, int alpha, int beta, int ply, int colour) {
        ++this.counter;
        if (depth <= 0) {
            if (ply > 64) {
                throw new IllegalStateException("ply =" + ply);
            } else {
                int eval = this.evaluator.eval(this.board, colour);
                if (trace.isDebugEnabled()) {
                    trace.debug("eval returns " + eval + " (" + ply + "," + colour + "," + this.board.getId().hashCode() + ")");
                }

                return eval;
            }
        } else if (System.currentTimeMillis() > this.timer) {
            trace.info("timeout! stop calculating!" + depth);
            return alpha;
        } else {
            int[] moves = this.sorter.getMoves(this.board, colour);
            int[] values = new int[32];

            for (int i = 0; moves[i] != -1 && alpha < beta; ++i) {
                if (System.currentTimeMillis() > this.timer) {
                    trace.info("timeout! stop calculating loop!" + depth);
                    return alpha;
                }

                if (trace.isDebugEnabled()) {
                    trace.debug("eval(" + depth + "," + alpha + "," + beta + ") moves to " + moves[i]);
                }

                this.board.doMove(moves[i], colour);
                values[i] = -this.evaluate(depth - 1, -(alpha + 1), -alpha, ply + 1, -colour);
                if (values[i] > alpha) {
                    if (trace.isDebugEnabled()) {
                        trace.debug("research " + depth + ";" + values[i]);
                    }

                    values[i] = -this.evaluate(depth - 1, -beta, -alpha, ply + 1, -colour);
                }

                this.board.undo();
                if (alpha < values[i]) {
                    alpha = values[i];
                }

                if (beta <= alpha && trace.isDebugEnabled()) {
                    trace.debug("cutoff(" + depth + ") b=" + alpha + ";" + beta);
                }
            }

            if (depth >= 2) {
                this.sorter.commit(this.board, moves, values);
            }

            if (trace.isDebugEnabled()) {
                trace.debug("return at depth " + depth + " value " + alpha);
            }

            return alpha;
        }
    }
}
