public class Stack<T>{
	//STACK IS FUNCTIONAL
	//TODO: Deal with null data pushes, possibly deal with bottom differentiation by using the weight variable for bottom. e.g. -1
	//Deal with it in the queue class
	private boolean empty = true;
	private Node<T> bot = new Node<T>(null);
	private Node<T> ptr;
	private Node<T> top;
	private T data;

	public Stack(){
		top = bot;//Nothing is in the stack
	}

	public Stack(T data){
		top = bot;
		push(data);
	}
	
	public void clear(){
		while(!isEmpty())
			pop();
	}

	public void push(T data){
		if(data == null){//if the data is null then just return, I'll deal with null objcets later
			return;
		}
		ptr = new Node<T>(data);//new data node
		ptr.setLeft(top);//place on top of the previous top node, pointing back to it
		top = ptr;//change top pointer to the new data
		empty = false;//not empty
	}

	public T peek(){
		return top.getData();
	}

	public T pop(){
		if(empty){//empty return null; 
			return null;
		}
		data = top.getData();//top node data stored in local var
		top = top.getLeft();//move top pointer down a node, top no longer pointing to the previous top, so it's cleaned by GC
		if(top.getData() == null){//If the top is now pointing to the bottom of the stack, it is empty
			empty = true;
		}
		return data;
	}

	public boolean isEmpty(){
		return empty;
	}

}
