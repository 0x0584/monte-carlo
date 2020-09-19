import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Foo
{
	public static void main(String[] args){

		int N = StdIn.readInt();
		// WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);

		while (!StdIn.isEmpty()) {
			int p = StdIn.readInt();
			int q = StdIn.readInt();
			// if (!uf.connected(p, q)) {
				// uf.union(p, q);
				StdOut.println(p + " " + q);
			// }
		}
	}
}
