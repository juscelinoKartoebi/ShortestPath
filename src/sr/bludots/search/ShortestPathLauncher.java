package sr.bludots.search;

	import java.util.Scanner;

	class ShortestPathLauncher {
	    public static void main(String[] args) {
	        Graph theGraph = new Graph();
	        theGraph.addVertex("Paramaribo");         // 0
	        theGraph.addVertex("Port of Spain");      // 1
	        theGraph.addVertex("Caracas");            // 2
	        theGraph.addVertex("Oranjestad");         // 3
	        theGraph.addVertex("Kingston");           // 4
	        theGraph.addVertex("San Juan");           // 5
	        theGraph.addVertex("Saint John");       // 6
	        theGraph.addVertex("Bridgetown");         //7

	        theGraph.addEdge(0, 1, 400);
	        theGraph.addEdge(0, 7, 700);
	        theGraph.addEdge(1, 2, 500);
	        theGraph.addEdge(1, 7, 200);
	        theGraph.addEdge(2, 3, 200);
	        theGraph.addEdge(3, 4, 400);
	        theGraph.addEdge(3, 5, 600);
	        theGraph.addEdge(5, 4, 700);
	        theGraph.addEdge(6, 5, 200);
	        theGraph.addEdge(7, 6, 400);
	        theGraph.addEdge(7, 3, 300);
	        theGraph.addEdge(7, 2, 500);

	        Scanner scan = new Scanner(System.in);
	        System.out.println("Enter current location:");
	        int userGiven = scan.nextInt();
	        System.out.println("The shortest routes are: ");

	        theGraph.findShortestPath(userGiven);

	        System.out.println("");
	        scan.close();
	    }
	}

