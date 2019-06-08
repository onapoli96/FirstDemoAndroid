package com.example.firstdemoandroid.giorgio.graph.algorithms.visit;

import com.example.firstdemoandroid.giorgio.graph.stuffs.Graph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.VertexAnalyser;

/**
 * interfaccia per la visita di un grafo, sono rese implementabili due tipologie principali (quelle studiate all'interno del corso):
 *  - visita in ampiezza -> breadthFirst che visita il grafo per "livelli"
 *  - visita in profondit� -> depthFirst 
 *  
 * @author Giorgio
 * 
 * @param <V> generic type to represent the vertex of the graph
 * @param <E> generic type to represent the information about the edges of the graph
 */
public interface GraphVisit<V, E> {
	/**
	 * 
	 * @param graph graph to explore in amplitude
	 * @param s node to begin the visit
	 * @param va object that implement a possible visit of the graph
	 * @return return the graph visited in amplitude
	 */
	Graph<V,E> breadthFirst(Graph<V, E> graph, V s, VertexAnalyser<V> va);
	
	/**
	 * @param graph graph to explore in depth
	 * @param s node to begin the visit
	 * @param va object that implement a possible visit of the graph
	 * @return return the graph visited in depth
	 */
	Graph<V,E> depthFirst(Graph<V, E> graph, V s, VertexAnalyser<V> va);
}