/*
 * TopologicalSort.java
 * 
 * Implementation of a directed graph and tological ordering
 * 
 * Author: Ethan Booker
 * 
 * The TopologicalSort class is a representation of a directed 
 * graph that can determine whether it has a topoligical order.
 */
/**
 * Initially given import for the class implementation
 */
import java.io.*;
/**
 * Initially given import for the class implementation
 */
import java.util.*;
/**
 * A representation of a directed graph that can find topological order 
 * 
 * @author Ethan Booker
 * @since 1/29/2018
 * @version 1.0
 */
public class TopologicalSort {
    //Adjacency list representation of a directed graph
    //See the class discussion for the details of the representation.
    /**
     * Represents the Vertext nodes for the directed graph list of vertices 
     */
    private class VertexNode {
        /**
         * Vertex name
         */
        private String name;
        /**
         * Reference to next Vertex 
         */
        private VertexNode nextV;
        /**
         * Reference to current Node's edge list 
         */
        private EdgeNode edges;
        /**
         * Current vertex indegree
         */
        private int indegree;

        /**
         * Vertex constructor 
         * 
         * @param String name of the Vertex
         * @param Vertex reference to next Vertex node
         */
        private VertexNode(String n, VertexNode v) {
            name = n;
            nextV = v;
            edges = null;
            indegree = 0;
        }
    }
    
    /**
     * Represents the Edge nodes for the vertices' list of edges 
     */
    private class EdgeNode {
        /**
         * The current vertex node
         */
        private VertexNode vertex1;
        /** 
         * The edge vertex node
         */
        private VertexNode vertex2;
        /**
         * Reference to the current vertex next edge node
         */
        private EdgeNode nextE;
        
        /**
         * EdgeNode constructor 
         * 
         * @param Vertex current vertex node
         * @param Vertex edge vertex node 
         * @param EdgeNode reference to next edgenode 
         */
        private EdgeNode(VertexNode v1, VertexNode v2,EdgeNode e) {
            vertex1 = v1;
            vertex2 = v2;
            nextE = e;
        }
    }
    /**
     * Head of vertex list 
     */
    private VertexNode vertices; 
    /** 
     * Number of vertices in vertex list 
     */
    private int numVertices;
    
    /**
     * TopgologicalSort default constructor
     */
    public TopologicalSort() {
        vertices = null;
        numVertices = 0;
    }
    
    /**
     * Adds vertex into vertex list 
     * 
     * @param String the name of the vertex
     */
    public void addVertex(String s) {
     //PRE: the vertex list is sorted in ascending order using the name as the key
     
     //Checks if vertex list empty or needs to be inserted at the start of the list 
     if(numVertices == 0 || vertices.name.compareTo(s) > 0) {
         vertices = new VertexNode(s, vertices);
     } else {
         VertexNode tempV = vertices;
         
         //Iterate through vertex list for proper position to insert desired Vertex
         while(tempV.nextV != null && tempV.nextV.name.compareTo(s) <= 0) {
             tempV = tempV.nextV;
         }
         
         //Prevents from adding a vertex with the same name
         if(tempV.name.equals(s)) {
             return;
         } else { 
             tempV.nextV = new VertexNode(s, tempV.nextV);
         }
     }
     
     //Added a vertex
     numVertices++;
     
     //POST: a vertex with name s has been add to the vertex list and the vertex
     // list is sorted in ascending order using the name as the key
    } 
    
    /**
     * Adds an edge onto the current vertex 
     * 
     * @String n1 The outdegree vertex 
     * @String n2 The indegree vertex
     */
    public void addEdge(String n1, String n2) {
     //PRE: the vertices n1 and n2 have already been added
     
     VertexNode tempV = getVertex(n1);
     VertexNode tempV2 = getVertex(n2);
     
     //Add new edge into front of outdegree vertex edge list 
     tempV.edges = new EdgeNode(tempV, tempV2, tempV.edges);
     
     //Update indegree vertex 
     tempV2.indegree++;
     
     //POST:the new edge (n1, n2) has been added to the n1 edge list
    }
    
    /**
     *  Helper method to get desired Vertex
     *  
     *  Note: Should be used only when certain the vertex exists in list 
     *  @param String name of desired Vertex
     */
    private VertexNode getVertex(String n) {
        VertexNode tempV = vertices; 
        //Iterates through until found vertex with desired name or null 
        while(tempV != null && !n.equals(tempV.name)) {
            tempV = tempV.nextV;
        }
        
        return tempV;
    }
    
    /**
     *  Helper method to get desired Vertex
     *  
     *  Note: Only use when vertex position is valid
     *  @param String name of desired Vertex
     */
    private VertexNode getVertex(int pos) {
        //Just a safety check to prevent iterating past the list size
        if(pos > numVertices) {
            return null;
        }
        
        VertexNode tempV = vertices; 
        
        //Iterates to desired node position
        for(int i = 0; i < pos; i++) {
            tempV = tempV.nextV;
        }
        
        return tempV;
    }
    
    /**
     * Helper method to get desired Vertex position
     * 
     * Note: Use only if vertex exists 
     */
    private int getVertexPos(VertexNode n) {
        VertexNode temp = vertices;
        int pos = 0;
        
        //Iterate through list until Vertex node is found 
        while(temp != n) {
            pos++;
            temp = temp.nextV;
        }
        return pos;
    }
    
    
    /**
     * Finds a topological order of the graph if it exists
     * 
     * @return String the topological order or doesn't exist message
     */
    public String topoSort() {
     //if the graph contains a cycle return null
     //otherwise return a string containing the names of vertices separated by
     //blanks in a topological order.
     
     //Initialize variables
     String notExist = "No topological ordering exists for the graph";
     int[] indegree = new int[numVertices];
     Queue<VertexNode> queue = new LinkedList<>();
     
     calcIndegreesAndQueue(indegree, queue);
     
     //The topological order 
     String topo = ""; 
     int topoCount = 0;
     
     while(queue.size() != 0) {
         VertexNode vertex = queue.remove(); 
         topo += vertex.name + " "; 
         updateIndegrees(indegree, queue, vertex);
         topoCount++;
     }
     
     return topoCount == numVertices ? topo : notExist;
    }
    
    /**
     * Helper method to calculate indegrees and set initial queue
     * 
     * @param int[] To initialize all the current indegree values
     * @param Queue<VertexNode> all the VertexNode's with 0 indegrees
     */
    private void calcIndegreesAndQueue(int[] indegrees, Queue<VertexNode> x) {
     VertexNode temp = vertices;
     
     //Iterates through the Vertex nodes
     for(int i = 0; i < indegrees.length; i++) {
         //Assigns indegree values to array 
         indegrees[i] = temp.indegree;
         
         //Add to queue if vertex indegree is 0
         if(temp.indegree == 0) {
             x.add(temp);
         }
         
         temp = temp.nextV;
     }
    }
    
    /**
     * Helper method to update the Queue and indegrees 
     * 
     * 
     * @param int[] the current array of indegrees to be updated 
     * @param Queue<VertexNode> the Queue that should contain VertexNodes 
     * @param VertexNode  the removed vertex 
     */
    private void updateIndegrees(int[] indegrees, Queue<VertexNode> x, VertexNode removed) 
    {
        EdgeNode tempVertexEdges = removed.edges;
        int pos;
        
        //Iterate through removed vertex's edge list 
        while(tempVertexEdges != null) {
            //Gets correct position of the indegree node 
            pos = getVertexPos(tempVertexEdges.vertex2);
            
            //Decrement the indegree node 
            indegrees[pos]--;
            
            //Add to list if indegree node has 0 indegrees
            if(indegrees[pos] == 0) {
                x.add(getVertex(pos));
            }
            
            tempVertexEdges = tempVertexEdges.nextE;
        }
    }
    
    /*
    //Used for testing purposes
    public String toString() {
        VertexNode tempV = vertices;
        EdgeNode tempE = tempV.edges;
        while(tempV != null) {
            System.out.println();
            System.out.println("Vertex: " + tempV.name);
            
            while(tempE != null)
            {
                System.out.println("Edge: " + tempE.vertex1.name + "\t" + tempE.vertex2.name);
                System.out.println("Indegree of v1: " + tempE.vertex1.indegree + "Indegree of v2: " + tempE.vertex2.indegree);
                tempE = tempE.nextE;
            }
            
            
            if(tempV.nextV != null && tempV.nextV.edges!= null) {
                tempE = tempV.nextV.edges;
            }
            tempV = tempV.nextV;
        }
        return "";
    }
    */
    
    /**
     * Helper method to test the class methods
     */
    private static void TestTopologicalSort(String filename) { 
        try {
            File filetxt = new File(filename);
            Scanner scan = new Scanner(filetxt);
            TopologicalSort testTopo = new TopologicalSort();
            addVerticies(scan, testTopo);
            addEdges(scan, testTopo);
            scan.close();
            System.out.println(testTopo.topoSort());
        }
        catch(FileNotFoundException ex) {
            System.out.println("File was not found: " + filename);
        }
        catch(Exception ex) {
            System.out.println("Syntax in file is not correct, please fix it");
        }
    }
    
    /**
     * Helper method to test the class addVertices method
     */
    private static void addVerticies(Scanner scan, TopologicalSort topo) {
        try { 
            String firstLine = scan.nextLine();

            String input[] = firstLine.split(" ");
            for(int i = 0; i < input.length; i++) {
                topo.addVertex(input[i]);
            }
        } catch (Exception ex) {
            System.out.println("The first line is empty, need values for the Vertices");
        }
    }
    
    /**
     * Helper method to test the class addEdges method
     */
    private static void addEdges(Scanner scan, TopologicalSort topo) {
        String edges = "";
        try {
            while(scan.hasNextLine()) {
                edges = scan.nextLine();
                String edge[] = edges.split(" ");
                topo.addEdge(edge[0], edge[1]);
            }
        } catch (Exception ex) {
            System.out.println("The edges should only have 2 values per line seperated by spaces");
        }
    }
   
    public static void main(String args[]) throws IOException {
        if(args.length != 1) {
            System.out.println("Error: you must provide only 1 command line arguemnt: name of text file");
        } else {
            TestTopologicalSort(args[0]);
        }
        //see problem statement
    }
}
