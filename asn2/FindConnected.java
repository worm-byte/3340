/* Rosaline Scully
    Student ID: 250966670
    February 20, 2025

    This function takes in a binary image and finds the connected components of it.
    It prints different levels of detail depending on the connected components.
    It uses a disjoint set data structure to achieve this.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class uandf {

    //private instances for disjoint set data structure
    private int parent[];
    private int rank[];
    private int numSets;
    private boolean done;

    //constructs and initializes a disjoint-set data type with n elements, 1, 2, . . . , n.
    public uandf(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];
        numSets = n;
        done = false;

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    //creates a new set whose only member (and thus representative) is i.
    public void makeSet(int i) {
        if (parent[i] == 0) {
            parent[i] = i;
            rank[i] = 0;
            numSets++;
        }
    }

    //returns the representative of the set containing i.
    public int findSet(int i) {
        if (parent[i] != i) {
            parent[i] = findSet(parent[i]);
        }
        return parent[i];
    }

    //unites the dynamic sets that contains i and j, respectively, into a
    //new set that is the union of these two sets.
    public void unionSet(int i, int j) {
        int root_i = findSet(i);
        int root_j = findSet(j);

        if (root_i != root_j) {
            if (rank[root_i] != rank[root_j]) {
                if (rank[root_i] < rank[root_j]) {
                    parent[root_i] = root_j;
                } else {
                    parent[root_j] = root_i;
                }
            } else {
                parent[root_j] = root_i;
                rank[root_i]++;
            }
            numSets--;
        }
    }

    //returns the total number of current sets, no sets, and finalizes the
    //current sets: (i) make set() and union sets() will have no eﬀect after this operation
    //and (ii) resets the representatives of the sets so that integers from 1 to no sets will
    //be used as representatives.
    public int finalSets() {
        if (done) {
            return numSets;
        }

        int currRep = 1;
        int[] reps = new int[parent.length];

        for (int i = 1; i < parent.length; i++) {
            int root_i = findSet(i);
            if (reps[root_i] == 0) {
                reps[root_i] = currRep;
                currRep++;
            }
            parent[i] = reps[root_i];
        }

        done = true;
        return numSets;
    }
}

public class FindConnected {

    //function to read the binary image and store it in a matrix
    public static int[][] readImage(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        int rows = 0, cols = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                rows++;
                if (line.length() > cols) {
                    cols = line.length();
                }
            }
        }

        //store the binary image into a matrix. if "+" is seen, replace it with 1. Else, put a 0.
        int[][] image = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                image[i][j] = (line.charAt(j) == '+') ? 1 : 0;
            }
        }
        return image;
    }

    //find connected components within the binary image
    public static void findConnectedComponents(int[][] image) {
        int rows = image.length;
        int cols = image[0].length;

        // initialize disjoint set data structure
        uandf ds = new uandf(rows * cols);

        // map to keep track of which indices are actually used (have foreground pixels)
        Set<Integer> usedIndices = new HashSet<>();

        // create sets and union adjacent pixels
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (image[i][j] == 1) {
                    int current = i * cols + j + 1;
                    ds.makeSet(current);
                    usedIndices.add(current);

                    // check for connectivity
                    if (j > 0 && image[i][j-1] == 1) { // left
                        int left = i * cols + (j-1) + 1;
                        ds.unionSet(current, left);
                    }
                    if (i > 0 && image[i-1][j] == 1) { // up
                        int up = (i-1) * cols + j + 1;
                        ds.unionSet(current, up);
                    }
                }
            }
        }

        // create mapping from root to component label
        Map<Integer, Character> rootToLabel = new HashMap<>();
        char currentLabel = 'a';

        // count sizes of components and assign labels
        Map<Character, Integer> componentSizes = new HashMap<>();
        char[][] labeledImage = new char[rows][cols];

        // initialize labeled image with spaces
        for (int i = 0; i < rows; i++) {
            Arrays.fill(labeledImage[i], ' ');
        }

        // label components and count sizes of components
        for (int index : usedIndices) {
            int root = ds.findSet(index);
            if (!rootToLabel.containsKey(root)) {
                rootToLabel.put(root, currentLabel);
                componentSizes.put(currentLabel, 1);
                currentLabel++;
            } else {
                char label = rootToLabel.get(root);
                componentSizes.put(label, componentSizes.get(label) + 1);
            }

            // convert back to 2D coordinates
            int i = (index - 1) / cols;
            int j = (index - 1) % cols;
            labeledImage[i][j] = rootToLabel.get(root);
        }

        // Print the original binary image
        System.out.println("Input Binary Image:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(image[i][j] == 1 ? '+' : ' ');
            }
            System.out.println();
        }

        // print the labeled image with letters of alphabet
        System.out.println("\nConnected Component Image:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(labeledImage[i][j]);
            }
            System.out.println();
        }

        // sort and print components by size in a list
        System.out.println("\nSorted Component List:");
        List<Map.Entry<Character, Integer>> sortedComponents = new ArrayList<>(componentSizes.entrySet());
        sortedComponents.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<Character, Integer> entry : sortedComponents) {
            System.out.println("Size: " + entry.getValue() + ", Label: " + entry.getKey());
        }

        // print components with size > 1
        System.out.println("\nConnected Component Image (Size > 1):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char label = labeledImage[i][j];
                if (label != ' ' && componentSizes.get(label) > 1) {
                    System.out.print(label);
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }

        // print components with size > 5
        System.out.println("\nConnected Component Image (Size > 5):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char label = labeledImage[i][j];
                if (label != ' ' && componentSizes.get(label) > 5) {
                    System.out.print(label);
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    //main function that takes an input binary image and finds connected components of it
    public static void main(String[] args) {
    try {
        if (args.length < 1) {
            System.err.println("Usage: java FindConnected <input_file>");
            System.exit(1);
        }
        String filePath = args[0];
        int[][] image = readImage(filePath);
        findConnectedComponents(image);
    } catch (IOException e) {
        System.err.println("Error reading the file: " + e.getMessage());
    }
}
}
