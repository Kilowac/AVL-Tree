public class Queue<T>{
	//QUEUE IS FUNCTIONAL
	//LOOK AT STACK FOR COMMENTS ON NULL DATA
	private boolean empty = true;
	private Node<T> end = new Node<T>(null);
	private Node<T> ptr;
	private Node<T> front;
	private T data;

	public Queue(){
		front = end;//nothing is in the queue
	}

	public Queue(T data){//Instanced with data
		front = end;
		enqueue(data);
	}

	public void enqueue(T data){
		if(data == null){//If null data don't enqueue; deal with it later
			return;
		}
		if(empty){//if empty; Separate because end's left pointer points to nothing, which is used in the else statement
			front = new Node<T>(data);//new data node
			front.setRight(end);//point to the end since it's the first node
			end.setLeft(front);//end points to it to keep track
		} else {
			ptr = new Node<T>(data);//new data node
			end.getLeft().setRight(ptr);//get the previous end and point it to the new enqueued data
			ptr.setRight(end);//have the new node point to the end node
			end.setLeft(ptr);//end now points to new node
		}
		empty = false;
	}

	public T peek(){
		return front.getData();
	}

	public T dequeue(){
		if(empty){
			return null;
		}
		data = front.getData();//front data stored in local var
		front = front.getRight();//front moves to the next queued node, prev is GC'd
		if(front.getData() == null){//front is pointing to end
			front.setLeft(null);//clear the left over pointer from end pointing left
			empty = true;
		}
		return data;
	}

	public boolean isEmpty(){
		return empty;
	}

}
