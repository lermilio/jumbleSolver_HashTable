package prog10;

import prog02.GUI;
import prog02.UserInterface;
import prog07.BST;
import prog10.HashTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

public class Jumble {
    /**
     * Sort the letters in a word.
     * @param word Input word to be sorted, like "computer".
     * @return Sorted version of word, like "cemoptru".
     */
    public static String sort (String word) {
        char[] sorted = new char[word.length()];
        for (int n = 0; n < word.length(); n++) {
            char c = word.charAt(n);
            int i = n;

            while (i > 0 && c < sorted[i-1]) {
                sorted[i] = sorted[i-1];
                i--;
            }

            sorted[i] = c;
        }

        return new String(sorted, 0, word.length());
    }

    public static void main (String[] args) {
        UserInterface ui = new GUI("Jumble");
        // UserInterface ui = new ConsoleUI();

        //Map<String,String> map = new TreeMap<String,String>();
        //Map<String,String> map = new PDMap();
        //Map<String,String> map = new LinkedMap<String,String>();
        //Map<String,String> map = new SkipMap<String,String>();
        //Map<String,String> map = new BST<String,String>();
        Map<String,String> map = new HashTable<String,String>();
        Map<String,List<String>> map2 = new HashTable<String,List<String>>();

        Scanner in = null;
        do {
            String fileName = null;
            try {
                fileName = ui.getInfo("Enter word file.");
                if (fileName == null)
                    return;
                in = new Scanner(new File(fileName));
            } catch (Exception e) {
                ui.sendMessage(fileName + " not found.");
                System.out.println(e);
                System.out.println("Try again.");
            }
        } while (in == null);

        int n = 0;
        while (in.hasNextLine()) {
            String word = in.nextLine();
            if (n++ % 1000 == 0)
                System.out.println(word + " sorted is " + sort(word));

            // EXERCISE: Insert an entry for word into map.
            // What is the key?  What is the value?
            ///
            map.put(sort(word), word);
            //map.remove(sort(word));
            if(map2.containsKey(sort(word))){
                map2.get(sort(word)).add(word);
            }else{
                List <String> wordList = new ArrayList<>();
                wordList.add(word);
                map2.put(sort(word), wordList);
            }
            ///
        }

        while (true) {
            String jumble = ui.getInfo("Enter jumble.");
            if (jumble == null) {
                while(true) {
                    String clue = ui.getInfo("Enter clue letters:");
                    if (clue == null) {
                        return;
                    }
                    String punString = ui.getInfo("How many letters in the first word?");
                    if (punString == null) {
                        return;
                    }
                    int punLen = Integer.parseInt(punString);
                    String sortedLetters = sort(clue);
                    int key1index = 0;
                    for (String key1 : map.keySet()) {
                        if (key1.length() == punLen) {
                            key1index = 0;
                            String key2 = "";
                            for (int i = 0; i < sortedLetters.length(); i++) {
                                if (key1index >= key1.length())
                                    key2 += sortedLetters.charAt(i);
                                else if (key1.charAt(key1index) == (sortedLetters.charAt(i)))
                                    key1index++;
                                else if (sortedLetters.charAt(1) > key1.charAt(key1index))
                                    break;
                                else
                                    key2 += sortedLetters.charAt(i);
                            }
                            if (key1index == key1.length() && map.containsKey(sort(key2))) {
                                ui.sendMessage(map.get(sort(key1)) + " " + map.get(sort(key2)));
                            }
                        }
                    }
                }
            }
            String word = null;
            // EXERCISE:  Look up the jumble in the map.
            // What key do you use?
            ///
            word = map.get(sort(jumble));
            ///

            if (word == null)
                ui.sendMessage("No match for " + jumble);
            else
                ui.sendMessage(jumble + " unjumbled is " + word);
        }
    }
}
