//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import de.luisoft.reversi.ReversiEngine;

public class ReversiEngineRandomImpl implements ReversiEngine {
    private final ReversiBoard board = new ReversiBoard();
    private final MoveGenerator generator = new RandomMoveGenerator();
    private int toMove = -1;

    public ReversiEngineRandomImpl() {
        this.generator.setBoard(this.board);
    }

    public String getName() {
        return "Random Reversi";
    }

    public void setGameTime(long seconds) {
        this.generator.setGameTime(seconds);
    }

    public void setRemainingTime(long seconds) {
        this.generator.setRemainingTime(seconds);
    }

    public void forceMove(int sq) {
        boolean b = this.board.doMove(sq, this.toMove);
        if (!b) {
            throw new IllegalArgumentException("The move " + sq + " is illegal at \n" + this.board);
        } else {
            this.toMove *= -1;
        }
    }

    public void forceNull() {
        this.toMove *= -1;
    }

    public int getMove() {
        return this.generator.getMove(this.toMove);
    }

    public void reset() {
        this.toMove = -1;
        this.board.reset();
    }

    public String toString() {
        return this.board.toString();
    }

    public BoardModel getModel() {
        return this.board;
    }
}
