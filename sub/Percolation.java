import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	private final String ExcpNegative = "n is not valid! must be > 0";
	private final String ExcpBounds    = "site out of bound!";

	private final int TOP;
	private	final int BOTTOM;
	private final int SIZE;

	private WeightedQuickUnionUF uf;
	private boolean[] open_sites;
	private int n_open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
		throws IllegalArgumentException
	{
		if (n <= 0)
			throw new IllegalArgumentException(ExcpNegative);

		uf = new WeightedQuickUnionUF(n * n);

		n_open = 0;
		open_sites = new boolean[n * n];

		SIZE = n;
		TOP = SIZE;
		BOTTOM = 1;
	}

	private int site(int row, int col)
		throws IllegalArgumentException
	{
		if (row < BOTTOM || TOP < row
				|| col < BOTTOM || TOP < col)
			throw new IllegalArgumentException(ExcpBounds);
		row--;
		col--;
		return row * SIZE + col;
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col)
		throws IllegalArgumentException
	{
		final int[] dx = {0, 0, 1, -1};
		final int[] dy = {1, -1, 0, 0};

		if (isOpen(row, col))
			return;

		n_open += 1;
		open_sites[site(row, col)] = true;
		for (int i = 0, y, x; i < 4; ++i) {
			x = col + dx[i];
			y = row + dy[i];
			try {
				int neighbor = site(y, x);
				if (open_sites[neighbor])
					uf.union(site(row, col), neighbor);
			} catch(IllegalArgumentException e) {}
		}
	}

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
		throws IllegalArgumentException
	{
		return open_sites[site(row, col)];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col)
		throws IllegalArgumentException
	{
		if (!isOpen(row, col))
			return false;

		int canonical = uf.find(site(row, col));
		for (int bottom = 1; bottom <= SIZE; ++bottom)
			if (canonical == uf.find(site(BOTTOM, bottom)))
				return true;

		return false;
	}

	// returns the number of open sites
	public int numberOfOpenSites()
	{
		return n_open;
	}

	// does the system percolate?
	public boolean percolates()
	{
		for (int col = 1; col <= SIZE; ++col)
			if (isFull(TOP, col))
				return true;
		return false;
	}

	// test client (optional)
	public static void main(String[] args)
		throws IllegalArgumentException
	{
		int n = StdIn.readInt();
		Percolation perc = new Percolation(n);

		while (!StdIn.isEmpty())
			perc.open(StdIn.readInt(),
					  StdIn.readInt());

		StdOut.println("percolates? " + perc.percolates());
		StdOut.println("open_sites? " + perc.numberOfOpenSites());

		while (!perc.percolates())
			perc.open(StdRandom.uniform(1, n + 1),
					  StdRandom.uniform(1, n + 1));

		StdOut.println("percolates? " + perc.percolates());
		StdOut.println("open_sites? " + perc.numberOfOpenSites());
	}
}
