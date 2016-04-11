import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.LinkedBag;
import edu.princeton.cs.algs4.LinearProbingHashST;

public class WordNet {

    private Digraph G;
    private LinearProbingHashST<String, LinkedBag<Integer>> hashTable;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
           throw new java.lang.NullPointerException();
        hashTable = new LinearProbingHashST<String, LinkedBag<Integer>>();
        In synFile = new In(synsets);
        In hypFile = new In(hypernyms);
        String line;
        String[] fields;
        String[] syns;
        int synCount = 0;
        while (synFile.hasNextLine()) {
            line = synFile.readLine();
            fields = line.split(","); 
            Integer synId = Integer.parseInt(fields[0]);
            syns = fields[1].split(" ");
            for (int i = 0; i < syns.length; ++i) {
                LinkedBag<Integer> bag = hashTable.get(syns[i]);
                if (bag == null) {
                    bag = new LinkedBag<Integer>();
                    bag.add(synId);
                    hashTable.put(syns[i], bag);
                } else {
                    bag.add(synId);
                }
            }
            synCount++;
        }

        G = new Digraph(synCount);
        while (hypFile.hasNextLine) {
            line = hypFile.readLine();
            fields = line.split(",");
            Integer v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; ++i) {
               Integer w = Integer.parseInt(fields[i]);
               G.addEdge(v, w);
            }
        }
    }

   // returns all WordNet nouns
   public Iterable<String> nouns() {
       return null;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
       if (word == null)
           throw new java.lang.NullPointerException();
       return true;
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
       if (nounA == null || nounB == null)
           throw new java.lang.NullPointerException();
       return -1;
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
       if (nounA == null || nounB == null)
           throw new java.lang.NullPointerException();
       return "fuck";
   }

   // do unit testing of this class
   public static void main(String[] args) {
       WordNet wn = new WordNet(null, null);
   }
}
