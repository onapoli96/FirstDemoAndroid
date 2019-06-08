package com.example.firstdemoandroid.giorgio.graph.test;

import com.example.firstdemoandroid.giorgio.graph.algorithms.visit.GraphVisitImplements;
import com.example.firstdemoandroid.giorgio.graph.stuffs.SparseGraph;
import com.example.firstdemoandroid.giorgio.graph.stuffs.VertexAnalyserImplements;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException{
		SparseGraph<Integer,String> grafo = new SparseGraph<Integer,String>();
		//System.out.println("vertici del grafo");
		grafo.addVertex(4);
		grafo.addVertex(8);
		grafo.addVertex(11);
		grafo.addVertex(3);
		grafo.addVertex(5);
		grafo.addVertex(9);
		
		//grafo.printVertex();
		//System.out.println("archi del grafo");
		grafo.addEdge(4, 8, "4->8");
		grafo.addEdge(4, 11,"4->11");
		grafo.addEdge(8, 3, "8->3");
		grafo.addEdge(3, 9, "3->9");
		grafo.addEdge(11, 9, "11->9");
		

		//grafo.printNeighbors(3);
		//grafo.printNeighbors(5);
		//System.out.println("numero nodi: "+grafo.n);
		//System.out.println("numero arch: "+grafo.m);
		
		GraphVisitImplements<Integer, String> gv = new GraphVisitImplements<Integer, String>();
		VertexAnalyserImplements<Integer> va = new VertexAnalyserImplements<Integer>();
		System.out.println("visita in profondit�");
		gv.depthFirst(grafo, 4, va);
		System.out.println("visita in ampiezza");
		gv.breadthFirst(grafo, 4, va);
		
	}
}
