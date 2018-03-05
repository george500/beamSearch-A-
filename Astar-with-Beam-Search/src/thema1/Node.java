package thema1 ;
import java.util.ArrayList ;


public class Node {					//kathe node exei mia lista apo geitones, string kai double apo X,Y, tis apostaseis,
	//ena prev node pou deixnei ton patera ston A*
	private String X, Y ;
	private ArrayList<Tuple> neighbours ;	//γεμίζει από την class CSVReader
	private double heuristic, real ;
	private double XCoord, YCoord ;
	private Node prevNode ;

	public static double distanceCalculatorKM(double latA, double lonA, double latB, double lonB) {
		/*double theDistance = Math.sin(Math.toRadians(latA)) *		//pio ligo (megali apoklisi)
	            Math.sin(Math.toRadians(latB)) +
	            Math.cos(Math.toRadians(latA)) *
	            Math.cos(Math.toRadians(latB)) *
	            Math.cos(Math.toRadians(lonA - lonB)) ;
	            
		return new Double((Math.toDegrees(Math.acos(theDistance))) * 69.09) ;*/
		//int r = 6371; // average radius of the earth in km		//pio poly	(mikroteri apoklisi)
		double r = 6370.079 ;	// earth radius @Athens=37.9838 N		//pio poly	(mikroteri apoklisi)
		double dLat = Math.toRadians(latB - latA) ;
		double dLon = Math.toRadians(lonB - lonA) ;
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)) 
				* Math.sin(dLon / 2) * Math.sin(dLon / 2) ;
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) ;
		double d = r * c ;
		return d ;
		
	}

	public Node(String X, String Y){
		this.X = X ;
		this.Y = Y ;
		neighbours = new ArrayList<Tuple>() ;
		XCoord = Double.parseDouble(X) ;
		YCoord = Double.parseDouble(Y) ;
		prevNode = null ;
		real = -1 ;
		heuristic = -1 ;
	}

	public void setHeuristic(Node G){
		//heuristic = Math.sqrt(Math.pow(XCoord-G.getXCoord(),2.0) + Math.pow(YCoord- G.getYCoord(),2.0)) ;		//ama thelw metrisi se syntetagmenes
		double latA = XCoord ;
		double latB = G.getXCoord() ;
		double longA = YCoord ;
		double longB = G.getYCoord() ;
		heuristic =  distanceCalculatorKM(latA, longA, latB, longB) ;
	}

	public void setReal(double real) {
		this.real = real ;
	}

	@Override
	public boolean equals(Object S){
		if (!(S instanceof Node)){
			return false ;
		}
		else {
			Node S1 = (Node) S ;
			String X1 = S1.getX() ;
			String Y1 = S1.getY() ;

			return (X.equals(X1) && Y.equals(Y1)) ;
		}
	}

	@Override
	public  int hashCode(){
		int result = 17 ;
		result = 31*result + X.hashCode() ;
		result = 31*result + Y.hashCode() ;
		return result ;
	}

	public void addNeighbour(Node New){             //prosthetei enan neo geitona kai tin apostasi tou apo ton komvo
		//elegxontas prwta an yparxei

		double x1 = Double.parseDouble(New.getX()) ;
		double x2 = Double.parseDouble(X) ;
		double y1 = Double.parseDouble(New.getY()) ;
		double y2 = Double.parseDouble(Y) ;

		//double dist = Math.sqrt(Math.pow((x1-x2),2.0) + Math.pow((y1-y2),2)) ;			/////////////////////
		double dist = distanceCalculatorKM(x1, y1, x2, y2) ;

		Tuple newNeighbor = new Tuple(New, dist) ;
		String X1 = New.getX() ;
		String Y1 = New.getY() ;

		for (int i = 0 ; i < neighbours.size() ; i++){
			Tuple Z = neighbours.get(i) ;
			Node Y = Z.getX() ;
			String X2 = Y.getX() ;
			String Y2 = Y.getY() ;

			if (X1.equals(X2) && Y1.equals(Y2)){
				return ;
			}
		}

		neighbours.add(newNeighbor) ;

	}

	public void setPrevNode(Node S) { prevNode = S ; }

	public Node getPrevNode() { return  prevNode ;}

	public double getXCoord() { return XCoord ; }

	public double getYCoord() { return YCoord ; }

	public double getHeuristic() { return heuristic ; }

	public  double getReal() { return real ; }

	public String getX(){ return X ; }

	public String getY(){ return Y ; }

	public ArrayList<Tuple> getNeighbours(){
		return neighbours ;
	}

	public void printNode() { System.out.println(X + " " + Y) ;}

	public void printNeighbours(){ //eixe xrhsimopoiithei gia elegxo tis dimiourgias toy grafoy
		System.out.println(neighbours.size()) ;
		for (int i = 0 ; i < neighbours.size() ; i++){
			Node curr = neighbours.get(i).getX() ;
			double dist = neighbours.get(i).getY() ;
			curr.printNode() ;
			System.out.println(dist) ;
		}
	}

}