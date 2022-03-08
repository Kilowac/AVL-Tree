public class Node<T>{
	private T data;//The actual value it holds
	public int weight = 0;
	public int lDepth = 0;
	public int rDepth = 0;
	private Node<T> parent = null;
	private Node<T> left = null;//also prev
	private Node<T> right = null;//also next
	public Node(){
		data = null;
	}
	public Node(T data){
		this.data = data;
	}
	public Node(T data, Node<T> right){
		this.data = data;
		this.right = right;
	}
	public Node(T data, Node<T> right, Node<T> left){
		this.data = data;
		this.right = right;
		this.left = left;
	}

	public void setData(T data){ 
		this.data = data; 
	}

	public void setParent(Node<T> parent){ 
		this.parent = parent; 
	}

	public void setLeft(Node<T> left){ 
		this.left = left; 
	}

	public void setRight(Node<T> right){ 
		this.right = right; 
	}
	
	public T getData(){ 
		return data; 
	}
	
	public Node<T> getParent(){ 
		return parent; 
	}
	
	public Node<T> getLeft(){ 
		return left; 
	}
	
	public Node<T> getRight(){ 
		return right; 
	}

}
