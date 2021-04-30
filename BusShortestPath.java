import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class BusShortestPath {
	private String stop_times;
	private String transfers;
	private double Matrix[][] = new double[15000][15000];
	
	

	BusShortestPath(String stop_times, String transfers) {
		this.stop_times = stop_times;
		this.transfers = transfers;
		

		try {
			makeMatrix();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void makeMatrix() throws FileNotFoundException {
		
		for(int i = 0; i < Matrix.length; i++) {
			for(int j = 0; j < Matrix[i].length; j++) {
				if(i != j) {
					Matrix[i][j] = Double.NaN;
				}
				else {
					Matrix[i][j] = 0;
				}
			}
		}
		//read in stop times file 
		
		File stopTimesFile = new File(stop_times);
		Scanner fileScan = new Scanner(stopTimesFile);
		Scanner lineScan = null;
		fileScan.nextLine();
		
		int from = 0;
		int to = 0;
		int prevRouteId = 0; 
		int routeId = 0;
		
		//use different weights based on which file you're using 
		double weight;
		String currentLine;
		
		
		weight = 1;
		while(fileScan.hasNextLine()) {
			currentLine = fileScan.nextLine();
			lineScan = new Scanner(currentLine);
			lineScan.useDelimiter(",");
			
			prevRouteId = routeId;
			routeId = lineScan.nextInt();
		
////			lineScan.next();
////			lineScan.next();
			
			from = to;
			to = lineScan.nextInt();
			if(prevRouteId == routeId) {
				Matrix[from][to] = weight;
			}
			lineScan.close();
		}
		fileScan.close();
		
		int transferType; 
		double minTime;
		double divisor = 100;
		
		try {
			File transfersFile = new File(transfers);
			fileScan = new Scanner(transfersFile);
			
			fileScan.nextLine();
		}
		 catch(Exception error) {
	            System.out.println("Its broken.. : " + error.toString());
	        }
		
//		File transfersFile = new File(transfers);
		
		
		
		while(fileScan.hasNextLine()) {
			currentLine = fileScan.nextLine();
			fileScan = new Scanner(currentLine);
			fileScan.useDelimiter(",");
			
			from = fileScan.nextInt();
			to = fileScan.nextInt();
			transferType = fileScan.nextInt();
			
			if(transferType == 0) {
				Matrix[from][to] = 2;
			}
			else if(transferType == 2) {
				minTime = fileScan.nextDouble();
				Matrix[from][to] = minTime / divisor;
			}
			lineScan.close();
		}
		fileScan.close();
	
	}
	
	
    private void relaxEdge(int from, int to, double[] distTo, int[] edgeTo) {
    	if(distTo[to] > distTo[from] + Matrix[from][to]) {
    		distTo[to] = distTo[from] + Matrix[from][to];
    		edgeTo[to] = from;
    	}
    }
	
	public String findShortDistance(int from, int to){
		
		if(from == to) {
			return "" + Matrix[from][to] + " through " + from;
		}
		
		int visited[] = new int[Matrix.length];
    	double distTo[] = new double[Matrix.length];
    	int edgeTo[] = new int[Matrix.length];
    	
    	//set all but starting node to infinity
    	for(int i = 0; i < distTo.length; i++) {
    		if(i != from)
    		{
    			distTo[i] = Double.POSITIVE_INFINITY;
    		}
    	}
    	
    //Dijkstra's Algorithm
    	visited[from] = 1;
    	distTo[from] = 0; 
    	int currentNode = from;
    	int totalNodesVisited = 0;
    	while(totalNodesVisited < distTo.length)
    	{
    		//relax the edges pointing from the current node then set it as visited
    		for(int i = 0; i < Matrix[currentNode].length; i ++) {
    			if(!Double.isNaN(Matrix[currentNode][i]) && visited[i] == 0) {
        			relaxEdge(currentNode, i, distTo, edgeTo);
        		}
    		}
    		visited[currentNode] = 1;
    		
    		//pick node w shortest distance to relax
    		double shortestDist = Integer.MAX_VALUE;
    		for(int i = 0; i < distTo.length; i++) {
    			if(visited[i] != 1 && shortestDist > distTo[i]) {
    				currentNode = i;
    				shortestDist = distTo[i];
    			}
    		}
    		totalNodesVisited++;
    	}
    	
    	//build the path we took through the graph
    	if(distTo[to] == Double.POSITIVE_INFINITY) {
    		return "not existent";
    	}
    	
    	int u = from;
    	int v = to;
    	String trace = "";
    	while(v != u) {
    		trace =  "->" + edgeTo[v] + trace;
    		v = edgeTo[v];
    	}
    	trace = trace + "->" + to;
    	
    	return distTo[to] + " through " + trace;
    }
	

}
