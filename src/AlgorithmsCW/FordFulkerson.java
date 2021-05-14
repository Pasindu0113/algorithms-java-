package AlgorithmsCW;

import java.util.*;

public class FordFulkerson {
    public int[] parent; //initialising the necessary variables
    public Queue<Integer> queue;
    public int numberOfVertices;
    public boolean[] visited;

    //initialising values to the variables created parameterised constructor
    public FordFulkerson(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        this.queue = new LinkedList<>();
        parent = new int[numberOfVertices];
        visited = new boolean[numberOfVertices];
    }

    public boolean breadthFirstSearch(int source, int goal, int[][] graph) { //implementing the breadth first search algorithm (passing the source, goal and the graph to breadth first search method)
        boolean pathFound = false;
        int destination, element;

        for(int vertex = 0; vertex < numberOfVertices; vertex++)//finding the source of the graph
        {
            parent[vertex] = -1;
            visited[vertex] = false;
        }

        queue.add(source);
        parent[source] = -1;
        visited[source] = true;

        while (!queue.isEmpty())
        {
            element = queue.remove();
            destination = 1;
//            System.out.println(element);

            while (destination < numberOfVertices) //loop through all the vertices in the graph
            {
                //checking if the graph has a considerable edge and weather the nodes are visited
                if (graph[element][destination] > 0 &&  !visited[destination]) //only one side will be considered since it is a directed graph
                {
                    parent[destination] = element; //replacing the value of element with the destination index of the array
                    queue.add(destination); //adding the destination to the queue
                    visited[destination] = true; //marking the visited nodes as true
//                    System.out.println(Arrays.toString(parent));
//                    System.out.println(queue);
//                    System.out.println(Arrays.toString(visited));
                }
                destination++;
            }
        }
        if(visited[goal]) { //if the target node is reached it is considered as a path
            pathFound = true;
        }
        return pathFound; //return the path found by the breadth first search algorithm to the ford fulkerson algorithm to continue
    }

    public int fordFulkerson(int[][] graph, int source, int destination) //implementing the ford fulkerson algorithm by passing the source graph and the target node
    {
        int u, v; //start and end nodes of a particular edge
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[numberOfVertices][numberOfVertices]; //getting the residual graphs after a flow passes through
        for (int sourceVertex = 0; sourceVertex < numberOfVertices; sourceVertex++)
        {
            for (int destinationVertex = 0; destinationVertex < numberOfVertices; destinationVertex++)
            {
                residualGraph[sourceVertex][destinationVertex] = graph[sourceVertex][destinationVertex];
            }

        }

        while (breadthFirstSearch(source ,destination, residualGraph)) //going through the augmented path returned from the breadth first algorithm
        {
            System.out.println("-----------------------------------");
            System.out.println(Arrays.deepToString(residualGraph));
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v]){
                System.out.println(Arrays.toString(parent));
                u = parent[v];
                System.out.println(u);
                System.out.println(v);
                pathFlow = Math.min(pathFlow, residualGraph[u][v]); //determining the min value of the pathFlow(s to t path flow)
                System.out.println(pathFlow);
            }
            for (v = destination; v != source; v = parent[v]){
                u = parent[v];
//                System.out.println(Arrays.toString(parent));
//                System.out.println(u);
//                System.out.println(v);
//                System.out.println(pathFlow);
                residualGraph[u][v] -= pathFlow;
//                System.out.println(Arrays.deepToString(residualGraph));
                residualGraph[v][u] += pathFlow;
//                System.out.println(Arrays.deepToString(residualGraph));
            }

            System.out.printf("Current path flow is %d AND current Max flow is %d%n", pathFlow,maxFlow);
            System.out.println("-----------------------------------");
            System.out.println(Arrays.deepToString(residualGraph));
            maxFlow += pathFlow; //adding the path flow to the max flow to get the final output
        }

        return maxFlow; // return the max flow of the graph
    }
}
