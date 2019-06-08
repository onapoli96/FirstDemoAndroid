package com.example.firstdemoandroid.giorgio.graph.stuffs;

/**
 * This class implements the interface VertexAnalyser, that contain a method to analyze the vertex gived as parameter 
 * in this implementation the method print on console the vertex
 * @author Giorgio
 * @param <V> generic type to define the graphs vertex
 */
public class VertexAnalyserImplements<V> implements VertexAnalyser<V>{

	/**
	 * The method print in the console the vertex
	 * @param <V> generic type to define the graphos vertex
	 */
	@Override
	public void analyse(V vertex) {
		System.out.println(vertex);
	}

}
