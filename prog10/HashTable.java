package prog10;

import java.util.Map;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Set;
import java.util.AbstractSet;
import java.util.NoSuchElementException;
import java.io.PrintWriter;
import java.io.FileWriter;

public class HashTable<K, V> extends AbstractMap<K, V> {
  private class Entry implements Map.Entry<K, V> {
    K key;
    V value;
    Entry next;

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V value) {
      V oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    Entry (K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  private final static int DEFAULT_CAPACITY = 5;
  private Entry[] table = new HashTable.Entry[DEFAULT_CAPACITY];
  private int size;

  private int hashIndex (Object key) {
    int index = key.hashCode() % table.length;
    if (index < 0)
      index += table.length;
    return index;
  }

  public V put (K key, V value) {
    //System.out.println("put " + key + " " + value + " hash index " + hashIndex(key));

    // EXERCISE 1
    Entry entry = find(key);
    if (entry != null) {
      // What do you do if you find the Entry with that key?
      V val = entry.value;
      entry.value = value;
      return val;
    }

    if (size == table.length)
      rehash(2 * table.length);

    // Create a new Entry and add it to the appropriate list.
    Entry newEntry = new Entry(key, value);
    int index = hashIndex(key);
    newEntry.next = table[index];
    table[index] = newEntry;
    size++;




    // Increment size.

    return null;
  }

  private Entry find (Object key) {
    int index = hashIndex(key);


    for(Entry e = table[index]; e != null; e = e.next){
      if(e.key.equals(key)){
        return e;
      }
    }
    // EXERCISE 2
    // Search the list stored in table[index]
    // for an Entry with that key.
    // If you find it, return it.
    return null;
  }

  public boolean containsKey (Object key) {
    return find(key) != null;
  }

  public V get (Object key) {
    Entry entry = find(key);
    if (entry == null)
      return null;
    return entry.value;
  }

  private void rehash (int newCapacity) {
    System.out.println("rehash " + newCapacity);
    Entry[] oldTable = table;
    table = new HashTable.Entry[newCapacity];

    for (int i = 0; i < oldTable.length; i++) {
      // EXERCISE 3
      // Move every Entry in oldTable[i] to the correct index list in table.
      for(Entry entry = oldTable[i]; entry != null; entry = entry.next ){
        Entry newEntry = new Entry(entry.key, entry.value);
        int index = hashIndex(entry.key);
        newEntry.next = table[index];
        table[index] = newEntry;
      }
    }
  }

  public V remove (Object key) {
    int index = hashIndex(key);
    if(table[index] == null){
      return null;
    }
    if((table[index].key.equals(key))){

      V value = table[index].value;
      table[index] = table[index].next;
      size--;

      if(size <= table.length/4){
        rehash(table.length/2);
      }

      return value;
    }
    Entry previous = table[index];
    Entry current = previous;

    while(current != null){
      current = current.next;

      if(current == null){
        return null;
      }

      if(current.key == key){
        V value = current.value;
        previous.next = current.next;
        size--;

        if(size <= table.length/4){
          rehash(table.length/2);
        }

        return value;
      }

      previous = previous.next;

    }




    // Return null otherwise.
    return null;
  }


  private Iterator<Map.Entry<K, V>> entryIterator () {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<K, V>> {
    // EXERCISE 5
    int index;
    Entry entry;

    private EntryIterator () {
      // Set index to the the index of the first non-empty list
      // or table.length if there isn't one.
      // Set entry to the first entry in that list.
      int i = 0;
      for(i = 0; i < table.length; i++){
        if(table[i] != null){
          index = i;
          entry = table[i];
          break;
        }
      }
      if(i == table.length || table[i] == null){
        index = table.length;
      }
      /*

      int counter = 0;
      for(int i=0; table[i].getKey() != null; i++){
        counter++;
        index = i;
      }
      if(counter == table.length){
        index = table.length;
      }
      entry = table[index];

       */
    }

    public boolean hasNext () {
      // You just need to use the value of index.
      return index < table.length;
    }

    public Map.Entry<K, V> next () {
      if (!hasNext())
        throw new NoSuchElementException();

      Entry nextEntry = entry;

      // Set entry to the next entry in the list.
      // Or if there isn't one,
      // set index to the index of the next non-null list,
      // or table.length if there isn't one,
      // and set entry to the first entry in that list.
      if (entry.next != null) {
        entry = entry.next;

      } else {
        int i = 0;
        for (i = index + 1; i < table.length; i++) {
          if (table[i] != null) {
            index = i;
            entry = table[i];
            break;
          }
        }

        if (i == table.length || table[i] == null) {
          index = table.length;
        }
      }
      return nextEntry;
    }

    public void remove () {}
  }

  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }

  private class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<K, V>> iterator () {
      return entryIterator();
    }

    public void remove () {}
  }

  public String toString () {
    String ret = super.toString() + "\n";
    for (int i = 0; i < table.length; i++) {
      ret = ret + i + ":";
      for (Entry entry = table[i]; entry != null; entry = entry.next)
        ret = ret + " " + entry.key + " " + entry.value;
      ret = ret + "\n";
    }
    return ret;
  }

  static void print (PrintWriter out, Object s) {
    out.println(s);
    System.out.println(s);
  }

  public static void main (String[] args) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new FileWriter("hashtable-output.txt"));
    } catch (Exception e) {
      System.out.println(e);
      return;
    }

    HashTable<String, Integer> table =
            new HashTable<String, Integer>();

    print(out, "put Brad 46 hash index " + table.hashIndex("Brad") + " returns " + table.put("Brad", 46));
    print(out, table);
    print(out, "put Hal 10 hash index " + table.hashIndex("Hal") + " returns " + table.put("Hal", 10));
    print(out, table);
    print(out, "put Brad 60 hash index " + table.hashIndex("Brad") + " returns " + table.put("Brad", 60));
    print(out, table);
    print(out, "put Hal 24 hash index " + table.hashIndex("Hal") + " returns " + table.put("Hal", 24));
    print(out, table);
    print(out, "put Kyle 6 hash index " + table.hashIndex("Kyle") + " returns " + table.put("Kyle", 6));
    print(out, table);
    print(out, "put Lisa 43 hash index " + table.hashIndex("Lisa") + " returns " + table.put("Lisa", 43));
    print(out, table);
    print(out, "put Lynne 43 hash index " + table.hashIndex("Lynne") + " returns " + table.put("Lynne", 43));
    print(out, table);
    print(out, "hash code of Victor is " + "Victor".hashCode());
    print(out, "put Victor 46 hash index " + table.hashIndex("Victor") + " returns " + table.put("Victor", 46));
    print(out, table);
    print(out, "put Zoe 6 hash index " + table.hashIndex("Zoe") + " returns " + table.put("Zoe", 6));
    print(out, table);
    print(out, "put Zoran 76 hash index " + table.hashIndex("Zoran") + " returns " + table.put("Zoran", 76));
    print(out, table);

    for (String key : table.keySet())
      System.out.print(key + " ");
    System.out.println();

    print(out, "remove Fred returns " + table.remove("Fred"));
    print(out, table);
    print(out, "remove Hal returns " + table.remove("Hal"));
    print(out, table);
    print(out, "remove Brad returns " + table.remove("Brad"));
    print(out, table);
    print(out, "remove Lynne returns " + table.remove("Lynne"));
    print(out, table);
    print(out, "remove Lisa returns " + table.remove("Lisa"));
    print(out, table);
    print(out, "remove Lisa returns " + table.remove("Lisa"));
    print(out, table);
    print(out, "remove Zoran returns " + table.remove("Zoran"));
    print(out, table);
    print(out, "remove Zoe returns " + table.remove("Zoe"));
    print(out, table);
    print(out, "remove Victor returns " + table.remove("Victor"));
    print(out, table);
    print(out, "remove Hal returns " + table.remove("Hal"));
    print(out, table);

    out.close();
  }
}