package com.example.firstdemoandroid.giorgio.graph.stuffs;

import java.util.ArrayList;

/**
 * interface to create the graph
 * 
 * @author Giorgio
 *
 * @param <V> generic type to represent the vertex of the graph
 * @param <E> generic type to represent the information about the edges of the graph
 */
public interface Graph<V, E> {
	
	/**
	 * to add a new vertex into the graph
	 * @param vertex vertex to insert
	 * @return true if it is correctly inserted, else false (vertex present).
	 */
	boolean addVertex(V vertex);
	
	/**
	 * add the edge v1->v2 if it is not present in the graph
	 * @param v1 source vertex
	 * @param v2 destination vertex
	 * @param info information about the edge
	 * @return true if it is correctly inserted, else false (edge present).
	 */
	boolean addEdge(V v1, V v2, E info);
	
	/**
	 *add the edge v1->v2 if it is not present in the graph (with weight)
	 * @param v1 source vertex
	 * @param v2 destination vertex
	 * @param weight weight of the edge
	 * @param info information about the edge
	 * @return true if it is correctly inserted, else false (edge present).
	 */
	boolean addEdge(V v1, V v2, double weight, E info);
	
	/**
	 *add the undirected edge v1->v2 if it is not present in the graph 
	 * @param v1 source vertex
	 * @param v2 destination vertex
	 * @param info information about the edge
	 * @return true if it is correctly inserted, else false (edge present).
	 */
	boolean addUndirectedEdge(V v1, V v2, E info);
	
	/**
	 **add the undirected edge v1->v2 if it is not present in the graph  (with edge)
	 * @param v1 source vertex
	 * @param v2 destination vertex
	 * @param weight weight of the edge
	 * @param info information about the edge
	 * @return true if it is correctly inserted, else false (edge present).
	 */
	boolean addUndirectedEdge(V v1, V v2, double weight, E info);
	
	/**
	 * check if the vertex is in the graph
	 * @param vertex vertex to check
	 * @return true if is in the graph, false otherwise
	 */
	boolean hasVertex(V vertex);
	
	/**
	 * check if the edge is in the graph
	 * @param v1 vertex to check
	 * @param v2 vertex to check
	 * @return true if is in the graph, false otherwise
	 */
	boolean hasEdge(V v1, V v2);
	
	/**
	 * return the weight of the edge source->dest
	 * @param source source of the edge
	 * @param dest destination of the edge
	 * @return return the weight of the edge source->dest
	 */
	double getWeight(V source, V dest);
	
	/**
	 * return the vertices of the graph stored in an ArrayList
	 * @return ArrayList of vertices
	 */
	ArrayList<V> vertices();

	ArrayList<Edge<V,E>> getAllEdges();
	
	/**
	 * return the adjacent vertices of a vertex
	 * 
	 * @param vertex
	 * @return ArrayList with the adjacent vertices
	 */
	ArrayList<V> neighbors(V vertex);
	

}