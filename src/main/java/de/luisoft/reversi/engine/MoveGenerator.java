//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

public interface MoveGenerator {
    void setBoard(ReversiBoard var1);

    void setGameTime(long var1);

    void setRemainingTime(long var1);

    int getMove(int var1);
}
