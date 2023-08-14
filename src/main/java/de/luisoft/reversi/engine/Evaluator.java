//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package de.luisoft.reversi.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Evaluator {
    private static final Logger trace = LoggerFactory.getLogger(Evaluator.class);
    private static final int[] f;
    private static final int[] field;

    static {
        f = new int[]{512, -128, 64, 8, -196, 8, 4, 18, 16, 24};
        field = new int[]{f[0], f[1], f[2], f[3], f[3], f[2], f[1], f[0], f[1], f[4], f[5], f[6], f[6], f[5], f[4], f[1], f[2], f[5], f[7], f[8], f[8], f[7], f[5], f[2], f[3], f[6], f[8], f[9], f[9], f[8], f[6], f[3], f[3], f[6], f[8], f[9], f[9], f[8], f[6], f[3], f[2], f[5], f[7], f[8], f[8], f[7], f[5], f[2], f[1], f[4], f[5], f[6], f[6], f[5], f[4], f[1], f[0], f[1], f[2], f[3], f[3], f[2], f[1], f[0]};
    }

    private final int depth;

    public Evaluator(int depth) {
        this.depth = depth;
    }

    public int eval(ReversiBoard n, int c) {
        int sum = 0;
        int i;
        if (this.depth < 58) {
            for (i = 0; i < 64; ++i) {
                sum += field[i] * n.getColour(i);
            }

            if (n.getColour(0) == n.getColour(1)) {
                sum += 256 * n.getColour(0);
            }

            if (n.getColour(0) == n.getColour(8)) {
                sum += 256 * n.getColour(0);
            }

            if (n.getColour(0) == n.getColour(9)) {
                sum += 256 * n.getColour(0);
            }

            if (n.getColour(7) == n.getColour(6)) {
                sum += 256 * n.getColour(7);
            }

            if (n.getColour(7) == n.getColour(14)) {
                sum += 256 * n.getColour(7);
            }

            if (n.getColour(7) == n.getColour(15)) {
                sum += 256 * n.getColour(7);
            }

            if (n.getColour(56) == n.getColour(48)) {
                sum += 256 * n.getColour(56);
            }

            if (n.getColour(56) == n.getColour(49)) {
                sum += 256 * n.getColour(56);
            }

            if (n.getColour(56) == n.getColour(57)) {
                sum += 256 * n.getColour(56);
            }

            if (n.getColour(63) == n.getColour(54)) {
                sum += 256 * n.getColour(63);
            }

            if (n.getColour(63) == n.getColour(55)) {
                sum += 256 * n.getColour(63);
            }

            if (n.getColour(63) == n.getColour(62)) {
                sum += 256 * n.getColour(63);
            }
        } else {
            for (i = 0; i < 64; ++i) {
                sum += n.getColour(i);
            }

            if (n.getStep() == 64) {
                if (sum < 0) {
                    sum = -32768;
                }

                if (sum > 0) {
                    sum = 32767;
                }
            }
        }

        return sum * c;
    }
}
