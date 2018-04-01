import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class HoffmanCoding1 {
	
	public static class Node<E>{
		E symbol;
		int frequency;
		Node leftChild;
		Node rightChild;
		String code;
		
		public Node(E symbol, int frequence) {
			this.symbol = symbol;
			this.frequency = frequence;
			this.code = "";
		}
		
		public String toString() {
			return "[Symbol: "+symbol+", Frequence: "+frequency+", code: "+code+"]";
		}
		
		public void setCode(String code) {
			this.code += code;
		}
		
		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}
		
		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}
		
		public void setSymbol(E symbol) {
			this.symbol = symbol;
		}
		
		public String getCode() {
			return code;
		}
		
		public boolean isParent() {
			if(symbol == "Parent")
				return true;
			else
				return false;
		}
	}
	
	
	public static void main(String[] args) {
		List<Node> nodes = new ArrayList<Node>();
		long startTime = System.nanoTime();	
		//input
		//for test the time complexity we can make it a loop
		//for(int i=0; i<2; i++) {
			nodes.add(new Node("Zhujian", 9));
			nodes.add(new Node("Feijian", 6));
			nodes.add(new Node("Yuanfei", 5));
			nodes.add(new Node("Zilaiye", 7));
			nodes.add(new Node("Shuimen", 8));
			nodes.add(new Node("Kakaxi", 4));
			nodes.add(new Node("Mingren", 10));
			nodes.add(new Node("Boren", 3));
		//}

		
		//Sort the Nodes
		sort(nodes);
		
		//create the hoffman tree
		Node tree = createHoffmanTree(nodes);
		
		//Encode the symbols recursively and print the symbols which hoffman code included
		System.out.println("These are the symbols encoded:");
		encodeSymbols(tree);
	    long endTime = System.nanoTime();
	    System.out.println("Time length:" + (endTime - startTime) + "ns");

	}
	
	public static void sort(List<Node> nodes) {
		quickSort(nodes, 0, nodes.size()-1);
		//System.out.println(nodes);
	}
	
	public static void quickSort(List<Node> nodes, int first, int last) {
		if (first<last) {
			//Divide from here
			Node divider = nodes.get(first);
			//start from left
			int l = first;
			//start from right
			int r = last+1;
			while(true) {
				//find the node whose frequency is bigger than the divider 
				while(l < last && nodes.get(++l).frequency >= divider.frequency);
				//find the node whose frequency is smaller than the divider
				while(r > first && nodes.get(--r).frequency <= divider.frequency);
				if(l<r) {
					swap(nodes, l, r);
				}else {
					break;
				}
			}
			swap(nodes, first, r);			
			quickSort(nodes, first, r-1);
			quickSort(nodes, r+1, last);
		}
	}
	
	public static void swap(List<Node> nodes, int i, int j) {
		Node mid = nodes.get(i);
		nodes.set(i, nodes.get(j));
		nodes.set(j, mid);
	}
	
	//create the hoffman tree
	public static Node createHoffmanTree(List<Node> nodes) {
		while((nodes.size() > 1)&&(nodes.get(0) != null)) {
			//let the least symbol be the rightchild
			//let the second least symbol be the leftchild
			Node right = nodes.get(nodes.size()-1);
			Node left = nodes.get(nodes.size()-2);
			//creat the parent of them
			Node parent = new Node(null, left.frequency + right.frequency);
			parent.setSymbol("Parent");
			parent.setLeftChild(left);
			parent.setRightChild(right);
			nodes.remove(nodes.size()-1);
			nodes.remove(nodes.size()-1);
			nodes.add(parent);
			sort(nodes);
		}
		return nodes.get(0);
	}
	
	//Encode the symbols and print the symbols which hoffman code included recursively
	public static void encodeSymbols(Node root) {
		if(root == null) return;
		//make sure that the left child of this node exists
		if(root.leftChild != null)
			//add a "0" to the left child of this node
			root.leftChild.setCode(root.getCode()+"0");
			//print out the node if this node is the symbol I inputed
			if((root.leftChild != null)&&(!root.leftChild.isParent())) System.out.println(root.leftChild);
		//make sure that the right child of this node exists
		if(root.rightChild != null)
			//add a "1" to the right child of this node
			root.rightChild.setCode(root.getCode()+"1");
			//print out the node if this node is the symbol I inputed
			if((root.rightChild != null)&&(!root.rightChild.isParent())) System.out.println(root.rightChild);
		//recursively implement the above steps
		encodeSymbols(root.leftChild);
		encodeSymbols(root.rightChild);
	}
}
