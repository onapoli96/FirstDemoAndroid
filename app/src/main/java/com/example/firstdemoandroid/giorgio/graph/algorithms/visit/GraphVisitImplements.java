package com.example.firstdemoandroid.giorgio.graph.algorithms.visit;

import com.example.firstdemoandroid.giorgio.graph.stuffs.Graph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.VertexAnalyser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * classe implement the interface GraphVisit with the methods:
 * - visit in amplitude -> breadthFirst 
 * - visit in depth-> depthFirst 
 *  
 * @author Giorgio
 *
 * @param <V> generic type to represent the vertex of the graph
 * @param <E> generic type to represent the information about the edges of the graph
 */
public class GraphVisitImplements<V,E> implements GraphVisit<V,E> {

	Queue<V> F;
	HashMap<V,Boolean>visit;
	Stack<V> stack;
	ArrayList<V> parents;
	
	/**
	 * visit in amplitude
	 */
	public Graph<V, E> breadthFirst(Graph<V, E> graph, V s, VertexAnalyser<V> va) {///mancano i padri
		parents = new ArrayList<V>();
		ArrayList<V> vertex = graph.vertices();
		visit = new HashMap<V,Boolean>();	
		for(V n : vertex){
			visit.put(n, false);
		}
		F = new LinkedList<V>();
		F.clear();
		for(V v: vertex){
			if(!visit.get(v))bfs(graph,v,va);
		}
		return graph;
	}
	private void bfs(Graph<V, E> graph, V s, VertexAnalyser<V> va) {
		visit.put(s, true);
		F.offer(s);
		while(!F.isEmpty()){
			V u = F.poll();
			va.analyse(u);
			for(V adj:graph.neighbors(u)){
				if(!visit.get(adj)){
					F.add(adj);
					visit.put(adj, true);
				}
			}
		}
	}

	/**
	 * visit in depth
	 */
	@Override
	public Graph<V, E> depthFirst(Graph<V, E> graph, V s, VertexAnalyser<V> va) {
		ArrayList<V> vertex = graph.vertices();
		visit = new HashMap<V,Boolean>();	
		visit.clear();
		for(V n : vertex){
			visit.put(n, false);
		}
		stack = new Stack<V>();
		stack.clear();
		for(V v: vertex){
			if(!visit.get(v))dfs(graph,v,va);
		}
		return graph;
	}
	
	public void dfs(Graph<V, E> graph, V s, VertexAnalyser<V> va) {
		visit.put(s, true);
		stack.push(s);
		while(!stack.isEmpty()){
			V u = stack.pop();

			for(V adj:graph.neighbors(u)){
				if(!visit.get(adj)){
					visit.put(adj, true);
					stack.push(adj);
				}else{
					stack.remove(u);
				}
			}
			va.analyse(u);
		}
	}
}