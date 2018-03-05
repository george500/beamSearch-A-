package thema1 ;

import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.Map ;

//converts a csv file to kml
public class TaxiApp {

	static int maxMetwpo ;

	public static void main(String[] args) {

		//maxMetwpo = 100 ;
		maxMetwpo = Integer.parseInt(args[0]) ;
		
		System.out.println("max Metwpo anazitisis: " + maxMetwpo) ;
		if(maxMetwpo == 0) {
			System.out.println("Cannot run A* with metwpo anazitisis 0.") ;
			return ;
		}

		Node client = null ;
		//Node taxi = null ;
		Map<Node, String> taxis = null ;		//holds Node taxi and taxiID
		Graph graph = null ;

		//READING FILES
		CSVReader reader = new CSVReader() ;
		client = reader.readClient() ;
		if (client == null) {
			System.out.println("Client file reading failed") ;
			return ;
		}
		taxis = reader.readTaxis() ;
		if (taxis == null) {
			System.out.println("Taxi file reading failed") ;
			return ;
		}
		graph = reader.readNodes() ;
		if (graph == null) {
			System.out.println("Node file reading failed") ;
			return ;
		}

		//STARING MAIN PROGRAM
		client = graph.put(client) ;		//put client in the graph

		Iterator taxiIter = taxis.entrySet().iterator() ;
		
		double minDist = Math.pow(10,10) ;		//to xrisimopoiw gia megalyteri apodosi, anti na kanw minPath.getDistance() klp
		PathInfo newPath, minPath = null ;
		ArrayList<PathInfo> routes = new ArrayList<PathInfo>() ;
		int taxisCanReach = 0 ;
		while (taxiIter.hasNext())                        //put all taxis in the graph
		{
			newPath = null ;
			Map.Entry<Node,String> pair = (Map.Entry)taxiIter.next() ;
			Node taxi = pair.getKey() ;						//epelekse ena taxi apo to mapping
			String id = pair.getValue() ;
			
			taxi = graph.put(taxi) ;                        //put the taxi in the graph
			//System.out.println("\t" + taxi.getXCoord() + ", " + taxi.getYCoord() + ", " + id) ;
			//System.out.println("\t" + client.getXCoord() + ", " + client.getYCoord()) ;
			//double newDist = graph.printPath(taxi,client) ;   //gia kathe taxi tupwnoume tin diadromi kai kratame tin apostasi
			
			System.out.println("Taxi " + id) ;
			newPath = graph.findPath(taxi,client) ;   //gia kathe taxi kratame tin diadromi kai tin apostasi
			
			//gia na vroume to kalytero
			//System.out.println() ;
			if (newPath == null) {
				System.out.println("\tTaxi " + id + " couldn't reach destination") ;
			}
			else {
				//System.out.println("\tRoute distance: " + newPath.getDistance()) ;
				taxisCanReach++ ;
				newPath.setTaxiID(id) ;
				routes.add(newPath) ;
				if (newPath.getDistance() < minDist) {
					minDist = newPath.getDistance() ;
					minPath = newPath ;
				}
			}
		}
		String[] taxiName = null ;
		String[] color = null ;
		String[][] coor = null ;
		if (minDist != Math.pow(10,10)) {
			//System.out.println("Closest taxi is " + minPath.getTaxiID()) ;
			//System.out.println("Max metwpo anazitisis is " + max) ;
			for (PathInfo route : routes) {
				if (route == minPath) {		//i optimal diadromi
					route.setOptimal(true) ;
				}
			}
			int taxisNo = taxisCanReach ;
			taxiName = new String[taxisNo] ;
			color = new String[taxisNo] ;
			coor = new String[taxisNo][] ;			//now create String[] color, String[][] coor
			int pathCoorNo ;
			int i = 0, j ;
			for (PathInfo route : routes) {
				taxiName[i] = route.getTaxiID() ;
				pathCoorNo = route.getPath().size() ;
				coor[i] = new String[pathCoorNo] ;
				j = 0 ;
				for (Node point : route.getPath()) {
					//System.out.println("Taxi " + route.getTaxiID() + " route: " + Double.toString(point.getXCoord()) + "," + Double.toString(point.getYCoord()));
					coor[i][j] = Double.toString(point.getXCoord()) + "," + Double.toString(point.getYCoord()) + ",0" ;
					j++ ;
				}
				if (route == minPath) {
					color[i] = "green" ;
				}
				else
					color[i] = "red" ;
				i++ ;
			}
			
		}
		/*else {
			System.out.println("No taxi  could reach destination,\nretry with bigger metwpo anazitisis.") ;
		}*/
		/*
		for (int i = 0 ; i < taxiName.length ; i++) {
			System.out.println(coor[i].length) ;
		}*/
		//WRITING TO FILES
		KMLWriter kml = new KMLWriter() ;
		kml.createKML(taxiName, color, coor) ;
	}

}
