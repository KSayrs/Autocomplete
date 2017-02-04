import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

/**
 * Created by Katyana on 4/19/2016.
 *
 * Term Object. Each Term has a query and a weight.
 *
 * This class includes methods to compare the various aspects of these terms.
 *
 */

public final class Term implements Comparable<Term> {

    private final String query; // a term's query
    private final long weight;  // a term's weight

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight){
        if(query == null) { throw new java.lang.NullPointerException("Query is null."); }
        if(weight <0) { throw new java.lang.IllegalArgumentException("Weight is negative."); }

        this.query = query;
        this.weight = weight;
    }

    // tiny private compare weight class
    private static class CompareWeight implements Comparator<Term>{
        private long aweight;
        private long bweight;

        public int compare(Term a, Term b){
            aweight = a.weight;
            bweight = b.weight;
            return (int)bweight - (int)aweight; // b-a for reverse order
        }
    }

    // Compare by prefix
    private static class ComparePrefix implements Comparator<Term>{
        private String aq;  // a query
        private String bq;  // b query
        private final int r; // prefix length
        // Constructor, set r
        private ComparePrefix(int r){
            this.r = r;
        }
        // Checks string prefixes - if a string is smaller than the prefix, count it as not a match
        public int compare(Term a, Term b){
            aq = a.query;
            bq = b.query;
            int smaller;   // the smaller length
            if(aq.length()<bq.length()) {smaller = aq.length(); }   // find smallest string length
            else { smaller = bq.length(); }
            if (smaller < r) { //if the smaller string is less than the prefix length
                // if the substrings up to size smaller are the same
                if(aq.substring(0, smaller).compareTo(bq.substring(0, smaller)) == 0){
                    if (aq.length() == smaller) { return -1; }    // and the Term that isn't the key (b) is the small one, throw it out
                    else return 1;
                }
                else return aq.substring(0, smaller).compareTo(bq.substring(0, smaller)); //else just return the compare
            }
            else return aq.substring(0, r).compareTo(bq.substring(0, r)); // normal string compare
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder(){
        return new CompareWeight();
    }

    // Compares the two terms in lexicographic order but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r){
        if(r < 0) { throw new java.lang.IllegalArgumentException("r cannot be negative"); } else {
            return new ComparePrefix(r);
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that){
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString(){
        return this.weight + "\t" + this.query;
    }

    // unit testing (required)
    public static void main(String[] args){
        // smaller means it comes earlier
        Term testTerm = new Term("This is the query", 12);
        Term testTerm2 = new Term("This query is second", 10);
        Term testTerm3 = new Term("Third", 10);
        int r = 4;

        StdOut.println(testTerm.toString());
        StdOut.println(testTerm2.toString());

        Comparator weight = byReverseWeightOrder();
        Comparator prefix = byPrefixOrder(r);

        StdOut.println("Weight in reverse order: " + weight.compare(testTerm, testTerm2));
        StdOut.println("Weight in reverse order: " + weight.compare(testTerm2, testTerm3));
        StdOut.println("Prefix by " + r + " : " + prefix.compare(testTerm, testTerm2));
        if(weight.compare(testTerm2, testTerm3) == 0){
            StdOut.println("test2 and test3 have the same weight");
        }

    }
}
