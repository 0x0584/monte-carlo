import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
	private static final String ExcpNegative = "n is not valid! must be > 0";
	private static final String ExcpBounds = "site out of bound!";

	private	static final int ROOT_BOTTOM = 0;
	private	static final int ROOT_TOP = 1;

	private final WeightedQuickUnionUF uf;
	private final int size;
	private final boolean[] open_sites;
	private int n_open;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
	{
		if (n <= 0)
			throw new IllegalArgumentException(ExcpNegative);

		size = n;
		n_open = 0;

		uf = new WeightedQuickUnionUF(2 + n * n);
		open_sites = new boolean[2 + n * n];
	}

	private int site(int row, int col)
	{
		if (row < 1 || size < row || col < 1 || size < col)
			throw new IllegalArgumentException(ExcpBounds);
		row--;
		col--;
		return 2 + row * size + col;
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col)
	{
		final int[] dx = {0, 0, 1, -1};
		final int[] dy = {1, -1, 0, 0};

		if (isOpen(row, col))
			return;

		int cell = site(row, col);

		n_open += 1;
		open_sites[cell] = true;

		if (row == 1)
		    uf.union(ROOT_BOTTOM, cell);
		if (row == size)
		    uf.union(ROOT_TOP, cell);

		for (int i = 0; i < 4; ++i)
			try {
				int x = col + dx[i];
				int y = row + dy[i];
				int neighbor = site(y, x);
				if (open_sites[neighbor])
					uf.union(cell, neighbor);
			} catch(IllegalArgumentException e) {
				continue;
			}
	}

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
	{
		return open_sites[site(row, col)];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col)
	{
		if (!isOpen(row, col))
			return false;
		return uf.find(site(row, col)) == uf.find(ROOT_BOTTOM);
	}

	// returns the number of open sites
	public int numberOfOpenSites()
	{
		return n_open;
	}

	// does the system percolate?
	public boolean percolates()
	{
		return uf.find(ROOT_TOP) == uf.find(ROOT_BOTTOM);
	}

	// test client (optional)
	public static void main(String[] args)
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
