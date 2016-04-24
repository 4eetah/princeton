import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class WordNet {

    private Digraph G;
    private SAP sapObj;
    private LinearProbingHashST<String, LinkedBag<Integer>> synToId;
    private LinearProbingHashST<Integer, String> idToSynset;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
        synToId = new LinearProbingHashST<String, LinkedBag<Integer>>();
        idToSynset = new LinearProbingHashST<Integer, String>();
        In synFile = new In(synsets);
        In hypFile = new In(hypernyms);
        String line;
        String[] fields;
        String[] syns;
        int synId;
        int synCount = 0;
        while (synFile.hasNextLine()) {
            line = synFile.readLine();
            fields = line.split(","); 
            synId = Integer.parseInt(fields[0]);
            syns = fields[1].split(" ");

            idToSynset.put(synId, fields[1]);
            for (int i = 0; i < syns.length; ++i) {
                LinkedBag<Integer> bag = synToId.get(syns[i]);
                if (bag == null) {
                    bag = new LinkedBag<Integer>();
                    bag.add(synId);
                    synToId.put(syns[i], bag);
                } else {
                    bag.add(synId);
                }
            }
            synCount++;
        }

        G = new Digraph(synCount);
        while (hypFile.hasNextLine()) {
            line = hypFile.readLine();
            fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; ++i) {
               int w = Integer.parseInt(fields[i]);
               G.addEdge(v, w);
            }
        }
        // check if Digraph is a rooted DAG
        int root = 0;
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) == 0) root++;
        }
        DirectedCycle finder = new DirectedCycle(G);
        if (root != 1 || finder.hasCycle())
            throw new java.lang.IllegalArgumentException();

        sapObj = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
       return synToId.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
       if (word == null)
           throw new java.lang.NullPointerException();
       return synToId.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        return sapObj.length(synToId.get(nounA), synToId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        int anc = sapObj.ancestor(synToId.get(nounA), synToId.get(nounB));
        return idToSynset.get(anc);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        StdOut.printf("Synset nouns:\n");
        for (String s : wn.nouns())
            StdOut.printf(s);
        StdOut.printf("\n");
        String nounA, nounB;
        StdOut.printf("Enter two wordnet nouns: ");
        while (!StdIn.isEmpty()) {
            nounA = StdIn.readString();
            nounB = StdIn.readString();
            StdOut.printf("Shortest ancestral path length(%s, %s) = %d\n", nounA, nounB, wn.distance(nounA, nounB));
            StdOut.printf("Common ancestor in SAP(%s, %s) = %s\n", nounA, nounB, wn.sap(nounA, nounB));
            StdOut.printf("Enter two wordnet nouns: ");
        }
    }
}
