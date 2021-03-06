
package implementation;

import java.util.*;


	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph() {
		this.dataMap = new HashMap<String, E>();
		this.adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
	}

	public void addVertex(String vertexName, E data) {
		if (adjacencyMap.containsKey(vertexName)) {
			throw new IllegalArgumentException("Vertex Already Exists");
		} else {
			dataMap.put(vertexName, data);
			HashMap<String, Integer> temp = new HashMap<String, Integer>();
			adjacencyMap.put(vertexName, temp);
		}
	}

	public void addDirectedEdge(String startVertexName, String endVertexName, int cost) {
		if (!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("Vertex don't exist");
		} else if (adjacencyMap.containsKey(startVertexName)) {
			HashMap<String, Integer> temp = adjacencyMap.get(startVertexName);
			temp.put(endVertexName, cost);
			adjacencyMap.replace(startVertexName, temp);
		}
	}

	public String toString() {
		Set<String> verteces = adjacencyMap.keySet();
		Set<String> allVerteces = new TreeSet<String>();
		allVerteces.addAll(verteces);
		ArrayList<String> vertex = new ArrayList<String>(allVerteces);
		String answer = "Vertices: [";
		for (int i = 0; i < vertex.size(); i++) {
			if (i < vertex.size() - 1) {
				answer += vertex.get(i) + ", ";
			} else {
				answer += vertex.get(i);
			}
		}

		answer += "]" + "\n";

		answer += "Edges:" + "\n";

		for (int i = 0; i < vertex.size(); i++) {
			if (i < vertex.size() - 1) {
				answer += "Vertex(" + vertex.get(i) + ")";
				answer += "--->" + adjacencyMap.get(vertex.get(i)).toString() + "\n";
			} else {
				answer += "Vertex(" + vertex.get(i) + ")";
				answer += "--->" + adjacencyMap.get(vertex.get(i)).toString();

			}
		}

		return answer;

	}

	public Map<String, Integer> getAdjacentVerticies(String vertexName) {
		return adjacencyMap.get(vertexName);
	}

	public int getCost(String startVertexName, String endVertexName) {
		if (adjacencyMap.containsKey(startVertexName) && adjacencyMap.containsKey(endVertexName)) {
			return adjacencyMap.get(startVertexName).get(endVertexName);
		} else {
			throw new IllegalArgumentException("Invalid Parameters");
		}
	}

	public E getData(String vertex) {
		if (adjacencyMap.containsKey(vertex)) {
			return dataMap.get(vertex);
		} else {
			throw new IllegalArgumentException("Invalid Parameters");
		}
	}

	public void doDepthFirstSearch(String VertexName, CallBack<E> callback) {
		if (adjacencyMap.containsKey(VertexName)) {
			ArrayList<String> visited = new ArrayList<String>();
			Stack<String> discovered = new Stack<String>();
			discovered.push(VertexName);

			while (!discovered.isEmpty()) {
				String temp = discovered.pop();

				if (!visited.contains(temp)) {
					visited.add(temp);
					callback.processVertex(temp, dataMap.get(temp));

					for (String temp2 : adjacencyMap.get(temp).keySet()) {
						if (!visited.contains(temp2)) {
							discovered.push(temp2);
						}
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid parameter");
		}

	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		if (adjacencyMap.containsKey(startVertexName) && adjacencyMap.containsKey(endVertexName)) {
			HashMap<String, Integer> distanceCost = new HashMap<String, Integer>();
			ArrayList<String> S = new ArrayList<String>();
			HashMap<String, String> predecessor = new HashMap<String, String>();
			Queue<String> discovered = new LinkedList<String>();

			discovered.add(startVertexName);

			for (String temp : adjacencyMap.keySet()) {
				distanceCost.put(temp, Integer.MAX_VALUE);
			}

			for (String temp : adjacencyMap.keySet()) {
				predecessor.put(temp, "None");
			}

			distanceCost.put(startVertexName, 0);
			predecessor.put(startVertexName, startVertexName);

			int count = 0;

			/*for (String temp : adjacencyMap.keySet()) {
				if (adjacencyMap.get(temp).size() > 0) {
					count++;
				}
			}*/ 
			
			CallBackDepth<E> e = new CallBackDepth<E>();
			doDepthFirstSearch(startVertexName,e);
			

			while (S.size() != e.getSize()) {
				String temp = discovered.remove();
				String K = "";
				int distance = Integer.MAX_VALUE;
				for (String temp2 : distanceCost.keySet()) {
					if (!S.contains(temp2)) {
						if (distanceCost.get(temp2) < distance) {
							K = temp2;
							distance = distanceCost.get(temp2);
						}
					}
				}

				S.add(K);

				for (String temp2 : adjacencyMap.get(K).keySet()) {
					if (!S.contains(temp2)) {
						if ((distanceCost.get(K) + adjacencyMap.get(K).get(temp2)) < distanceCost.get(temp2)) {
							distanceCost.put(temp2, distanceCost.get(K) + adjacencyMap.get(K).get(temp2));
							predecessor.put(temp2, K);
						}

						discovered.add(temp2);
					}
				}

			}

			if (distanceCost.get(endVertexName) == Integer.MAX_VALUE) {
				shortestPath.add("None");
				return -1;
			} else {
				String curr = endVertexName;
				shortestPath.add(curr);
				while (predecessor.get(curr) != startVertexName) {
					curr = predecessor.get(curr);
					shortestPath.add(curr);
				}

				if (endVertexName != startVertexName) {
					shortestPath.add(predecessor.get(curr));
				}

				Collections.reverse(shortestPath);
				return distanceCost.get(endVertexName);
			}

		} else {
			throw new IllegalArgumentException("Invalid parameters");
		}
	}
	
	

	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callBack) {
		if (adjacencyMap.containsKey(startVertexName)) {
			ArrayList<String> visited = new ArrayList<String>();
			Queue<String> discovered = new LinkedList<String>();
			discovered.add(startVertexName);

			while (!discovered.isEmpty()) {
				String temp = discovered.remove();

				if (!visited.contains(temp)) {
					visited.add(temp);
					callBack.processVertex(temp, dataMap.get(temp));

					for (String temp2 : adjacencyMap.get(temp).keySet()) {

						if (!visited.contains(temp2)) {
							discovered.add(temp2);
						}

					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid parameter");
		}

	}

	public Set<String> getVertices() {
		Set<String> temp = adjacencyMap.keySet();
		return temp;
	}
}
