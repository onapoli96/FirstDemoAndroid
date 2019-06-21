package com.example.firstdemoandroid.giorgio.graph.algorithms.search;

import com.example.firstdemoandroid.giorgio.graph.priorityqueue.HeapPriorityQueue;
import com.example.firstdemoandroid.giorgio.graph.stuffs.Graph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.SparseGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Dijkstra Algorithm
 * class wich implement the min path function in a graph
 * @author Giorgio
 * @param <V> generic type to represent the vertex of the graph
 * @param <E> generic type to represent the information about the edges of the graph
 */
public class MinPathDijkstra<V,E> {
	HeapPriorityQueue<V> q;
	HashMap<String,Double> dist;
	HashMap<String, V> vertici;
	HashMap<String,V> fathers;
	ArrayList<Object> path;
	
	/**
	 * Dijkstra to find a min path from a source node s to a destination node d
	 * @param g graph in input
	 * @param s source node
	 * @param d destination node
	 * @return sequence of nodes that form the min path from s to d
	 */
	public ArrayList<V> minPath(Graph<V,E> g, V s, V d){

		ArrayList<V> vertex = g.vertices();

		q = new HeapPriorityQueue<>(g.vertices().size());
		dist = new HashMap<>();
		fathers = new HashMap<>();
		ArrayList<V> minPath = new ArrayList<>();
		

		for(V x:vertex){
			dist.put(x.toString(),Double.POSITIVE_INFINITY);
			fathers.put(x.toString(),null);
		}
		fathers.put(s.toString(), s);
		dist.put(s.toString(), 0.0);
		/*for(V x:vertex){
			q.insert(x, dist.get(x.toString()));
		}

		q.printHeap();
		int i = 0;
		while(!q.isEmpty()){
			V u = q.extractfirst();
			for(V v:g.neighbors(u)){

				double weight = g.getWeight(u, v);
				double distanceThrought = dist.get(u.toString())+weight;

				if(distanceThrought<dist.get(v.toString())){
					dist.put(v.toString(), distanceThrought);
					fathers.put(v.toString(), u);

					q.decreasePriority(v, dist.get(v.toString()));

				}
			}
			i++;
			System.out.println("iterazione numero "+i);
			q.printHeap();
		}*/
		//implementazione mia
		for(V x:vertex){
			q.insert(x, dist.get(x.toString()));

		}

		int i = 0;
		ArrayList<String> nodiVisitati = new ArrayList<>();

		while(nodiVisitati.size()<vertex.size()){
			V u = q.extractfirst();
			nodiVisitati.add(u.toString());
			for(V v:g.neighbors(u)){

					double weight = g.getWeight(u, v);
					double distanceThrought = dist.get(u.toString()) + weight;

					if (distanceThrought < dist.get(v.toString())) {
						dist.put(v.toString(), distanceThrought);
						q.decreasePriority(v, dist.get(v.toString()));
						System.out.println("Qui ci arrivo");
						fathers.put(v.toString(), u);

					}

			}
			i++;
			System.out.println("iterazione numero "+i);

		}

		//fine implementazione mia




		System.out.println("Queste sono tutte le distanza alla fine del for"+dist);
		V step;
		step = d;
		System.out.println("Questo è l'has dei padriS"+fathers);
		if(fathers.get(d.toString())==null){
			System.out.println("IO rompo tutto  "+"   " +d.toString() +"    "+fathers.containsKey(d.toString()));
			System.out.println("Questi son i vicini del colpevole"+ g.neighbors(d));
			return null;
		}
		minPath.add(step);
		while(fathers.get(step.toString())!=null){
			if(step.toString().equals(s.toString())) break;
			step = fathers.get(step.toString());
			minPath.add(step);
			System.out.println(step);
		}

		Collections.reverse(minPath);
		System.out.println("Questo è il min path: " +minPath);
		return minPath;
	}

	/**
	 * Dijkstra to find a min path from a source node s to the others nodes of an input graph
	 * @param g graph in input
	 * @param s source node
	 * @return sequence of nodes that form the min path from d to s
	 */
	public Graph<V,E> minPath(Graph<V,E> g,V s){
		q = new HeapPriorityQueue<V>(g.vertices().size());
		dist = new HashMap<String, Double>();
		fathers = new HashMap<String, V>();
		path = new ArrayList<Object>();
		Graph<V,E> minPath = new SparseGraph<V,E>();
		
		ArrayList<V> vertex = g.vertices();
		for(V x:vertex){
			dist.put(x.toString(),Double.POSITIVE_INFINITY);
			fathers.put(x.toString(),null);
		}
		fathers.put(s.toString(), s);
		dist.put(s.toString(), 0.0);
		for(V x:vertex){
			q.insert(x, dist.get(x));
		}

		while(!q.isEmpty()){
			V u = q.extractfirst();
			for(V v:g.neighbors(u)){
				double weight = g.getWeight(u, v);
					double distanceThrought = dist.get(u)+weight;//distanza del padre + la sua distanza
					if(distanceThrought<dist.get(v)){//se il costo � inferiore al nodo attuale
						dist.put(v.toString(), distanceThrought);
						if(!(minPath.vertices().contains(v))) minPath.addVertex(v);
						if(!(minPath.vertices().contains(u))) minPath.addVertex(u);
						E info = null;
						minPath.addEdge(v, u,info);
						fathers.put(v.toString(), u);
						q.decreasePriority(v, dist.get(v));
					}
			}
		}	
		return minPath;
	}
	
	public ArrayList<V> nodiPozzo(Graph<V,E> g){
		ArrayList<V> nodiPozzo = new ArrayList<V>();
		for(V v:g.vertices()){
			if(g.neighbors(v)==null) nodiPozzo.add(v);
		}
		return nodiPozzo;
	}
}

