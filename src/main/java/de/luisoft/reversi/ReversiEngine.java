package de.luisoft.reversi;

import de.luisoft.reversi.engine.BoardModel;

public interface ReversiEngine {

    void forceMove(int move);

    int getMove();

    void forceNull();

    void setGameTime(long time);

    void reset();

    BoardModel getModel();
}
