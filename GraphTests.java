package tests.student;

import org.junit.Test;

import base.*;

public class GraphTests {

	
	
	
	@Test
	public void testGetAllValues () {
		Graph graph1 = new Graph();
		
		
		
		
		graph1.addEdge(graph1.addNode(1), graph1.addNode(2));
		graph1.addEdge(graph1.addNode(3), graph1.addNode(4));
		graph1.addEdge(graph1.addNode(5), graph1.addNode(6));
		graph1.addEdge(graph1.addNode(7), graph1.addNode(8));
		
		
		System.out.println(graph1.getAllValues());
		System.out.println(graph1.getEdges());
		System.out.println(graph1.getNode(3).getValue());
		
		
		
	}
}
