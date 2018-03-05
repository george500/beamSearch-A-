package thema1 ;

public class Taxi extends Node{
	private String ID ;

	public Taxi(String X, String Y, String Z){
		super(X, Y) ;
		ID = Z ;
	}

	public void setID(String S) { ID = S ; }

	public String getID(){
		return ID ;
	}

	public void printNode() { System.out.println(super.getX() + " " +  super.getY()+ " " + ID) ;}

}