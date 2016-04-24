import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

    private static final int INFINITY = Integer.MAX_VALUE;
    private Digraph G;

   // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new java.lang.NullPointerException();
        // make SAP immutable in this way,
        // as Digraph is mutable we should make a deep copy
        this.G = new Digraph(G);
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V())
            throw new java.lang.IndexOutOfBoundsException();
    }
    private void validateVertex(Iterable<Integer> vBag) {
        for (int v : vBag)
            validateVertex(v);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        int minPath = 0, res = INFINITY;
        for (int i = 0; i < G.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                minPath = bfsv.distTo(i) + bfsw.distTo(i);
                if (minPath < res) res = minPath;
            }
        }
        if (res == INFINITY) return -1;
        else                          return res;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        int minPath = 0, res = INFINITY, anc = -1;
        for (int i = 0; i < G.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                minPath = bfsv.distTo(i) + bfsw.distTo(i);
                if (minPath < res) {
                    res = minPath;
                    anc = i;
                }
            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        int minPath = 0, res = INFINITY;
        for (int i = 0; i < G.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                minPath = bfsv.distTo(i) + bfsw.distTo(i);
                if (minPath < res) res = minPath;
            }
        }
        if (res == INFINITY) return -1;
        else                          return res;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(G, w);
        int minPath = 0, res = INFINITY, anc = -1;
        for (int i = 0; i < G.V(); ++i) {
            if (bfsv.hasPathTo(i) && bfsw.hasPathTo(i)) {
                minPath = bfsv.distTo(i) + bfsw.distTo(i);
                if (minPath < res) {
                    res = minPath;
                    anc = i;
                }
            }
        }
        return anc;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
