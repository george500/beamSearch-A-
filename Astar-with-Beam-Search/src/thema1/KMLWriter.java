package thema1 ;
import java.io.BufferedWriter ;
import java.io.FileWriter ;


public class KMLWriter {
	
	String poli = "athina_";		//px athina_, kavala_ 

	public void createKMLTester(String fileName) {
		String[] taxiName = new String[] {"A12", "B15"} ;
		String[] color = new String[] {"#green", "#red"} ;
		String[][] coor = new String[][] {
			{"22.735289,38.006913,0", "22.783516,37.931722,0"},
			{"22.783516,37.931745,0", "22.803516,37.981745,0"},
		} ;
		createKML(taxiName, color, coor) ;
	}

	//takes the path of the file, the route color for every taxi, and the array of coordinates of each route 
	public void createKML(String[] taxiName, String[] color, String[][] coor) {
		String kmlFile = ".\\resources\\output\\" + poli + "result" + TaxiApp.maxMetwpo + "8888.kml" ;

		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"\t<kml xmlns=\"http://earth.google.com/kml/2.1\">\n" +
				"\t<Document>\n" +
				"\t\t<name>Taxi Routes</name>\n" +
				"\t\t<Style id=\"green\">\n" +
				"\t\t\t<LineStyle>\n" +
				"\t\t\t\t<color>ff009900</color>\n" +
				"\t\t\t\t<width>4</width>\n" +
				"\t\t\t</LineStyle>\n" +
				"\t\t</Style>\n" +
				"\t\t<Style id=\"red\">\n" +
				"\t\t\t<LineStyle>\n" +
				"\t\t\t\t<color>ff0000ff</color>\n" +
				"\t\t\t\t<width>4</width>\n" +
				"\t\t\t</LineStyle>\n" +
				"\t\t</Style>\n" ;
		String kmlelement1 = "\t\t<Placemark>\n" +
				"\t\t\t<name>Taxi " ;		//meta bainei to noumero tou taxi
		String kmlelement2 = "</name>\n" +
				"\t\t\t<styleUrl>#" ; 		//meta bainei to xrwma tis diadromis
		String kmlelement3 = "</styleUrl>\n" +
				"\t\t\t<LineString>\n" +
				"\t\t\t\t<altitudeMode>relative</altitudeMode>\n" +
				"\t\t\t\t<coordinates>\n" ; //meta bainoun oi syntetagmenes me \t\t\t\t\t brosta tous
		String kmlelement4 = "\t\t\t\t</coordinates>\n" +
				"\t\t\t</LineString>\n" +
				"\t\t</Placemark>\n" ;
		String kmlend = "</Document>\n" +
				"</kml>" ;

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(kmlFile)) ;
			writer.write(kmlstart) ;
			String insert ;
			if (taxiName != null) {
				for (int i = 0 ; i < taxiName.length ; i++) {
					insert = kmlelement1 + taxiName[i] + kmlelement2 +  color[i] + kmlelement3 ;
					for (int j=0 ; j<coor[i].length ; j++) {
						insert += "\t\t\t\t\t" + coor[i][j] + "\n" ;
					}
					insert += kmlelement4 ;
					writer.write(insert) ;
				}
			}
			writer.write(kmlend) ;
			writer.close() ;
		}
		catch (Exception e) {
			e.printStackTrace() ;
		}
	}

}
