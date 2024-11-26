public class HuffmanEncoder {

    /**
     * You can use this node implementation for building your Huffman tree.
     * Feel free to change it, but be sure to update at least compareTo
     * so it will work correctly with the MinHeap
     */
    private class Node implements Comparable<Node>{
        public Node left, right;
        public Character c;
        public int count;

        public Node(Character c, int count) {
            this.c = c;
            this.count = count;
            left = right = null;
        }

        @Override
        public int compareTo(Node o) {
            return count - o.count;
        }

        @Override
        public String toString() {
            if(c == null) { return "null-" + count; }
            return c.toString() + "-" + count;
        }
    }


    public HuffmanEncoder() {
    }

    /**
     * Huffman Part 1: Implement this
     *
     * Generates:
     *      1) Huffman tree
     *      2) Encoded version of same
     *      3) Character bit strings
     *      4) Encoded version of string
     *
     * @param s -- String to be Huffman encoded
     */
    MinHeapPriorityQueue<Node> pq = new MinHeapPriorityQueue<>();
    String input = "";
    public void encode(String s) {
        // TODO: implement this
        input = s;
        int[] counts = new int[256];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i)]++;
        }

        for(int i = 0; i < counts.length; i++){     // for loops goes through array
            if (counts[i] > 0){     // if slot is greater than 0... (string has the letter)
                Node p = new Node((char) i, counts[i]);     // puts information into node
                pq.insert(p);    // inserts into priority queue
            }
        }
        // inserting in priority queue
        while(pq.size() > 1){
            Node parent = new Node(null, 0);
            parent.left = pq.delNext();
            parent.right = pq.delNext();

            parent.count = parent.left.count + parent.right.count;

            pq.insert(parent);
        }

        return;
    }

    /**
     * Huffman Part 2: Implement this
     *
     * Your string encoding should use the actual letter rather than an
     * ASCII code. So if you are outputting a capital letter A, write "A",
     * not 65.
     *
     * @return String encoding of Huffman tree, as per slides
     */
    public String getEncodedTree() {
        // TODO: implement this
        Node rt = pq.delNext();
        preOrderTraversal(rt);

        pq.insert(rt);  // reinsert

        return bit_string;      // returns string
    }
    String bit_string = "";     // to be used in different functions
    void preOrderTraversal(Node rt){
        if (rt == null){
            return;
        }
        if (rt.c != null){      // rt is a leaf
            bit_string = bit_string + '1' + rt.c ;
        }
        else{   // rt is an internal node
            bit_string = bit_string + '0';
        }
        preOrderTraversal(rt.left);     // go left
        preOrderTraversal(rt.right);    // go right
    }

    /**
     * Huffman Part 3: Implement this
     *
     * Returns an array of encoded bit strings.
     * Array should have 256 entries (one for each character value 0-255).
     * Entries that correspond to a character in the input should contain
     * the bitstring for that character.
     * Entries whose characters are not in the input should be null.
     *
     * Hint: the trick used for tracking counts in the slides would help here
     *
     * @return array of encoded bit strings
     */
    String[] bitArray = new String[256];
    public String[] getBitStrings() {
        // TODO: implement this
        String bit_string = "";

        Node rt = pq.delNext();     // take out tree from priority queue

        preOrderTraversalBitStrings(rt, bit_string);
        pq.insert(rt);

        return bitArray;    // returning String array
    }

    // Goes through tree
    void preOrderTraversalBitStrings(Node rt, String bitString){
        if (rt == null){
            return;
        }
        if (rt.c != null){      // rt is a leaf
            bitArray[rt.c] = bitString;     // once leaf is hit, inserts bit string made into array slot
        }
        preOrderTraversalBitStrings(rt.left, bitString + '0');  // adds onto string
        preOrderTraversalBitStrings(rt.right, bitString + '1');
    }

    /**
     * Huffman Part 4: Implement this
     *
     * Returns a string corresponding to the Huffman encoding of the
     * string provided to encode(). String should contain only '0's
     * and '1's (i.e. be binary). Other characters will be considered
     * incorrect.
     *
     * @return 'bit' encoding of overall string
     */
    public String getEncodedText() {
        // TODO: implement this
        getBitStrings();
        String encodedString = "";
        for (int i = 0; i < input.length(); i++){
            encodedString = encodedString + bitArray[input.charAt(i)];
            }
        return encodedString;
    }


    public static void main(String args[]) {
        HuffmanEncoder he = new HuffmanEncoder();

        he.encode("ABRACADABRA!");
        System.out.println(he.getEncodedTree());
        System.out.println(he.getEncodedText());
        System.out.println(he.getEncodedText().length() + " <-- should be 28");
    }

}

class MinHeapPriorityQueue<I extends Comparable<I>> implements PriorityQueue<I> {
    // Java doesn't like creating arrays of generic types
    // I'm providing code that will manage this array for you
    private I[] heap;
    private int N;      // number of elements in the heap

    // Java would normally warn us about the cast below.
    // this directive keeps that from happening
    @SuppressWarnings("unchecked")
    public MinHeapPriorityQueue() {
        N = 0;
        heap = (I[]) new Comparable[1];
    }

    /**
     * Priority Queue Part 1: Implement this
     *
     * Insert an item into the priority queue, performing
     * sift/swim operations as appropriate. You *must* maintain
     * a heap array. No fair just searching for the smallest value later
     *
     * Make a call to resize() when you need to resize the array
     *
     * @param item -- Item to insert into the queue
     */
    @Override public void insert(I item) {
        // TODO: implement this
        if (N >= heap.length){
            resize();
        }
        int hole = N;
        for (; hole > 0 && item.compareTo(heap[(hole-1)/2]) < 0 ; hole = ((hole - 1) / 2)){
            heap[hole] = heap[(hole - 1)/2];
        }
        heap[hole] = item;
        N++;
        return;
    }

    /**
     * Priority Queue Part 2: Implement this
     *
     * Extract the next item from queue, perform appropriate sift/swim
     * operations, and return the item. You *must* maintain a heap array.
     * No fair just searching for the smallest value.
     *
     * @return Item with highest priority (i.e. one with lowest value)
     */
    @Override public I delNext() {
        // TODO: implement this
        if (N == 0) {
            return null;
        }
        I min = heap[0];    // root is min element - will return
        I tmp = heap[--N];  // temp - deleted last element
        int hole = 0;
        while(2 * hole + 1 <= N){
            int child = 2 * hole + 1;

            if (child != N && (heap[child + 1].compareTo(heap[child]) < 0)){
                child ++;
            }
            if (heap[child].compareTo(tmp) < 0){
                heap[hole] = heap[child];
                hole = child;
            }
            else {
                break;
            }
        }
        heap[hole] = tmp;
        return min;
    }

    /**
     * I assume you use N to track number of items in the heap.
     * If that's not the case, update this method.
     *
     * @return number of items in the heap
     */
    @Override public int size() {
        return N;
    }

    /**
     * Doubles the size of the heap array. Takes care of allocating
     * memory and moving everything, so you don't have to.
     * Call this from insert when you need more array space.
     */
    private void resize() {
        @SuppressWarnings("unchecked")
        I[] newHeap = (I[]) new Comparable[heap.length * 2];
        for(int i = 0; i < N; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    /**
     * Returns a stringified version of the array. You shouldn't
     * need to mess with this if you use the heap array. If you do
     * something else, make sure this method still works properly
     * based on whatever your heap storage is, and the format matches exactly.
     *
     * @return a comma-separated list of array values, wrapped in brackets
     */
    @Override public String toString() {
        if(N == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0; i < N-1; i++) {
            sb.append(heap[i] + ",");
        }
        sb.append(heap[N-1] + "]");
        return sb.toString();
    }

    public static void main(String args[]) {
        MinHeapPriorityQueue<Integer> pq = new MinHeapPriorityQueue<>();

        pq.insert(5);
        pq.insert(11);
        pq.insert(8);
        pq.insert(4);
        pq.insert(3);
        pq.insert(15);
        System.out.println(pq + " <-- should be [3,4,8,11,5,15]");

        for(int i = 0; i < 6; i++) {
            System.out.println(pq.delNext());
        }
    }
}
