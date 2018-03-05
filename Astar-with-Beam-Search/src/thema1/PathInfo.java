package thema1 ;

import java.util.ArrayList ;

public class PathInfo {
	private String taxiID ;
	private double distance ;
	private ArrayList<Node> path ;
	private boolean optimal ;
	
	public PathInfo(double distance, ArrayList<Node> path) {
		taxiID = null ;
		this.distance = distance ;
		this.path = path ;
		optimal = false ;
	}
	
	public void setTaxiID (String taxiID) {
		this.taxiID = taxiID ;
	}
	
	public void setOptimal (boolean optimal) {
		this.optimal = optimal ;
	}
	
	public String getTaxiID() { return taxiID ; }
	
	public double getDistance() { return distance ; }
	
	public ArrayList<Node> getPath() { return path ; }
	
	public boolean getOptimal() { return optimal ; }
}