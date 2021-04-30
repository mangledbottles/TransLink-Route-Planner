import java.io.*;
import java.util.*;
import java.text.*;

public class SearchArrivalTime {
	
	public static void main(String[] args) {
		System.out.println("Enter arrival time (hh:mm:ss)");
		Scanner input = new Scanner( System.in );
		String item = input.next();
		getlist(item);
	}

    public static final String MAX_TIME = "24:00:00";
	
	public static ArrayList<String> parseFile(String input){
		
        ArrayList<String> trips = new ArrayList<String>();
        ArrayList<String> tripIDs = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		try{
			Date maxTime = sdf.parse(MAX_TIME);
			BufferedReader br = new BufferedReader(new FileReader("inputs/stop_times.txt"));
			br.readLine();
			String line;
	        while ((line = br.readLine()) != null) {
	        	if (!line.isEmpty()) {
	            	String[] temp = line.split(",");
	            	Date time = sdf.parse(temp[1]);
	            	if (time.getTime() < maxTime.getTime()) {
	            		trips.add(line);
	            	}
	        	}
            }
            br.close();
        } catch (Exception e){
            System.out.print(e);
        }
        
        for (int i = 0; i<trips.size(); i++) {
			String line = trips.get(i);
			String[] t = line.split(",");
			try {
				Date time = sdf.parse(t[1]);
				Date inputTime = sdf.parse(input);
				if (time.getTime() == inputTime.getTime()) {
					tripIDs.add(line);
				}
			} catch (Exception e){
	            System.out.print(e);
	        }
		}
        //tripIDs.sort(Comparator.comparing(Double::parseDouble));
        return tripIDs;
	}
		
	public static void getlist(String searchItem){
		ArrayList<String> list = parseFile(searchItem);
		if (!list.isEmpty()) {
			System.out.println(list.size() + " trips match your search:");
			for(int i=0; i < list.size(); i++){
	            System.out.println( list.get(i) );
	        }
		}
		else {
			System.out.println("There are no trips that match your search.");
		}
	}
	
}
