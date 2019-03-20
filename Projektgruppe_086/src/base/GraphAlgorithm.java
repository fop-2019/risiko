package base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Abstrakte generische Klasse um Wege zwischen Knoten in einem Graph zu finden.
 * Eine implementierende Klasse ist beispielsweise {@link game.map.PathFinding}
 * @param <T> Die Datenstruktur des Graphen
 */
public abstract class GraphAlgorithm<T> {

    /**
     * Innere Klasse um {@link Node} zu erweitern, aber nicht zu verändern
     * Sie weist jedem Knoten einen Wert und einen Vorgängerknoten zu.
     * @param <T>
     */
    private static class AlgorithmNode<T> {

        private Node<T> node;
        private double value;
        private AlgorithmNode<T> previous;

        AlgorithmNode(Node<T> parentNode, AlgorithmNode<T> previousNode, double value) {
            this.node = parentNode;
            this.previous = previousNode;
            this.value = value;
        }
    }

    private Graph<T> graph;

    // Diese Liste enthält alle Knoten, die noch nicht abgearbeitet wurden
    private List<Node<T>> availableNodes;

    // Diese Map enthält alle Zuordnungen
    private Map<Node<T>, AlgorithmNode<T>> algorithmNodes;

    /**
     * Erzeugt ein neues GraphAlgorithm-Objekt mit dem dazugehörigen Graphen und dem Startknoten.
     * @param graph der zu betrachtende Graph
     * @param sourceNode der Startknoten
     */
    public GraphAlgorithm(Graph<T> graph, Node<T> sourceNode) {
        this.graph = graph;
        this.availableNodes = new LinkedList<>(graph.getNodes());
        this.algorithmNodes = new HashMap<>();

        for(Node<T> node : graph.getNodes())
            this.algorithmNodes.put(node, new AlgorithmNode<>(node, null, -1));

        this.algorithmNodes.get(sourceNode).value = 0;
    }

    private AlgorithmNode<T> getSmallestNode() {
    if (availableNodes.size() == 0 || availableNodes == null) return null;
    
    Node<T> currentLow = null;
    
    for (Node<T> node : availableNodes) {
    	if (currentLow == null) {
    		currentLow = node;
    	} else {
    		if (algorithmNodes.get(currentLow).value >= algorithmNodes.get(node).value && algorithmNodes.get(node).value >= 0 && algorithmNodes.get(currentLow).value >= 0) {
    			currentLow = node;
    		} 
    	}
    }
    for (Node<T> node : availableNodes) {
    	if (algorithmNodes.get(currentLow).value == algorithmNodes.get(node).value) {
    		availableNodes.remove(node);
    		return algorithmNodes.get(node);
    	}
    }
    	
    return null;
}


public void run() {
   	while (availableNodes.size()!= 0) {
    	AlgorithmNode<T> smallest = this.getSmallestNode();
    	List<Edge<T>> edges = graph.getEdges();
    	for (Edge<T> edge:edges) {
    		if (!(edge.contains(smallest.node)) || (edge.contains(smallest.node) && this.isPassable(edge) == false)) {
    			edges.remove(edge);
    		}
    	}
    	
    	for (Edge<T> edge:edges) {
    		double a = this.getValue(edge) + smallest.value;
    		if (algorithmNodes.get(edge.getOtherNode(smallest.node)).value == -1 || a < algorithmNodes.get(edge.getOtherNode(smallest.node)).value) {
    			algorithmNodes.get(edge.getOtherNode(smallest.node)).value = a;
    			algorithmNodes.get(edge.getOtherNode(smallest.node)).previous = smallest;
    		} else {
    			algorithmNodes.get(edge.getOtherNode(smallest.node)).value = a;
    		}
    	}
    }
}

public List<Edge<T>> getPath(Node<T> destination) {
    List<Edge<T>> path = new LinkedList<Edge<T>>();
    
    Node<T> prev = destination;
    Node<T> p = algorithmNodes.get(prev).previous.node;
    while (algorithmNodes.get(prev).previous!=null) {
    	path.add(graph.getEdge(prev, p));
    	Node<T> tmp = p;
    	p=algorithmNodes.get(prev).previous.node;
    	prev = tmp;
    }
    
    return path;
}

    /**
     * Gibt den betrachteten Graphen zurück
     * @return der zu betrachtende Graph
     */
    protected Graph<T> getGraph() {
        return this.graph;
    }

    /**
     * Gibt den Wert einer Kante zurück.
     * Diese Methode ist abstrakt und wird in den implementierenden Klassen definiert um eigene Kriterien für Werte zu ermöglichen.
     * @param edge Eine Kante
     * @return Ein Wert, der der Kante zugewiesen wird
     */
    protected abstract double getValue(Edge<T> edge);

    /**
     * Gibt an, ob eine Kante passierbar ist.
     * @param edge Eine Kante
     * @return true, wenn die Kante passierbar ist.
     */
    protected abstract boolean isPassable(Edge<T> edge);

    /**
     * Gibt an, ob eine Knoten passierbar ist.
     * @param node Eine Knoten
     * @return true, wenn der Knoten passierbar ist.
    */
    protected abstract boolean isPassable(Node<T> node);
}
