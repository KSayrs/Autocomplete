import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

/**
 * Created by Katyana on 4/19/2016.
 *
 * BiniarySearchDeluxe - Contains methods for binary searching for the first index of a value and the last index of a value.
 */

public class BinarySearchDeluxe {

    // Returns the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator){
        if (a==null || key==null || comparator==null) { throw new java.lang.IllegalArgumentException("NO NULL ARGUMENTS"); }

        int low = 0;
        int high = a.length-1;

        while(low+1 < high) {
            int middle = (low + high) / 2;
            if(comparator.compare(a[middle], key) >= 0) {
                high = middle;
            }
            else low = middle;
        }
        if(comparator.compare(a[low], key) == 0){
            return low;
        }
        if(comparator.compare(a[high], key) == 0) {
            return high;
        }
        return -1;  // if nothing matches
    }

    // Returns the index of the last key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator){
        if (a==null || key==null || comparator==null) { throw new java.lang.IllegalArgumentException("NO NULL ARGUMENTS"); }

        int low = 0;
        int high = a.length-1;

        while(high-1 > low) {
            int middle = (low + high) / 2;
            if(comparator.compare(a[middle], key) <= 0) {
                low = middle;
            }
            else high = middle;
        }
        if(comparator.compare(a[high], key) == 0) {
            return high;
        }
        if(comparator.compare(a[low], key) == 0){
            return low;
        }
        return -1;  // if nothing matches
    }

    // unit testing (required)
    public static void main(String[] args){
        String a = "they say the Lion and the Lizard keep the Courts where Jamshyd Gloried and Drank Deep";
        String[] words = a.split(" ");
        Term[] wordss = new Term[words.length];

        for (int i=words.length-1; i>=0; i--){
            wordss[i] = new Term(words[i], (long)i);
            //StdOut.println(i + "\t" + wordss[i]);
        }

        Term noot = new Term("the", 4);
        wordss[2] = noot;
        Comparator comparator = Term.byPrefixOrder(1);
        Comparator compare2 = Term.byReverseWeightOrder();

        MergeX.sort(wordss, comparator);
        StdOut.print("first index of 't': " + firstIndexOf(wordss, noot, comparator));
        StdOut.println();
        StdOut.println("last index of 't': " + lastIndexOf(wordss, noot, comparator));
        for (int i=words.length-1; i>=0; i--){
            //wordss[i] = new Term(words[i], (long)i);
            StdOut.println(i + "\t" + wordss[i]);
        }

        MergeX.sort(wordss, compare2);
        StdOut.print("first index of weight 4: " + firstIndexOf(wordss, noot, compare2));
        StdOut.println();
        StdOut.println("last index of weight 4: " + lastIndexOf(wordss, noot, compare2));
       // StdOut.println(noot.toString());

        for (int i=words.length-1; i>=0; i--){
            //wordss[i] = new Term(words[i], (long)i);
            StdOut.println(i + "\t" + wordss[i]);
        }
    }
}
