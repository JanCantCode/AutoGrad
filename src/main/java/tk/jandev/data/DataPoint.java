package tk.jandev.data;

import java.util.Arrays;

public interface DataPoint {
    double[] input();
    double[] output();

    class SimpleDataPoint implements DataPoint {
        private final double[] in;
        private final double[] out;

        public SimpleDataPoint(double[] in, double[] out) {
            this.in = in;
            this.out = out;

        }
        @Override
        public double[] input() {
            return this.in;
        }

        @Override
        public double[] output() {
            return this.out;
        }

        @Override
        public String toString() {
            return "{ " + Arrays.toString(this.in) + " :: " + Arrays.toString(this.out) + " }";
        }
    }
}
