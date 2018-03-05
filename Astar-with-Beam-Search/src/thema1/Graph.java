package thema1 ;

import java.util.* ;

public class Graph{
	private Hashtable<String, Node> g ;			//o grafos ylopoieitai ws ena hashtable pou exei toys kombous
	Comparator<Node> comparator ;
	PriorityQueue<Node> metwpoAnazitisis ;		//prwto stoixeio to pio kontino
	HashSet<Node> visited ;
	HashSet<Node> queue ;
	private int plithosVimatwn, maxMetwpoAnazitisisPragmatiko ;
	
	public Graph(){
		g = new Hashtable<String, Node>() ;
	}

	public Node addVertex(Node New){
		Node res = g.putIfAbsent(New.getX() + New.getY(), New) ;
		return res ;
	}

	public Node getVertex(String S){
		return g.get(S) ;
	}

	private Node removeLast() {
		PriorityQueue<Node> pqnew = new PriorityQueue<Node>(10, comparator) ;
		Node last = null ;
		while(metwpoAnazitisis.size() > 1) {		//leave last element out
			last = metwpoAnazitisis.poll() ;
			pqnew.add(last) ;
		}
		metwpoAnazitisis.clear() ;
		metwpoAnazitisis = pqnew ;
		return last ;
	}

	private void AddElement(Node X1) {		//adds element in list (changes queue)
		if (metwpoAnazitisis.size() < TaxiApp.maxMetwpo-1) {
			metwpoAnazitisis.add(X1) ;
			queue.add(X1) ;
			//resultPath.add(X1) ;
		}
		else {
			Node lastElement ;
			lastElement = removeLast() ;
			queue.remove(lastElement) ;
			//resultPath.remove(lastElement) ;
			metwpoAnazitisis.add(X1) ;
			queue.add(X1) ;
			//System.out.println("List tried to get big.") ;
			//System.exit(1) ;
			return ;
		}
	}

	public double AStar(Node S, Node G){
		int metwpoAnazitisisMegethos = 0 ;
		maxMetwpoAnazitisisPragmatiko = 0 ;
		plithosVimatwn = 0 ;
		comparator = new NodeComparator() ;
		metwpoAnazitisis = new PriorityQueue<Node>(10, comparator) ;   //to metwpo anazitisis
		//resultPath = new ArrayList<Node>() ;		//i diadromi
		visited = new HashSet<>() ;			//oi komvoi poy exoyme episkeftei
		queue = new HashSet<>() ;			//exei ta idia me tin oura (metwpoAnazitisis), alla prosferei pio grigori euresi stoixeiwn
		//gia na blepoyme an kati yparxei sto metwpo anazitisis idi

		S.setHeuristic(G) ;
		//System.out.println("Heuristic: " + S.getHeuristic());
		S.setReal(0) ;
		
		AddElement(S) ;						//add sto metwpo anazitisis
		//metwpoAnazitisis.add(S) ;
		//queue.add(S) ;
		boolean reached = false ;
		while (!metwpoAnazitisis.isEmpty()) {        //oso to metwpo den einai adeio synexizoyme
			metwpoAnazitisisMegethos = metwpoAnazitisis.size() ;
			if (metwpoAnazitisisMegethos > maxMetwpoAnazitisisPragmatiko) {
				maxMetwpoAnazitisisPragmatiko = metwpoAnazitisisMegethos ;
			}
			//System.out.println("here") ;
			//if (metwpoAnazitisis.size() > TaxiApp.max)
			//	TaxiApp.max = metwpoAnazitisis.size() ;
			Node current = metwpoAnazitisis.poll() ;
			queue.remove(current) ;
			if (current.equals(G)) {
				reached = true ;
				//System.out.println("Reached") ;
				break ;
			}
			visited.add(current) ;

			ArrayList<Tuple> neighbourhood = current.getNeighbours() ; //briskoume toys geitones
			
			//System.out.println(neighbourhood.size());
			
			double distance = current.getReal() ;    //pairnoume tin trexousa apostasi

			for (int i = 0 ; i < neighbourhood.size() ; i++) { //elegxoume kathe geitona
				plithosVimatwn++ ;			/////////////////////////vima A*
				Tuple X = neighbourhood.get(i) ;
				Node X1 = X.getX() ;
				double X2 = X.getY() ;

				if (!visited.contains(X1)) {          //an ton exoume episkefthei tote den asxoloymaste
					if (!queue.contains(X1)) {       //elegxoume an einai sto metwpo
						X1.setReal(distance + X2) ;
						X1.setHeuristic(G) ;         //an den einai ton bazoume
						X1.setPrevNode(current) ;
						AddElement(X1) ;
						/*if (metwpoAnazitisis.size() > TaxiApp.maxMetwpo) {
							System.out.println("Previous: " + prev + ", now: " + metwpoAnazitisis.size()) ;
							System.out.println("Violated") ;
							System.exit(2) ;
						}*/
						//metwpoAnazitisis.add(X1) ;
						//queue.add(X1) ;
					} else {
						double newReal = distance + X2 ;       //an einai sto metwpo elegxoume ti nea real apostasi
						//an einai mikroteri ton bgazoume kai ton bazoume pali
						//gia na allaxei i proteraiotita
						if (X1.getReal() > newReal) {
							metwpoAnazitisis.remove(X1) ;
							//resultPath.remove(X1) ;
							X1.setReal(newReal) ;
							X1.setPrevNode(current) ;
							metwpoAnazitisis.add(X1) ;
							//resultPath.add(X1) ;
						}
					}
				}
			}
		}
		if (reached)
			return G.getReal() ;
		else
			return -1 ;
	}

	public ArrayList<Node> constructPath(Node G){      //ftiaxnei mia lista me to monopati me basi to prev node
		ArrayList<Node> result = new ArrayList<Node>() ;
		result.add(G) ;
		//Node curr = G ;
		Node prev = G.getPrevNode() ;
		while (prev != null){
			//distanceMonopati += Node.distanceCalculatorKM(curr.getXCoord(), curr.getYCoord(), prev.getXCoord(), prev.getYCoord()) ;
			//System.out.println(curr.getXCoord() + ", " + curr.getYCoord() + ", " + 
			//		prev.getXCoord() + ", " + prev.getYCoord()) ;
			//System.out.println(distanceMonopati) ;
			result.add(prev) ;
			//curr = prev ;
			prev = prev.getPrevNode() ;
		}

		return result ;
	}
	
	public PathInfo findPath(Node S, Node G){        //tupwnei to monopati pou exei ftiaxtei apo prin
		//resultPath = null ;
		double distance = AStar(S, G) ;
		//System.out.println("\t" + distance) ;
		if (distance == -1)		//if -1 then not available route
			return null ;

		ArrayList <Node> resultPath = constructPath(G) ;
		//int i = resultPath.size()-1 ;
		
		//double distanceKM = distToKilometers(distance) ;
		
		PathInfo pathInfo = new PathInfo(distance, resultPath) ;
		
		System.out.println("\tPlithos vimatwn: " + plithosVimatwn) ;
		System.out.println("\tmax Megethos metwpou: " + maxMetwpoAnazitisisPragmatiko) ;
		System.out.println("\tRoute distance in kilmeters: " + distance) ;		//mikri apoklisi se sxesi me to google maps <15%
		
		/*
		while (i >= 0) {
			Node current = result.get(i) ;
			System.out.println(current.getX() + "," + current.getY() + ",0") ;
			i-- ;
		}*/
		
		return pathInfo ;
	}

	public Node put(Node S)				//topothetei tous pelates kai ta taxi ston grafo an den yparxoyn sto arxeio nodes
	{
		if (!g.containsValue(S))
		{
			double X1 = S.getXCoord() ;
			double Y1 = S.getYCoord() ;

			double dist = Math.pow(10,10) ;
			Node neighbour = null ;
			Set<String> keys = g.keySet() ;


			for (String key: keys)
			{
				Node cand = g.get(key) ;
				double X2 = cand.getXCoord() ;
				double Y2 = cand.getYCoord() ;


				double newDist = Math.sqrt(Math.pow(X1-X2,2.0) + Math.pow(Y1-Y2,2.0)) ;

				if (newDist < dist)
				{
					dist = newDist ;
					neighbour = cand ;
				}

			}

			S.addNeighbour(neighbour) ;
			neighbour.addNeighbour(S) ;
			g.put(S.getX()+S.getY(),S) ;
			return S ;
		}
		else{
			return g.get(S.getX()+S.getY()) ;
		}

	}

	public boolean contains(Node S){
		return g.containsKey(S.getX() + S.getY()) ;
	}


}