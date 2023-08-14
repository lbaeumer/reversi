//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

public interface BoardModel {
    int getColour(int var1);

    boolean doMove(int var1, int var2);

    int getScore();

    int getStep();
}
