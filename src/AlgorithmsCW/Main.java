package AlgorithmsCW;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner myReader; //initialising the required variables
    public static int[][] graph;
    public static int numberOfNodes;
    public static int source;
    public static int sink;
    public static int maxFlow;
    public static int a;
    public static int b;

    public static void main(String[] args) throws IOException {
        final Files filesBenchmark = new Files(); //calling the files class to get the benchmark files

        while (true) {
            Scanner sc = new Scanner(System.in).useDelimiter("\n"); //user options
            System.out.println("\n1. Get file from the passed Args");
            System.out.println("2. Get file from the benchmark files");
            System.out.println("3. Get the customised input from the 'input.txt' file");
            System.out.println("Enter 'x' to exit\n");
            System.out.print("Enter your request : ");
            String userRequest = sc.nextLine();

            if (userRequest.equals("1")) {
                myReader = new Scanner(new File(args[0])); //getting a scanner input from a file and pass the file as a run argument
                getFileInfo();

            } else if (userRequest.equals("2")) {
                int selectedNo, actualNo;
                int i = 1;

                System.out.println("Benchmark Files\n");
                for (String name : filesBenchmark.files) { //loop through the benchmark files
                    if (i < 10) {
                        System.out.println(" " + i + ". " + name);
                    } else {
                        System.out.println(i + ". " + name);
                    }
                    i++;
                }

                System.out.print("\nChoose a file from the list: ");
                selectedNo = sc.nextInt(); //getting the user input
                actualNo = selectedNo - 1; //getting the actual index of the array
                String file = filesBenchmark.files.get(actualNo); //check the specified index

                myReader = new Scanner(new File(file));
                getFileInfo();
            } else if (userRequest.equals("3")) {
                myReader = new Scanner(new File("input.txt"));
                getFileInfo();
            } else if (userRequest.equalsIgnoreCase("x")) {
                System.out.println("Programme Successfully Terminated");
                break;
            } else {
                System.out.print("Invalid input, Reenter: ");
            }

        }
    }

    private static void getFileInfo() {

        assert false;
        numberOfNodes = myReader.nextInt(); //get the number of vertices from the file and pass it to a variable
        graph = new int[numberOfNodes][numberOfNodes]; //add the number of vertices to the graph and create a 2d array
        System.out.println("\nNo. of Vertices : " + numberOfNodes); //print the number of vertices

        for (int sourceVertex = 0; sourceVertex < numberOfNodes; sourceVertex++) {
            for (int destinationVertex = 0; destinationVertex < numberOfNodes; destinationVertex++) {
                graph[sourceVertex][destinationVertex] = 0;
            }
        }

        while (myReader.hasNext()) {
            a = myReader.nextInt(); //getting the from node of the edge from the file
            b = myReader.nextInt(); //getting the to node of the edge from the file
            int c = myReader.nextInt(); //getting the capacity of each edge and assigning it to it
            graph[a][b] = c;
            System.out.println("From : " + a + " | To : " + b + " | Capacity : " + c + ""); //print out the edges of the graph
        }

        source = 0; //initialising the source of the graph
        sink = numberOfNodes - 1; //initialising the target of the graph

//        long start = System.currentTimeMillis();
        FordFulkerson fordFulkerson = new FordFulkerson(numberOfNodes);

        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);

        System.out.println("Max Flow is : " + maxFlow + "\n");
//        long now = System.currentTimeMillis();
//        double elapsed = (now - start) / 1000.0;
//        System.out.println("\nElapsed time = " + elapsed + " seconds");
        printGraph();
        myReader.close();
    }

    public static void printGraph() {
        System.out.println("Adjacency Matrix : ");
        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Adjacency List : ");
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.print("Vertex " + i + " -> ");
            for (int j = 0; j <numberOfNodes ; j++) {
                if(graph[i][j] > 0){
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }


}
