import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnion
{
	private int[] ids;

	public QuickUnion(int n) {
		ids = new int[n];

		for (int i = 0; i < n; ++i)
			ids[i] = i;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pid = find(q), qid = find(p);
		ids[pid] = qid;
	}

	public int find(int i) {
		while (i != ids[i])
			i = ids[i];
		return i;
	}

	public static void main(String[] args){

		int N = StdIn.readInt(), unions = 0, count = 0;
		QuickUnion uf = new QuickUnion(N);

		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			count++;
			if (!uf.connected(p, q)) {
				uf.union(p, q);
				StdOut.println(p + " " + q);
				unions++;
			}
		}

		StdOut.println(" QuickUnion >>> unions: " + unions +
					   " - count: " + count);
	}
}
