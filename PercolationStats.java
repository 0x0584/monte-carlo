import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats
{
	private static final String ExcpTrials = "Number trial is incorrect";
	private static final double CONFIDENCE_95 = 1.96;

	private final double t_sqrt;
	private final double mean_value;
	private final double stddev_value;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
	{
		if (trials <= 0)
			throw new IllegalArgumentException(ExcpTrials);

		t_sqrt = Math.sqrt(trials);
		double[] threshold = new double[trials];
		for (int t = 0; t < trials; ++t) {
			Percolation perc = new Percolation(n);
			while (!perc.percolates())
				perc.open(StdRandom.uniform(1, n + 1),
						  StdRandom.uniform(1, n + 1));
			double open = perc.numberOfOpenSites();
			threshold[t] = open / (n * n);
		}
		mean_value = StdStats.mean(threshold);
		stddev_value = StdStats.stddev(threshold);
	}

	// sample mean of percolation threshold
	public double mean()
	{
		return mean_value;
	}

	// sample standard deviation of percolation threshold
	public double stddev()
	{
		return stddev_value;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo()
	{
		return mean_value - CONFIDENCE_95 * stddev_value / t_sqrt;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi()
	{
		return mean_value + CONFIDENCE_95 * stddev_value / t_sqrt;
	}

	// test client (see below)
	public static void main(String[] args)
	{
		int n = 50;
		int trials = 10;

		if (args.length > 0)
			n = Integer.parseInt(args[0]);
		if (args.length > 1)
			trials = Integer.parseInt(args[1]);

		PercolationStats stats = new PercolationStats(n, trials);

		StdOut.printf("%-23s = %.17f\n", "mean", stats.mean());
		StdOut.printf("%-23s = %.17f\n", "stddev", stats.stddev());
		StdOut.printf("%-23s = [%.17f, %.17f]\n", "95% confidence interval",
					   stats.confidenceLo(), stats.confidenceHi());
	}
}
