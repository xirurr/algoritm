import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double V = 1.96;
    private final double[] results;
    private final double mean;
    private final double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0 || n >= Integer.MAX_VALUE || trials >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Given N <= 0 || T <= 0");
        }

        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            //  Stopwatch stopwatch = new Stopwatch();
            Percolation percolation = new Percolation(n);
            int count = 0;

            while (!percolation.percolates()) {
                int i1 = StdRandom.uniform(1, n + 1);
                int j1 = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(i1, j1)) {
                    percolation.open(i1, j1);
                }
            }

            int openSites = percolation.numberOfOpenSites();
            results[i] = (double) openSites / (double) (n * n);
        }
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);


    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return mean - (V * stddev / Math.sqrt(results.length));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (V * stddev / Math.sqrt(results.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        if (n <= 0 || trials <= 0) {
            throw new IllegalStateException();
        }
        PercolationStats percolationStats = new PercolationStats(n, trials);

        String confidence = "[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]";
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

    private int countDecValue(int row, int col, int rows) {
        int decValue;
        if (row == 1) decValue = col - 1;
        else decValue = rows * (row - 1) + (col - 1);
        return decValue;
    }

}