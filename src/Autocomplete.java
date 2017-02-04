import edu.princeton.cs.algs4.*;

import java.util.Comparator;

/**
 * Created by Katyana on 4/20/2016.
 *
 * Autocomplete Class. Uses Term and BinarySearchDeluxe to find and sort all matches to terms according to
 * lexicographic order and weight.
 */

public class Autocomplete {

    private final Term[] terms2; // defensive copy
    private Comparator byPrefix; // comparators from Term
    private Comparator byWeight;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms){
        if(terms == null) { throw new java.lang.NullPointerException("terms is null"); }
        for(Term item : terms){
            if (item == null){
                throw new java.lang.NullPointerException("terms cannot have a null value");
            }
        }
        // unfortunate defensive copy
        terms2 = new Term[terms.length];
        for(int i=0;i<terms.length; i++) {
            this.terms2[i] = terms[i];
        }
        // sort by prefix
        MergeX.sort(this.terms2);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix){
        if(prefix == null) { throw new java.lang.NullPointerException("prefix is null"); }

        //temporary term
        Term one = new Term(prefix, 0);

        byPrefix = Term.byPrefixOrder(prefix.length());
        byWeight = Term.byReverseWeightOrder();

        int location = BinarySearchDeluxe.firstIndexOf(this.terms2, one, byPrefix);
        int location2 = BinarySearchDeluxe.lastIndexOf(this.terms2, one, byPrefix);

        Term[] news = new Term[location2-location+1]; // array to store all matching terms!
        if( location == -1 || location2 == -1){       // if no matches, do nothing
            news = new Term[0];
            return news;
        }

        int j=0;
        for(int i=location; i<=location2; i++){     // fill up new array
            news[j] = terms2[i];
            j++;
        }

        MergeX.sort(news, byWeight); //sort by weight
        return news;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix){
        if(prefix == null) { throw new java.lang.NullPointerException("prefix is null"); }
        Term one = new Term(prefix, 0);
        byPrefix = Term.byPrefixOrder(prefix.length());
        byWeight = Term.byReverseWeightOrder();
        int location = BinarySearchDeluxe.firstIndexOf(this.terms2, one, byPrefix);
        int location2 = BinarySearchDeluxe.lastIndexOf(this.terms2, one, byPrefix);
        return location2-location+1;
    }


    // TEST CLIENT - kinda.. weird
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print out the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
