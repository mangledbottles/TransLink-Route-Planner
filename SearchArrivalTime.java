import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.text.*;

public class SearchArrivalTime {
	
	public static final String MAX_TIME = "24:00:00";

	public static void main(String[] args) {
		System.out.println("Enter arrival time (hh:mm:ss)");
		Scanner input = new Scanner(System.in);
		String item = input.next();

		ArrayList<String> list = parseFile(item);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i));
			}
		} else {
			System.out.println("There are no trips that match your search.");
		}
	}

	public static ArrayList<String> parseFile(String input) {

		ArrayList<String> trips = new ArrayList<String>();
		ArrayList<String> searchedTrips = new ArrayList<String>();
		List<Double> id = new ArrayList<>(Arrays.asList());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


		try {
			String line;
			
			BufferedReader br = new BufferedReader(new FileReader("inputs/stop_times.txt"));
			br.readLine();
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
						trips.add(line);
				}
			}
			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		for (int i = 0; i < trips.size(); i++) {
			String line = trips.get(i);
			String[] t = line.split(",");

			try {
				Date maxTime = sdf.parse(MAX_TIME);
				Date time = sdf.parse(t[1]);
				double ids = Double.parseDouble(t[0]);
				

				String pattern = "[0-24]:[0-59]:[0-59]";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);
				if(!m.find()) { 
					System.out.println("Invalid date");
					break;
				} 

				Date inputTime = sdf.parse(input);
				if (time.getTime() == inputTime.getTime() && time.getTime() < maxTime.getTime()	) {
					try {
						id.add(ids);
						searchedTrips.add(line);
					} catch (NumberFormatException ex) { // handle your exception
						System.out.print("");
					}

				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		searchedTrips.sort(Comparator.naturalOrder());
		return searchedTrips;
	}

}
