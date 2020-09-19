import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WeightedQuickUnion
{
	private int[] ids;
	private	int[] sz;

	public WeightedQuickUnion(int n) {
		ids = new int[n]; sz = new int[n];

		for (int i = 0; i < n; ++i) {
			ids[i] = i; sz[i] = 1;
		}
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public void union(int p, int q) {
		int pid = find(p), qid = find(q);

		if (pid == qid) return ;
		else if (sz[pid] < sz[qid]) {
			ids[pid] = qid; sz[qid] += sz[pid];
		} else {
			ids[Math.min(pid, qid)] = Math.max(pid, qid);
			sz[Math.max(pid, qid)] += sz[Math.min(pid, qid)];
			// if (pid < qid) {
			// 	ids[pid] = qid; sz[qid] += sz[pid];
			// } else {
			// 	ids[qid] = pid; sz[pid] += sz[qid];
			// }
		}
	}

	public int find(int i) {
		while (i != ids[i]) {
			ids[i] = ids[ids[i]];
			i = ids[i];
		}
		return i;
	}

	public static void main(String[] args){

		int N = StdIn.readInt(), unions = 0, count = 0;
		WeightedQuickUnion uf = new WeightedQuickUnion(N);

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

		StdOut.println(" find 1 " + uf.find(1));
		StdOut.println(" find 1 " + uf.connected(1, 2));
		StdOut.println(" find 2 " + uf.find(2));
		StdOut.println(" find 6 " + uf.find(6));
		StdOut.println(" find 9 " + uf.find(9));

		StdOut.println(" WeightedQuickUnion >>> unions: " + unions +
					   " - count: " + count);
	}
}
