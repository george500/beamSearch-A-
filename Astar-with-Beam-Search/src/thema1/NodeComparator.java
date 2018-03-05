package thema1 ;
import java.util.Comparator ;

public class NodeComparator implements Comparator<Node> {
	@Override                   //comparator gia tin priority queue pou vasizetai stin real kai heuristic apostasi
	public int compare(Node A, Node B){
		if (A.getHeuristic() + A.getReal() < B.getHeuristic() + B.getReal()){
			return -1 ;
		}
		else if (A.getHeuristic() + A.getReal() == B.getHeuristic() + B.getReal()){
			return 0 ;
		}
		else{
			return 1 ;
		}

	}
}
