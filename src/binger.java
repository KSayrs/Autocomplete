import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;


public class binger {

    private static int search(String word, HashMap map){
        // iterate through hashmap
        // Get a set of the entries
        //Set set = map.entrySet();
        int num = 0;
        // Get an iterator
        Iterator i = map.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            String key = (String)me.getKey();

            // if the small word equals the prefix of any of the bigger words
            if(word.equals(key.substring(0,word.length()))){
                int val = (int)me.getValue();
                num = val+1;
                break;
            }
            System.out.print(me.getKey() + ": " + me.getValue());
            System.out.println();
        }
        return num;
    }


    public static void main(String[] args){
        int numwords = 0;
        //ArrayList inp = new ArrayList();
        ArrayList counts = new ArrayList();


        Scanner sc = new Scanner(System.in);
        // skipping the first one here
        if(sc.hasNextInt()){
            numwords = parseInt(sc.next());
        }
        // make hashmap
        HashMap store = new HashMap(numwords);
        int smallestprefixsize = MAX_VALUE;
        int largestwordsize = 0;
        boolean larger = false;
        boolean smaller = false;

        // BEGIN LOOP
        while (sc.hasNext()){
            String word = sc.next();

            // if even smaller
            if (word.length() < smallestprefixsize) {
                smallestprefixsize = word.length();
                smaller = true;
                System.out.println("This one is smaller!");
            }

            // if larger
            if(word.length() > largestwordsize){
                largestwordsize = word.length();
                System.out.println("This one is larger!");
            }

            try { // if the word is already in the hashmap
                int num = (int)store.get(word);
                store.put(word, num+1);
                System.out.println("Duplicate!!!! :" + word + " : " + num+1);
            }
            catch (NullPointerException e){
                //System.out.print("nullpexcept");

                if(smaller){
                    //int c = 0;
                    int c = search(word, store);
                    System.out.println("searched: " + word + " : " + c);
                    store.put(word, c);
                    counts.add(c);
                    //count = c;
                }

                else {
                    store.put(word, 0);
                    System.out.println("new one added!");
                    counts.add(0);
                }
            }


           // for(int i=0; i<inp.size()-1; i++){
              //  store.put(inp.get(i), 0);
               /* String prev = (String) inp.get(i);
                // if they're equal
                if(word.equals(prev)){
                   count++;
                }
                // if word is smaller
                else if(word.length() < prev.length()){
                    if (word.equals(prev.substring(0, word.length()))){
                       count++;
                    }
                } */

          //  }
            //counts.add(count);
            smaller = false;
        }

        System.out.println(store);

        // Get a set of the entries
        Set set = store.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }

       // for (Object count : counts) {
        //    System.out.println(count);
        //}
    }
}