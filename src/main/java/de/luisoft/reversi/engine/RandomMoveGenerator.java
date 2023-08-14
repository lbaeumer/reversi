//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomMoveGenerator implements MoveGenerator {
    private static final Logger trace = LoggerFactory.getLogger(RandomMoveGenerator.class);
    private final AlphaBeta alphaBeta;
    private final Random random = new Random(0L);
    private ReversiBoard board;

    public RandomMoveGenerator() {
        this.alphaBeta = new AlphaBeta(this.board);
    }

    public void setBoard(ReversiBoard board) {
        this.board = board;
    }

    public void setGameTime(long seconds) {
    }

    public void setRemainingTime(long seconds) {
    }

    public int getMove(int colour) {
        trace.info("start calculating a move for " + (colour == 1 ? "white" : "black"));
        int i = 0;
        int[] moves = this.board.getMoves(colour);

        while (moves[i++] != -1) {
        }

        --i;
        trace.info("found " + i + " moves");
        return i == 0 ? -1 : moves[this.random.nextInt(i)];
    }
}
