import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickFind
{
	private int[] ids;

	public QuickFind(int n) {
		ids = new int[n];

		for (int i = 0; i < n; ++i)
			ids[i] = i;
	}

	public void union(int p, int q) {
		for (int i = 0; i < ids.length; ++i)
			if (ids[i] == ids[p]) ids[i] = q;
	}

	public int find(int p) {
		return ids[p];
	}

	public boolean connected(int p, int q) {
		return ids[p] == ids[q];
	}


	public static void main(String[] args){

		int n = StdIn.readInt(), unions = 0, count = 0;
		QuickFind uf = new QuickFind(n);

		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();

			count++;
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " union " + q);
				unions++;
			}
		}

		StdOut.println(" QuickFind >>> unions: " + unions +
					   " - count: " + count);
	}
}
