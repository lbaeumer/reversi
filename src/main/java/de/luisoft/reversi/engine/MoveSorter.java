//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MoveSorter {
    private static final Logger trace = LoggerFactory.getLogger(MoveSorter.class);
    private static final int MAX_SIZE = 1000000;
    private static final int MAX_SORT = 5;
    private final Map map = new HashMap();
    private final int[] val = new int[5];
    private final int[] move = new int[5];
    private final int[] tmp = new int[32];
    private int counter = 0;

    public MoveSorter() {
        for (int i = 0; i < 5; ++i) {
            this.val[i] = -65535;
            this.move[i] = -1;
        }

    }

    public int[] getMoves(ReversiBoard board, int colour) {
        int[] m = board.getMoves(colour);
        if (m[0] != -1 && m[1] != -1) {
            Long l = (Long) this.map.get(board.getId());
            if (l == null) {
                return m;
            } else {
                long lo = l;
                if (trace.isDebugEnabled()) {
                    trace.debug("getMoves for board " + board.getId().hashCode() + "; sort=" + l);
                    trace.debug("unsorted moves=" + this.print(m));
                }

                int i;
                for (i = 0; m[i] != -1; ++i) {
                    this.tmp[i] = m[i];
                }

                this.tmp[i] = m[i];

                for (i = 0; lo > 0L; ++i) {
                    m[i] = (int) (lo % 100L);
                    lo /= 100L;
                }

                int st = i;

                int t;
                for (int j = 0; (t = this.tmp[j]) != -1; ++j) {
                    boolean ex = false;

                    for (int k = 0; k < st; ++k) {
                        if (m[k] == t) {
                            ex = true;
                            break;
                        }
                    }

                    if (!ex) {
                        m[i] = t;
                        ++i;
                    }
                }

                if (trace.isDebugEnabled()) {
                    trace.debug("sorted moves=" + this.print(m));
                }

                return m;
            }
        } else {
            return m;
        }
    }

    private String print(int[] m) {
        StringBuilder st = new StringBuilder();
        int i = 0;

        while (m[i] != -1) {
            st.append(m[i++]).append(";");
        }

        return st.toString();
    }

    private void addMove(int m, int v) {
        if (this.counter < 5) {
            this.val[this.counter] = v;
            this.move[this.counter] = m;
            ++this.counter;
        } else {
            for (int i = 0; i < 5; ++i) {
                if (this.val[i] < v) {
                    this.val[i] = v;
                    this.move[i] = m;
                    break;
                }
            }
        }

    }

    public void clear() {
        this.map.clear();
    }

    public void commit(ReversiBoard board, int[] moves, int[] values) {
        if (this.map.size() > 1000000 && this.map.get(board.getId()) == null) {
            trace.info("map too large. Ignore entry");
        } else {
            int i = 0;

            for (this.counter = 0; moves[i] != -1; ++i) {
                this.addMove(moves[i], values[i]);
            }

            for (i = 4; i >= 1; --i) {
                for (int j = 0; j < i; ++j) {
                    if (this.val[j] > this.val[j + 1]) {
                        int t = this.val[j + 1];
                        this.val[j + 1] = this.val[j];
                        this.val[j] = t;
                        t = this.move[j + 1];
                        this.move[j + 1] = this.move[j];
                        this.move[j] = t;
                    }
                }
            }

            long m = 0L;

            for (i = 0; i < 5 && this.move[i] == -1; ++i) {
            }

            while (i < 5 && this.move[i] != -1) {
                m *= 100L;
                m += this.move[i];
                ++i;
            }

            this.map.put(board.getId(), m);
            if (trace.isDebugEnabled()) {
                trace.debug("commit board; " + board.getId().hashCode() + " sort=" + m);
            }

            for (i = 0; i < 5; ++i) {
                this.val[i] = -65535;
                this.move[i] = -1;
            }

        }
    }
}
