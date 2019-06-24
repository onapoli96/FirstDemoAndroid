package com.example.firstdemoandroid.giorgio.Helper;

/*
 * @author Giorgio
 * 
 * 
 * @param <V> generic type to define the graphos vertex
 * @param <E> information about the edge
 * 
 */
public class Edge <V,E> {

	private V in;
	private V out;
	private E info;
	private double weight;

	/**
	 * to create an edge with the two vertex
	 * @param in source vertex of the edge
	 * @param out destination vertex of the edge 
	 */
	public Edge(V in, V out){
		this.info=null;
		this.in = in;
		this.out = out;
		this.weight=1;
	}

	/**
	 * to create an edge with: information about itself, the two vertex.
	 * the weight is equal to 1
	 * @param info information about the edge
	 * @param in source vertex of the edge
	 * @param out destination vertex of the edge 
	 */
	public Edge(E info,V in, V out){
		this.info = info;
		this.in = in;
		this.out = out;
		this.weight=1;
	}

	/**
	 * to create an edge with: information about itself, the two vertex and the weight.
	 * @param info information about the edge
	 * @param in source vertex of the edge
	 * @param out destination vertex of the edge 
	 * @param weight weight of the edge
	 */
	public Edge(E info,V in, V out,double weight){
		this.info = info;
		this.in = in;
		this.out = out;
		this.weight = weight;
	}


	@Override
	public String toString() {
		return "In: " +this.in + "Out:" +this.out;
	}

	double getWeight(){
		return weight;
	}

	public V getIn() {
		return in;
	}

	public void setIn(V in) {
		this.in = in;
	}

	public V getOut() {
		return out;
	}

	public void setOut(V out) {
		this.out = out;
	}

	public E getInfo() {
		return info;
	}

	public void setInfo(E info) {
		this.info = info;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
