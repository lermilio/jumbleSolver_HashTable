OVERVIEW
*This project implements a jumble solver that helps unscramble words using a HashTable-based dictionary lookup. The program:
*Reads words from a file and stores them in a HashTable, mapping sorted letter sequences to valid words.
*Allows users to enter scrambled words (jumbles) and retrieves possible matches.
*Supports multi-word clue solving by breaking clues into smaller sub-words.

PROJECT GOALS
*Implement a fast word lookup using a HashTable.
*Support single-word and multi-word jumble solving.
*Optimize search and insertion performance with hashing techniques.

FEATURES
Jumble Solving
*Sorts letters in words to create a unique key for lookup.
*Uses HashTable to store sorted letter sequences and retrieve matching words.
*Supports multi-word solutions by breaking clues into smaller components.
Efficient Data Handling
*Reads words from a file and inserts them into the HashTable.
*Uses hashing to provide near O(1) lookup performance.
*Handles collisions effectively with linked list chaining.
User Interface
*GUI-based interface (GUI.java) for input and interaction.
*Console fallback for text-based interactions.

DATA STRUCTURES USED
*HashTable<K, V> → Stores sorted word keys mapped to valid words.
*ArrayList<String> → Stores word lists for words with the same sorted key.
*GUI Interface → Allows interactive user input.

IMPLEMENTATION DETAILS 
*Word Sorting & Hashing
*sort(String word) → Sorts characters in a word alphabetically to generate a key.
*HashTable<K, V> → Custom hash table implementation for efficient word storage.
Jumble Solving Process
*Load dictionary into the HashTable.
*Sort input jumble and search for matching keys in the HashTable.
*Retrieve matching words and display results.
*For multi-word clues, split input into possible word pairs and check both words.
Multi-Word Clue Handling
*Splits the clue into possible first and second words.
*Iterates through HashTable keys to find valid word combinations.
*Displays potential two-word matches.
