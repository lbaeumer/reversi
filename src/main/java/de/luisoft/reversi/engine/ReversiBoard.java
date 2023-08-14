//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReversiBoard implements BoardModel {
    private static final Logger trace = LoggerFactory.getLogger(ReversiBoard.class);
    private int[] game;
    private int step;
    private int undoCounter;
    private int[] undo;

    public ReversiBoard() {
        this.reset();
    }

    public ReversiBoard(ReversiBoard board) {
        this.game = new int[64];

        int i;
        for (i = 0; i < 64; ++i) {
            this.game[i] = board.game[i];
        }

        this.step = board.step;
        this.undoCounter = board.undoCounter;
        this.undo = new int[512];

        for (i = 0; i < 512; ++i) {
            this.undo[i] = board.undo[i];
        }
    }

    public int getStep() {
        return this.step;
    }

    public void reset() {
        this.game = new int[64];
        this.game[27] = 1;
        this.game[36] = 1;
        this.game[28] = -1;
        this.game[35] = -1;
        this.step = 4;
        this.undoCounter = 0;
        this.undo = new int[512];
    }

    public void setColour(int pos, int colour) {
        this.game[pos] = colour;
    }

    public int getColour(int pos) {
        return this.game[pos];
    }

    public boolean doMove(int move, int colour) {
        return this.doMove(move, colour, false);
    }

    private boolean doMove(int move, int colour, boolean noExecute) {
        if (trace.isDebugEnabled() && !noExecute) {
            trace.debug("doMove(" + move + "," + colour + ")");
        }

        if (this.game[move] != 0) {
            trace.warn("Move (" + (colour == 1 ? "white" : "black") + ") to pos " + move + " is illegal. There is already " + (this.game[move] == 1 ? "white" : "black"));
            return false;
        } else {
            boolean success = false;
            int pos;
            if (move >= 16) {
                for (pos = move - 8; pos > 0 && this.game[pos] == -colour; pos -= 8) {
                }

                if (pos >= 0 && move - pos > 8 && this.game[pos] == colour) {
                    if (noExecute) {
                        return true;
                    }

                    if (trace.isDebugEnabled()) {
                        trace.debug("found a legal move at " + move + " (north)");
                    }

                    pos += 8;

                    for (success = true; pos <= move; pos += 8) {
                        this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                        this.game[pos] = colour;
                    }
                }

                if (move % 8 >= 2) {
                    for (pos = move - 9; pos > 0 && pos % 8 != 0 && this.game[pos] == -colour; pos -= 9) {
                    }

                    if (pos >= 0 && move - pos > 9 && this.game[pos] == colour) {
                        if (noExecute) {
                            return true;
                        }

                        if (trace.isDebugEnabled()) {
                            trace.debug("found a legal move at " + move + " (north west)");
                        }

                        pos += 9;

                        for (success = true; pos <= move; pos += 9) {
                            this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                            this.game[pos] = colour;
                        }
                    }
                }

                if (move % 8 <= 5) {
                    for (pos = move - 7; pos > 0 && pos % 8 != 7 && this.game[pos] == -colour; pos -= 7) {
                    }

                    if (pos >= 0 && move - pos > 7 && this.game[pos] == colour) {
                        if (noExecute) {
                            return true;
                        }

                        if (trace.isDebugEnabled()) {
                            trace.debug("found a legal move at " + move + " (north east); " + pos + ";" + this.game[pos]);
                        }

                        pos += 7;

                        for (success = true; pos <= move; pos += 7) {
                            this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                            this.game[pos] = colour;
                        }
                    }
                }
            }

            if (move % 8 >= 2) {
                for (pos = move - 1; pos % 8 != 0 && this.game[pos] == -colour; --pos) {
                }

                if (move - pos > 1 && this.game[pos] == colour) {
                    if (noExecute) {
                        return true;
                    }

                    if (trace.isDebugEnabled()) {
                        trace.debug("found a legal move at " + move + " (west)");
                    }

                    ++pos;

                    for (success = true; pos <= move; ++pos) {
                        this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                        this.game[pos] = colour;
                    }
                }
            }

            if (move % 8 <= 5) {
                for (pos = move + 1; pos % 8 != 7 && this.game[pos] == -colour; ++pos) {
                }

                if (pos - move > 1 && this.game[pos] == colour) {
                    if (noExecute) {
                        return true;
                    }

                    if (trace.isDebugEnabled()) {
                        trace.debug("found a legal move at " + move + " (east)");
                    }

                    --pos;

                    for (success = true; pos >= move; --pos) {
                        this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                        this.game[pos] = colour;
                    }
                }
            }

            if (move < 48) {
                for (pos = move + 8; pos < 63 && this.game[pos] == -colour; pos += 8) {
                }

                if (pos <= 63 && pos - move > 8 && this.game[pos] == colour) {
                    if (noExecute) {
                        return true;
                    }

                    if (trace.isDebugEnabled()) {
                        trace.debug("found a legal move at " + move + " (south)");
                    }

                    pos -= 8;

                    for (success = true; pos >= move; pos -= 8) {
                        this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                        this.game[pos] = colour;
                    }
                }

                if (move % 8 >= 2) {
                    for (pos = move + 7; pos < 63 && pos % 8 != 0 && this.game[pos] == -colour; pos += 7) {
                    }

                    if (pos <= 63 && pos - move > 7 && this.game[pos] == colour) {
                        if (noExecute) {
                            return true;
                        }

                        if (trace.isDebugEnabled()) {
                            trace.debug("found a legal move at " + move + " (south west)");
                        }

                        pos -= 7;

                        for (success = true; pos >= move; pos -= 7) {
                            this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                            this.game[pos] = colour;
                        }
                    }
                }

                if (move % 8 <= 5) {
                    for (pos = move + 9; pos < 63 && pos % 8 != 7 && this.game[pos] == -colour; pos += 9) {
                    }

                    if (pos <= 63 && pos - move > 9 && this.game[pos] == colour) {
                        if (noExecute) {
                            return true;
                        }

                        if (trace.isDebugEnabled()) {
                            trace.debug("found a legal move at " + move + " (south east)");
                        }

                        pos -= 9;

                        for (success = true; pos >= move; pos -= 9) {
                            this.undo[this.undoCounter++] = pos * 10 - this.game[pos] + 1;
                            this.game[pos] = colour;
                        }
                    }
                }
            }

            if (success) {
                this.undo[this.undoCounter++] = -1;
                ++this.step;
            }

            return success;
        }
    }

    public int[] getMoves(int col) {
        int childCount = 0;
        int[] children = new int[32];

        for (int i = 0; i < 64; ++i) {
            if (this.game[i] == 0 && this.doMove(i, col, true)) {
                if (trace.isDebugEnabled()) {
                    trace.debug("found child at pos " + i);
                }

                children[childCount++] = i;
            }
        }

        children[childCount] = -1;
        return children;
    }

    public void undo() {
        this.undoCounter -= 2;
        if (trace.isDebugEnabled()) {
            trace.debug("undoCounter=" + this.undoCounter);
        }

        while (this.undoCounter >= 0 && this.undo[this.undoCounter] != -1) {
            if (trace.isDebugEnabled()) {
                trace.debug("undo pos " + this.undo[this.undoCounter] / 10 + " to value " + (1 - this.undo[this.undoCounter] % 10));
            }

            this.game[this.undo[this.undoCounter] / 10] = 1 - this.undo[this.undoCounter] % 10;
            --this.undoCounter;
        }

        ++this.undoCounter;
        --this.step;
    }

    public int getScore() {
        int sum = 0;

        for (int i = 0; i < 64; ++i) {
            sum += this.game[i];
        }

        return sum;
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();

        for (int i = 0; i < 64; ++i) {
            if (i % 8 == 0) {
                strb.append("    ---------------------------------\n");
                strb.append(8 * (i / 8)).append(i < 16 ? "   " : "  ");
                strb.append("|");
            }

            if (this.game[i] == 0) {
                strb.append("   ");
            } else {
                strb.append(this.game[i] == 1 ? " O " : " X ");
            }

            strb.append("|");
            if (i % 8 == 7) {
                strb.append("\n");
            }
        }

        strb.append("    ---------------------------------\n");
        strb.append("Step=").append(this.step).append("; score=")
                .append((this.step - getScore()) / 2 + ":" + (this.step + getScore()) / 2);
        return strb.toString();
    }

    public boolean equals(Object obj) {
        return obj != null && obj instanceof ReversiBoard && this.equals((ReversiBoard) obj);
    }

    public Object getId() {
        long l = 0L;
        long b = 0L;

        for (int i = 0; i < 64; ++i) {
            switch (this.game[i]) {
                case -1:
                    l *= 2L;
                    b = b * 2L + 1L;
                    break;
                case 1:
                    l = l * 2L + 1L;
                    b *= 2L;
                    break;
                default:
                    l *= 2L;
                    b *= 2L;
            }
        }

        List li = new ArrayList();
        li.add(l);
        li.add(b);
        return li;
    }

    public boolean equals(ReversiBoard obj) {
        for (int i = 0; i < 64; ++i) {
            if (this.game[i] != obj.game[i]) {
                return false;
            }
        }

        return true;
    }
}
