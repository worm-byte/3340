import sys
from heapq import heappush, heappop

class Heap:
    def __init__(self, keys, n):
        """
        Initializes a heap with the array keys of n + 1 elements where keys[0]
        contains the value for infinity. Element id is assumed to be the index of the array.
        """
        self.keys = keys.copy()  # Copy the keys array
        self.n = n
        # Heap contains tuples of (key, id)
        self.heap = [(keys[i], i) for i in range(1, n + 1)]
        # Position of element with id i in the heap array
        self.pos = {i: i-1 for i in range(1, n + 1)}
        # Build the heap
        self.build_heap()
    
    def build_heap(self):
        """Build the heap using heapify"""
        for i in range(self.n // 2 - 1, -1, -1):
            self._heapify(i)
    
    def in_heap(self, id):
        """Returns true if the element with id is in the heap."""
        return id in self.pos and self.pos[id] < len(self.heap)
    
    def is_empty(self):
        """Return true if heap is empty."""
        return len(self.heap) == 0
    
    def min_key(self):
        """Returns the minimum key of the heap."""
        if self.is_empty():
            return float('inf')
        return self.heap[0][0]
    
    def min_id(self):
        """Returns the id of the element with minimum key in the heap."""
        if self.is_empty():
            return 0
        return self.heap[0][1]
    
    def key(self, id):
        """Returns the key of the element with id in the heap."""
        if not self.in_heap(id):
            return float('inf')
        return self.keys[id]
    
    def delete_min(self):
        """Deletes the element with minimum key from the heap."""
        if self.is_empty():
            return None
        
        # Get the element with minimum key
        min_key, min_id = self.heap[0]
        
        # Replace the root with the last element in the heap
        if len(self.heap) > 1:
            self.heap[0] = self.heap.pop()
            self.pos[self.heap[0][1]] = 0
            self._heapify(0)
        else:
            self.heap.pop()
        
        # Mark the minimum element as not in the heap
        self.pos[min_id] = len(self.heap)
        
        return min_id
    
    def decrease_key(self, id, new_key):
        """
        Sets the key of the element with id to new_key if its current key is greater than new_key.
        """
        if not self.in_heap(id) or self.keys[id] <= new_key:
            return
        
        self.keys[id] = new_key
        idx = self.pos[id]
        self.heap[idx] = (new_key, id)
        self._bubble_up(idx)
    
    def _heapify(self, i):
        """Maintain the heap property starting from position i."""
        smallest = i
        left = 2 * i + 1
        right = 2 * i + 2
        heap_size = len(self.heap)
        
        if left < heap_size and self.heap[left][0] < self.heap[smallest][0]:
            smallest = left
        
        if right < heap_size and self.heap[right][0] < self.heap[smallest][0]:
            smallest = right
        
        if smallest != i:
            # Swap elements
            self.heap[i], self.heap[smallest] = self.heap[smallest], self.heap[i]
            self.pos[self.heap[i][1]] = i
            self.pos[self.heap[smallest][1]] = smallest
            self._heapify(smallest)
    
    def _bubble_up(self, i):
        """Bubble up an element to maintain the heap property."""
        parent = (i - 1) // 2
        
        if i > 0 and self.heap[i][0] < self.heap[parent][0]:
            # Swap elements
            self.heap[i], self.heap[parent] = self.heap[parent], self.heap[i]
            self.pos[self.heap[i][1]] = i
            self.pos[self.heap[parent][1]] = parent
            self._bubble_up(parent)


def parse_input_file(filename):
    """
    Parse the input file to create an adjacency list representation of the graph.
    """
    with open(filename, 'r') as f:
        lines = f.readlines()
    
    n = int(lines[0].strip())
    
    # Initialize the adjacency list
    adj_list = [[] for _ in range(n + 1)]
    
    # Parse each edge
    for i in range(1, len(lines)):
        if not lines[i].strip():
            continue
        edge = lines[i].split()
        u, v, w = int(edge[0]), int(edge[1]), int(edge[2])
        adj_list[u].append((v, w))
    
    return n, adj_list


def dijkstra(n, adj_list, source):
    """
    Implement Dijkstra's algorithm to find the shortest paths from source.
    """
    # Initialize distances with infinity
    dist = [float('inf')] * (n + 1)
    dist[source] = 0
    
    # Initialize parent array for reconstructing the shortest path tree
    parent = [0] * (n + 1)
    
    # Initialize the heap
    keys = [float('inf')] * (n + 1)
    keys[0] = float('inf')  # keys[0] contains the value for infinity
    keys[source] = 0
    
    heap = Heap(keys, n)
    
    # Shortest path tree edges
    spt_edges = []
    
    while not heap.is_empty():
        u = heap.min_id()
        heap.delete_min()
        
        if u != source:
            spt_edges.append((parent[u], u, dist[u]))
        
        for v, w in adj_list[u]:
            if heap.in_heap(v) and dist[u] + w < dist[v]:
                dist[v] = dist[u] + w
                parent[v] = u
                heap.decrease_key(v, dist[v])
    
    return spt_edges


def main():
    """
    Main function to solve the problem.
    """
    if len(sys.argv) != 2:
        print("Usage: python3 asn3.py <input_file>")
        sys.exit(1)
        
    # Parse the input file
    input_file = sys.argv[1]
    n, adj_list = parse_input_file(input_file)
    
    # Print the adjacency list representation
    print("Adjacency List Representation:")
    for i in range(1, n + 1):
        print(f"Vertex {i}:", end=" ")
        for v, w in adj_list[i]:
            print(f"({i}, {v}, {w})", end=" ")
        print()
    
    # Run Dijkstra's algorithm
    source = 1
    spt_edges = dijkstra(n, adj_list, source)
    
    # Print the shortest path tree edges
    print("\nShortest Path Tree Edges:")
    for u, v, w in spt_edges:
        print(f"({u}, {v}) : {w}")


if __name__ == "__main__":
    main()
