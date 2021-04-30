import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BusShortestPath {
	private static final int LINES = 12479;
	private String syp_timesFilename, transfersFilename;
	private double matrix[][] = new double[LINES][LINES];

	BusShortestPath(String syp_timesFilename, String transfersFilename) {
		this.syp_timesFilename = syp_timesFilename;
		this.transfersFilename = transfersFilename;
		try {
			matrix();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String findShortDistance(int x, int y) {
		if(x == y) {
			return "" + matrix[x][y] + " through " + x;
		}
		int visited[] = new int[matrix.length];
    	double disty[] = new double[matrix.length];
    	int edgey[] = new int[matrix.length];
    	
    	for(int i = 0; i < disty.length; i++) {
    		if(i != x)
    		{
    			disty[i] = Double.POSITIVE_INFINITY;
    		}
    	}
    	
    	visited[x] = 1;
    	disty[x] = 0;
    	int currentNode = x;
    	int ytalNodesVisited = 0;
    	while(ytalNodesVisited < disty.length)
    	{
    		for(int i = 0; i < matrix[currentNode].length; i ++) {
    			if(!Double.isNaN(matrix[currentNode][i]) && visited[i] == 0) {
        			relaxEdge(currentNode, i, disty, edgey);
        		}
    		}
    		visited[currentNode] = 1;
    		double shortestDist = Integer.MAX_VALUE;
    		for(int i = 0; i < disty.length; i++) {
    			if(visited[i] != 1 && shortestDist > disty[i]) {
    				currentNode = i;
    				shortestDist = disty[i];
    			}
    		}
    		ytalNodesVisited++;
    	}
    	
    	if(disty[y] == Double.POSITIVE_INFINITY) {
    		return "This route does not exist.";
    	}
    	
    	int u = x;
    	int v = y;
    	String busRoute = "";
    	while(v != u) {
    		busRoute =  "--->" + edgey[v] + busRoute;
    		v = edgey[v];
    	}
    	busRoute = busRoute + "--->" + y;
    	return disty[y] + " through " + busRoute;
    }
	
	private void matrix() throws FileNotFoundException {
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[i].length; j++) {
				if(i != j) {
					matrix[i][j] = Double.NaN;
				}
				else {
					matrix[i][j] = 0;
				}
			}
		}
		
		File sypTimesFile = new File(syp_timesFilename);
		Scanner fileScanner = new Scanner(sypTimesFile);
		Scanner lineScanner = null;
		fileScanner.nextLine();
		int x = 0; 
		int y = 0;
		int previousRouteId = 0; 
		int routeId = 0;
		double weight;
		String currentLine;
		
		weight = 1;
		while(fileScanner.hasNextLine()) {
			currentLine = fileScanner.nextLine();
			lineScanner = new Scanner(currentLine);
			lineScanner.useDelimiter(",");
			
			previousRouteId = routeId;
			routeId = lineScanner.nextInt();
			
			lineScanner.next();
			lineScanner.next();
			
			x = y;
			y = lineScanner.nextInt();
			if(previousRouteId == routeId) matrix[x][y] = weight;
			lineScanner.close();
		}
		fileScanner.close();
		
		int transferType; 
		double minimumTransferTime;
		double transferType2Divisor = 100;
		File transfersFile = new File(transfersFilename);
		fileScanner = new Scanner(transfersFile);
		fileScanner.nextLine();
		
		while(fileScanner.hasNextLine()) {
			currentLine = fileScanner.nextLine();
			lineScanner = new Scanner(currentLine);
			lineScanner.useDelimiter(",");
			
			x = lineScanner.nextInt();
			y = lineScanner.nextInt();
			transferType = lineScanner.nextInt();
			
			if(transferType == 0) {
				matrix[x][y] = 2;
			}
			else if(transferType == 2) {
				minimumTransferTime = lineScanner.nextDouble();
				matrix[x][y] = minimumTransferTime / transferType2Divisor;
			}
			lineScanner.close();
		}
		fileScanner.close();
	}
	
    private void relaxEdge(int x, int y, double[] disty, int[] edgey) {
    	if(disty[y] > disty[x] + matrix[x][y]) {
    		disty[y] = disty[x] + matrix[x][y];
    		edgey[y] = x;
    	}
    }
}