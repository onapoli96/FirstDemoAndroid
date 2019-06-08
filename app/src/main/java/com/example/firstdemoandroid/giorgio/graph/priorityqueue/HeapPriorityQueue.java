package com.example.firstdemoandroid.giorgio.graph.priorityqueue;

import java.util.HashMap;

/**
 * Heap
 * index:
 * 	-Father: (i-1)/2
 * 	-Left child: (2*i)+1
 * 	-Right child: (2*1)+2
 * */

public class HeapPriorityQueue<V> implements PriorityQueue<V> {
	
	private class ElemConPrior<V>{
		V element;
		double priority;
		ElemConPrior(V el,double p){
			element=el;
			priority=p;
		}
	}
	
	ElemConPrior<V>[] heap;
	int lastIndex;
	HashMap<V,Integer> position;
	
	/**
	 * Heap constructor
	 * heap : array of elemConPrio, length n+1;
	 * Last index: last element position, max prio, initialized to 0.
	 * position : hash table that wich associate a string to an integer,
	 *@param n : length of queue -1
	 */
	public HeapPriorityQueue(int n){
		heap = new ElemConPrior[n+1];
		lastIndex = 0;
		position = new HashMap<V,Integer>(); 
	}
	
	/**
	 * function that move an element to its right position
	 * @param i : index of the heaps element to move up
	 * */
	private void moveUp(int i){
		if(i >= lastIndex) throw new IllegalArgumentException();
		ElemConPrior<V> ep = heap[i];
		while(i>0){
			if(ep.priority>=heap[(i-1)/2].priority)
				break;
			heap[i] = heap[(i-1)/2];
			position.put(heap[i].element, i);
			i=(i-1)/2;
		}
		heap[i]=ep;
		position.put(heap[i].element,i);
	}
	
	private void moveDown(int i){
		if(i > lastIndex) throw new IllegalArgumentException();
			ElemConPrior<V> el = heap[i];
			int j;
			while((j=(2*i)+1)<lastIndex){
				if(j+1<lastIndex && heap[j+1].priority<heap[j].priority) 
					j++;
				if(el.priority<=heap[j].priority) break;
				heap[i]=heap[j];
				i=j;
			}
			heap[i]=el;
			position.put(heap[i].element, i);
	}
	
	@Override
	public boolean isEmpty() {
		if(lastIndex==0)return true;
		return false;
	}

	@Override
	public void insert(V element, double priority) {
		if(lastIndex==heap.length) rialloca();
		
		if(!position.containsKey(element)){	
			heap[lastIndex] = new ElemConPrior<V>(element,priority);
			position.put(element, lastIndex);
			lastIndex++;
			moveUp(lastIndex-1);
			
		}
	}

	public void rialloca(){
		ElemConPrior[] nuovo = new ElemConPrior[(heap.length*2)+1]; 
		for(int i = 0;i<heap.length;i++)
			nuovo[i]=heap[i];
		heap = nuovo;
	}
	
	@Override
	public V extractfirst() {
		if(lastIndex==0) throw new IllegalArgumentException();
		V first = heap[0].element;
		position.put(null, lastIndex);
		ElemConPrior<V> last = heap[lastIndex-1];
		heap[lastIndex--] = null;
		if(lastIndex>=0){
			heap[0]=last;
			moveDown(0);
		}
		position.remove(first);
		return first;
	}

	@Override
	public V getFirst() {
		return heap[0].element;
	}

	@Override
	public boolean decreasePriority(V element, double newPriority) {
		if(position.get(element)!=null){
			int i = position.get(element);
			decreasePriority(i, newPriority);
		}return false;
	}
	private boolean decreasePriority(int i, double newPriority){
		if(i>lastIndex) throw new IllegalArgumentException();
		if(heap[i].priority>newPriority){
			heap[i].priority = newPriority;
			moveDown(i);
			moveUp(i);
			return true;
		} return false;
	}

	@Override
	public void remove(V element) {
		if(lastIndex<=0) throw new IllegalArgumentException();
		int i= position.get(element);
		heap[i] = heap[lastIndex-1];
		lastIndex=lastIndex-1;
		moveDown(i);
	}
	
	public void printHeap(){
		for(int i = 0;i<lastIndex;i++){
			position.toString();
			System.out.println("Elemento: "+heap[i].element+" , Priority: "+heap[i].priority+" hash index: "+position.get(heap[i].element));
		}
	}
	public void printHash(){
		for(int i = 0;i<lastIndex;i++)
			System.out.println("hash: "+position.get(heap[i].element)+"element: "+heap[position.get(heap[i].element)].element);
	}
	
	public void printInTree(){
		for(int i = 0; i<lastIndex;i++){
			System.out.println("Father: "+heap[(i-1)/2].element);
			System.out.println("Left child "+heap[(2*i)+1].element+" of: "+heap[(i-1)/2].element);
			System.out.println("Right child: "+heap[(2*i)+2].element+" of: "+heap[(i-1)/2].element);
		}
	}
}
