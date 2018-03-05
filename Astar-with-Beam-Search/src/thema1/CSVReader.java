package thema1 ;
import java.io.BufferedReader ;
import java.io.FileNotFoundException ;
import java.io.FileReader ;
import java.io.IOException ;
import java.util.LinkedHashMap ;
import java.util.Map ;

public class CSVReader {

	String poli = "athina_";		//px athina_, kavala_
	
	public Node readClient() {
		String csvFile = ".\\resources\\input\\" + poli + "client.csv" ;
		BufferedReader br = null ;
		String line = "" ;
		String cvsSplitBy = "," ;

		Node client = null ;

		try {

			br = new BufferedReader(new FileReader(csvFile)) ;
			line = br.readLine() ;
			String[] point ;
			while ((line = br.readLine()) != null) {
				point = line.split(cvsSplitBy) ;				// use comma as separator
				client = new Node(point[0], point[1]) ;
				//System.out.println("Client [X= " + point[0] + ", Y=" + point[1] + "]") ;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace() ;
			return null ;
		} catch (IOException e) {
			e.printStackTrace() ;
			return null ;
		} finally {
			if (br != null) {
				try {
					br.close() ;
				} catch (IOException e) {
					e.printStackTrace() ;
				}
			}
		}
		return client ;
	}

	public Map<Node, String> readTaxis() {
		String csvFile = ".\\resources\\input\\" + poli + "taxis.csv" ;
		BufferedReader br = null ;
		String line = "" ;
		String cvsSplitBy = "," ;

		Node taxi = null ;
		Map<Node, String> taxis = new LinkedHashMap<Node,String>() ;

		try {
			br = new BufferedReader(new FileReader(csvFile)) ;
			line = br.readLine() ;
			String[] point ;
			while ((line = br.readLine()) != null) {
				point = line.split(cvsSplitBy) ;				// use comma as separator
				taxi = new Node(point[0], point[1]) ;
				taxis.put(taxi, point[2]) ;
				//System.out.println("Taxi [X= " + point[0] + ", Y=" + point[1] + ", id=" + point[2] + "]") ;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace() ;
			return null ;
		} catch (IOException e) {
			e.printStackTrace() ;
			return null ;
		} finally {
			if (br != null) {
				try {
					br.close() ;
				} catch (IOException e) {
					e.printStackTrace() ;
				}
			}
		}
		return taxis ;
	}

	public Graph readNodes() {
		String csvFile = ".\\resources\\input\\" + poli + "nodes.csv" ;
		BufferedReader br = null ;
		String line = "" ;
		String cvsSplitBy = "," ;

		String prevId = null ;
		String currId = null ;

		Graph graph = new Graph() ;
		Node currNode = null ;
		Node prevNode = null ;

		try {		//to onoma tis odou den apothikevetai, epeidi den yparxei se oles tis grammes
			br = new BufferedReader(new FileReader(csvFile)) ;
			line = br.readLine() ;
			String[] point ;
			while ((line = br.readLine()) != null) {
				point = line.split(cvsSplitBy) ;				// use comma as separator

				currNode = new Node(point[0], point[1]) ;
				Node res = graph.addVertex(currNode) ;		//an o currNode einai neos tha epistrepsei null
				currId = point[2] ;
				if (currId.equals(prevId)){
					Node X = graph.getVertex(currNode.getX()+currNode.getY()) ;
					Node Y = graph.getVertex(prevNode.getX()+prevNode.getY()) ;
					X.addNeighbour(Y) ;
					Y.addNeighbour(X) ;
					//System.out.println("added neighbour.");
				}
				prevId = currId ;
				if (res != null) {		//an diavasa neo node
					currNode = res ;
				}
				prevNode = currNode ;
				//System.out.println("Node [X= " + point[0] + ", Y=" + point[1] + ", id=" + point[2] + ", name=" + point[3] + "]") ;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace() ;
			return null ;
		} catch (IOException e) {
			e.printStackTrace() ;
			return null ;
		} finally {
			if (br != null) {
				try {
					br.close() ;
				} catch (IOException e) {
					e.printStackTrace() ;
				}
			}
		}
		return graph ;
	}
}
