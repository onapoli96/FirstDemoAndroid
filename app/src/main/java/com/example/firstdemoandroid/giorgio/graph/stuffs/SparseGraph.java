package com.example.firstdemoandroid.giorgio.graph.stuffs;

import java.util.ArrayList;

/**
 * the class that implements the interface Graph
 *  
 * @author Giorgio
 *
 * @param <V> generic type to represent the vertex of the graph
 * @param <E> generic type to represent the information about the edges of the graph
 */
public class SparseGraph<V,E> implements Graph<V,E>{
	private ArrayList<V> nodes;
	private ArrayList<Edge<V,E>> archi;
	int n;
	int m;
	
	/**
	 * constructor that inizialize:
	 *  - ArrayList of vertices (nodes)
	 *  - l'HashMap of the edges
	 *  - the vertex number to 0
	 *  - the vertices number to 0
	 */
	public SparseGraph(){
		nodes = new ArrayList<V>();
		archi = new ArrayList<Edge<V,E>>();
		n = 0;
		m = 0;
	}
	
	/**
	 * add a vertex v to the graph
	 * the method add the V element to the ArrayList, create a list of neighbors using the HashMap and increment the number of vertex in the graph.
	 * @param vertex vertex to add in the graph
	 * @return boolean to check if the insert was successful or not 
	 */
	@Override
	public boolean addVertex(V vertex) {
		if(!nodes.contains(vertex)){
			nodes.add(vertex);
			n++;
			ArrayList<Edge<V,E>> neighbors = new ArrayList<Edge<V,E>>();
			return true;
		}
		return false;
	}

	/**
	 * add an oriented edge.
	 * the method check if the two vertices are present in the graph
	 * if they are not present the method add them and the relative edge
	 * add him to the neighbors of v1
	 * @param v1 the source vertex
	 * @param v2 the vertex adjacent to v1
	 * @param info information about the edge, it can be null
	 * @return true if the edge is successful added, false if not
	 */
	@Override
	public boolean addEdge(V v1, V v2, E info) {
		if(!nodes.contains(v1)) addVertex(v1);
		if(!nodes.contains(v2)) addVertex(v2);
		Edge<V,E> a = new Edge<V,E>(info,v1,v2);
		archi.add(a);

		m++;
		return true;
	}

	/**
	 * add an oriented edge with relative weight.
	 * the method check if the two vertices are present in the graph
	 * if they are not present the method add them and the relative edge
	 * add him to the neighbors of v1
	 * @param v1 the source vertex
	 * @param v2 the vertex adjacent to v1
	 * @param weight the weight of the edge
	 * @param info information about the edge, it can be null
	 * @return true if the edge is successful added, false if not
	 */
	@Override 
	public boolean addEdge(V v1, V v2, double weight, E info) {
		if(v1 == null || v2 == null ) throw new IllegalArgumentException("il nodo non pu� essere null");
		if(nodes.contains(v1)){
			if(!nodes.contains(v1)) addVertex(v1);
			if(!nodes.contains(v2)) addVertex(v2);
			Edge<V,E> a = new Edge<V, E>(info,v1,v2,weight);
			archi.add(a);
			m++;
			return true;
		}
		return false;
	}
	
	/**
	 * add a NOT oriented edge.
	 * the method create two edges: v1->v2 and v2->v1
	 * use the method addEdge
	 * the edge will be added to the adjacent list of v1 and v2
	 * @param v1 vertex 
	 * @param v2 vertex adjacent to v1
	 * @param info information about the edge, it can be null
	 * @return boolean, true if the edge is successful added in both the adjacent list, false if they are present yet.
	 */
	@Override
	public boolean addUndirectedEdge(V v1, V v2, E info) {
		addEdge(v1,v2,info);
		addEdge(v2,v1,info);
		return true;
	}


	/**
	 * add a NOT oriented edge with relative weight.
	 * the method create two edges: v1->v2 and v2->v1
	 * use the method addEdge
	 * the edge will be added to the adjacent list of v1 and v2
	 * @param v1 vertex 
	 * @param v2 vertex adjacent to v1
	 * @param weight the weight of the edge
	 * @param info information about the edge, it can be null
	 * @return boolean, true if the edge is successful added in both the adjacent list, false if they are present yet.
	 */
	@Override
	public boolean addUndirectedEdge(V v1, V v2, double weight, E info) {
		addEdge(v1,v2,weight,info);
		addEdge(v2,v1,weight,info);
		return true;
	}
	/**
	 *check if the vertex is present in the graph
	 * @param vertex to check
	 * @return boolean true if it is present not otherwise
	 */
	@Override
	public boolean hasVertex(V vertex) {
		if(nodes.contains(vertex))
			return true;
		return false;
	}
/**
 * check if the Edge v1 -> v2 is present
 * the method check for first if the vertices are both present, than check in the adjacent list if they are both present.
 * @param v1 source node
 * @param v2 destination node
 * @return boolean, true if the Edge v1 -> v2 is in the graph, otherwise
 */
	@Override
	public boolean hasEdge(V v1, V v2) {
		if(nodes.contains(v1)){
			if(nodes.contains(v2)){
				for(Edge<V,E> arco: archi){
					if(arco.getIn().equals(v1) && arco.getOut().equals(v2))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * return the weight of an Edge source -> dest
	 * in case of an Edge without weight it returns 1
	 * @param source source node of the edge
	 * @param dest destination node of the edge
	 * @return weight of the edge
	 */
	@Override
	public double getWeight(V source, V dest) {
		return getEdge(source,dest).getWeight();
	}

	/**
	 * return the list of vertices
	 * @return ArrayList of all the inserted vertices
	 */
	@Override
	public ArrayList<V> vertices() {
		return nodes;
	}

	/**
	 * return the list of the adjacent nodes of a selected one.
	 * @param vertex selected vertex
	 * @return the list of the adjacent nodes of the vertex, null if he havent .
	 */
	@Override
	public ArrayList<V> neighbors(V vertex) {
		if(nodes.contains(vertex)){
			ArrayList<V> neighbors = new ArrayList<V>();

			for(Edge<V,E> edge: archi)
			{
				if (edge.getIn().equals(vertex))
				{
					neighbors.add(edge.getOut());
				}
				/*else if(edge.getOut().equals(vertex)){
					neighbors.add(edge.getIn());
				}*/
			}
			return neighbors;
		}
		return null;
	}
	
	/**
	 * return the Edge source -> dest if it is present in the graph.
	 * the method search into the edges list of the node sourde if exist an edge with source as source and dest as destination
	 * @param source source node of the edge source->dest
	 * @param dest destination of the Edge source -> dest
	 * @return true if the is present Edge source --> dest, null otherwise
	 */
	public Edge<V,E> getEdge(V source, V dest){
		Edge<V,E> find = new Edge<V,E>(source,dest);
		for(Edge<V,E> a : archi){
			if((a.getOut().equals(find.getOut()))&&(a.getIn().equals(find.getIn())))
				return a;
		}
		return null;
	}
	/**
	 * print the list of nodes in the console
	 */
	public void printVertex(){
		for(V i : nodes)
			System.out.println(i);
	}



	public ArrayList<Edge<V,E>> getAllEdges(){
		return archi;
	}


}
