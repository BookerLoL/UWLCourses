/*
 * UndirectedGraph.java
 * 
 * Implementation of an undirected graph being able to count triangles
 * 
 * Author: Ethan Booker
 * 
 * The UndirectedGraph class is a representation of an undirected graph
 * that can create count the number of valid triangles.
 */

/**
 * Given imports
 */
import java.util.*;
/**
 * Given imports
 */
import java.io.*;
/**
 * A repsentation of an undirected graph that can count triangles
 * 
 * @author Ethan Booker
 * @since 2/8/2018
 * @version 1.1
 */
public class UndirectedGraph {
    /**
     * Represents the vertex nodes in the undirected graph
     */
    private class Vertex {
        /**
         * EdgeNode list of verticies with greater values 
         */
        private EdgeNode edges1;
        
        /**
         * EdgeNode list of verticies with lesser values 
         */
        private EdgeNode edges2;
        
        /**
         * Number of verticies the vertex has an edge with 
         */
        private int numNeighbors; //This is the same as the degree of the vertex
        
        /**
         * Ordered list in ascending order of the neighbors 
         */
        private int orderedNeighbors[]; //Set of ordered neighbors
        //We will learn others ways to store sets but for this assignment
        //you must store the set in an array
        
        /**
         * Number of values that aren't neighbors in the orderedNeighbors
         * Note: additional class variable 
         */
        private int orderNeighborNotValues; // size  
        
        /**
         * Vertex Constructor 
         */
        private Vertex() {
            edges1 = null;
            edges2 = null;
            numNeighbors = 0; 
            orderedNeighbors = null;
        }
    }
    
    /**
     * Represents list of edges between 2 verticies 
     */
    private class EdgeNode {
        /**
         * Lower value vertex value
         */
        private int vertex1;
        
        /**
         * Higher value vertex value
         */
        private int vertex2;
        
        /**
         * Reference to next EdgeNode of greater vertex values
         */
        private EdgeNode next1;
        
        /**
         * Reference to next EdgeNode of lower vertex values
         */
        private EdgeNode next2;
        
        /**
         * EdgeNode constructor 
         * 
         * @param int Lower vertex value
         * @param int Higher vertex value
         * @param EdgeNode reference to next greater value EdgeNode 
         * @param EdgeNode reference to next lesser value EdgeNode 
         */
        private EdgeNode(int v1, int v2, EdgeNode e1, EdgeNode e2) {
         //PRE: v1 < v2
         //Each edge node is stored only once but it will be part of two lists
         vertex1 = v1; 
         vertex2 = v2;
         next1 = e1;
         next2 = e2; 
        }
    }
    
    /**
     * UndirectedGraph's array of verticies 
     */
    private Vertex[] g;
 
    /**
     * UndirectedGraph constructor 
     * 
     * @param int The number of verticies that will be put into the graph
     */
    public UndirectedGraph(int size) {
        //Create a graph with size vertices
        //The vertices will be identified by ints between 0 and size-1
        //The vertices are stored in a array
        g = new Vertex[size];
        for (int i = 0; i < size; i++) {
            g[i] = new Vertex();
        }
    } 
    
    /**
     * Adds edge onto UndirectedGraph
     * Note: List for both edges are ascending
     * 
     * @param int  specific vertex 
     * @param int specific vertex
     */
    public void addEdge(int v1, int v2) {
        //add a new edge between v1 and v2
        
        //Ensures v1 < v2, swaps values if v1 > v2
        if(v1 > v2) {
            int temp = v2;
            v2 = v1; 
            v1 = temp;
        } 
        
        //Increment number of neighbors
        g[v1].numNeighbors++;
        g[v2].numNeighbors++;

        //Adds EdgeNode to v1 in ascending order 
        //List is empty or vertex goes before head of list 
        if(g[v1].edges1 == null || g[v1].edges1.vertex2 > v2) {
            g[v1].edges1 = new EdgeNode(v1, v2, g[v1].edges1, null);
        } else {
            //In the middle or end of the list
            EdgeNode temp = g[v1].edges1;
            while(temp.next1 != null && temp.next1.vertex2 < v2) 
            {
                temp = temp.next1;
            }
            temp.next1 = new EdgeNode(v1, v2, temp.next1, null);
        }   
        
        //Adds EdgeNode to v2 
        addEdge2(v1, v2);
    }
    
    /**
     * Helper method to add neighbor onto edge2 list in ascending order 
     * 
     * @param int specific vertex
     * @param int specific vertex
     */
    private void addEdge2(int v1, int v2) { 
        //Adds node v2 in ascending order
        //List is empty or vertex goes before head of list 
        if(g[v2].edges2 == null || g[v2].edges2.vertex1 > v1) {
            g[v2].edges2 = new EdgeNode(v1, v2, null, g[v2].edges2);
        } else {
            //In the middle or end of the list
            EdgeNode temp = g[v2].edges2;
            while(temp.next2 != null && temp.next2.vertex1 < v1) 
            {
                temp = temp.next2;
            }
            temp.next2 = new EdgeNode(v1, v2, null, temp.next2);
        }
    }

    /**
     * Helps to show if graph is being correctly built 
     */
    public void printNeighbors() {
        //print the neighbors of each vertex to standard output
        //this will be useful for checking if you built the graph correctly
        //there should be one line for each vertex
        //the format of the line should be vertex name: comma delimited list of
        //neighbors
        
        //Iterates through all the verticies
        for(int i = 0; i < g.length; i++) {
            Vertex temp = g[i];
            EdgeNode edg1 = temp.edges1;
            EdgeNode edg2 = temp.edges2;
            System.out.print(i + ":");
            
            //Checks lower valued edges
            while(edg2 != null) {
                if(edg2.next2 != null) {
                    System.out.print(edg2.vertex1 + ",");
                }
                else {
                    if(edg2.next2 == null && edg1 != null) {
                        System.out.print(edg2.vertex1 + ",");
                    }
                    else {
                        System.out.print(edg2.vertex1);
                    }
                }
                edg2 = edg2.next2;
            }
            
            //Checks higher valued edges
            while(edg1 != null) {
                if(edg1.next1 != null) {
                    System.out.print(edg1.vertex2 + ",");
                }
                else {
                    System.out.print(edg1.vertex2);
                }
                edg1 = edg1.next1;
            }
            
            System.out.println();
        }
    }
    
    /**
     * Helper method to correctly insert neighbors in correct neighbor array position
     */
    private void populateOrderedNeighbors() {
        //find the ordered neighbors of each vertex
        //each orderedNeighbor set should be sorted in ascending order
        
        //Create neighbor arrays 
        initializeNeighborArrays();
        
        //Iterate through all the vertices
        for(int i = 0; i < g.length; i++) {
            EdgeNode tempE = g[i].edges1; 
            //Iterate through all edges in the vertices 
            while(tempE != null) {
                int v1 = tempE.vertex1;
                int v2 = tempE.vertex2;
                
                //Checks where to add vertex into correct neighbor array 
                if( (g[v1].numNeighbors < g[v2].numNeighbors) || (g[v1].numNeighbors == g[v2].numNeighbors && v1 < v2) ) {
                    g[v1].orderedNeighbors[g[v1].numNeighbors - g[v1].orderNeighborNotValues] = v2;
                    g[v1].orderNeighborNotValues--;
                } else {
                    g[v2].orderedNeighbors[g[v2].numNeighbors - g[v2].orderNeighborNotValues]   = v1;
                    g[v2].orderNeighborNotValues--;
                }
                
                tempE = tempE.next1;
            }
        }
    }
    
    /**
     * Helper method to fill neighbor arrays to appropriate size and default values 
     */
    private void initializeNeighborArrays() {
        //Create arrays with correct neighbor size
        for(int i = 0; i < g.length; i++) {
            int length = g[i].numNeighbors;
            g[i].orderedNeighbors = new int[length];
            g[i].orderNeighborNotValues = length;
            
            //Fill arrays with values of -1 as a stopping case 
            for(int k = 0; k < length; k++) {
                g[i].orderedNeighbors[k] = -1;
            }
        }
    }
    
    /**
     * Helper method to find intersection between two neighbor arrays
     * Note: Lists are already in order 
     * 
     * @param int vertex1
     * @param int vertex2
     */
    private int intersectionSize(int v1, int v2) {
        //find the number of elements in the intersection of the orderedNeighbors
        //of v1 and the orderedNeighbors of v2
        //your code must make use of the fact that the orderNeighbor sets are
        //sorted in ascending order
        
        int intersec = 0;
        int v1Length = g[v1].orderedNeighbors.length;
        int v2Length = g[v2].orderedNeighbors.length;
        
        int pos = 0;
        int pos2 = 0; 

        //Iterate through orderedneighbors taking advantage of ascended order 
        while( (pos < v1Length) && (g[v1].orderedNeighbors[pos] != -1) &&  (g[v2].orderedNeighbors[pos2] != -1) ) {
            if(g[v1].orderedNeighbors[pos] == g[v2].orderedNeighbors[pos2]) {
                intersec++;
                pos++;
                pos2++;
            } else if(g[v1].orderedNeighbors[pos] < g[v2].orderedNeighbors[pos2]) {
                pos++;
            } else {
                pos2++;
            }
        }
        
        return intersec;
    }
    
    /**
     * Finds all the triangles in the graph 
     * 
     * @int number of triangles in graph
     */
    public int countTriangles() {
        //return the number of triangles in the graph
        
        //Orderedneighbors must be populated 
        populateOrderedNeighbors();
        
        int triCount = 0; 
        
        //Iterates through all the vertices in the graph
        for(int v1 = 0; v1 < g.length; v1++) {
            //Iterates through all the vertices in the neighbor array
            for(int v2 : g[v1].orderedNeighbors) {
                //Avoids looking at default -1 values
                if(v2 == -1) {
                    break;
                }
                
                triCount += intersectionSize(v1, v2);
            }
        }
        return triCount; 
    }
    
    /**
     * Helper method to ensure better input results 
     *  
     * @param String file name 
     */
    private static void testUndirectedGraph(String fileName) {
        try {
            Scanner in = new Scanner(new File(fileName));
            UndirectedGraph g = new UndirectedGraph(Integer.parseInt(in.nextLine()));
            
            while(in.hasNextLine()) {
                String[] edges = in.nextLine().split(" ");
                g.addEdge(Integer.parseInt(edges[0]), Integer.parseInt(edges[1]));
            }
            //g.printNeighbors();
            in.close();
            System.out.println("The number of triangles is " + g.countTriangles());
        }
        catch(IOException ex) {
            System.out.println("Input file is not correct format!");
        } catch (Exception ex) {
            System.out.println("Unexpected error happened!");
        }
    }
    
    public static void main(String args[]) throws IOException {
        if(args.length != 1) {
            System.out.println("Error: you must provide 1 command line arguemnt: name of text file");
        } else {
            testUndirectedGraph(args[0]);
        }
    }
 }
