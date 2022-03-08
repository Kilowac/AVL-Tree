public class AVLTree extends Tree{//AVL is going to be a binary tree
	//TODO: Clean up, addData and the node tumbling could be merged togeather if the addData's data's node was instaced before tumbling, rotation has some spaghetti code
	//TODO: have the Node instance accept a parent node argument
	private Node<Integer> top, mid, bot;
	private Node<Integer> root;
	private Node<Integer> ptr;
	private Stack<Node<Integer>> stack = new Stack<Node<Integer>>();
	private Queue<Node<Integer>> q = new Queue<Node<Integer>>();
	public boolean balanced = true;
	private boolean con;
	private int depth;

	public AVLTree(){
		root = new Node<Integer>(null);//No data. Null root data
	}

	public AVLTree(int data){
		root = new Node<Integer>(data);//AVLTree instanced with data
	}

	public void addData(int data){
		tumble(root, data);
		analyze(root);
		isBalanced(root);
		System.out.println(balanced);
		if(!balanced){
			stats();
		}
		while(!balanced){
			con = false;
			rotations(root);
			analyze(root);
			balanced = true;
			isBalanced(root);
		}
	}

	private void tumble(Node<Integer> parent, int data){//Add data, the user doesn't decide where it's placed as this is an AVLTree
		if(parent.getData() == null){//If the root has no data then add data to the root
			parent.setData(data);
		} else if(data <= parent.getData()) {//Any number less than or equal to the root number will go down left
			if(parent.getLeft() != null){//If the left child has data already, tumble down the tree and find a proper place
				tumble(parent.getLeft(), data);//Tumble
			} else {//If there is no data
				parent.setLeft(new Node<Integer>(data));
				parent.getLeft().setParent(parent);
			}
		} else {//If data is more than the root data
			if(parent.getRight() != null){ //if a right child already exists
				tumble(parent.getRight(), data);//tumble
			} else { //no datai in the right child
				parent.setRight(new Node<Integer>(data));
				parent.getRight().setParent(parent);
			}
		}	
	}

	//FIXME:I might not even need to use a stack, I could just do what I did for the binarytree inversion
	//Learn how to do this without using the recursion stack; risk of stack overflow; Possibly do this with a while loop with stack.isEmpty() condition
	private void analyze(Node<Integer> node){
		node.lDepth = 0;//This is to clear previous depths, depths will be calculated at the end of recursion
		node.rDepth = 0;
		if(node.getLeft() != null){//if a left child exists
			stack.push(node);//push this parent node on the stack
			analyze(node.getLeft());//Depth first search, so we stop checking this node and just continue to the next
		} if (node.getRight() != null) {//if a left child DOES NOT exist
			stack.push(node);//push this parent node
			analyze(node.getRight());//recurse the right child
		}
		if(stack.isEmpty()){ //Root condition to skip popping the stack and just calculate weight
		} else {//THIS IS JUST FOR FINDING HEIGHT NOT WEIGHT
			ptr = stack.pop();//pop parent
			depth = node.lDepth > node.rDepth ? node.lDepth + 1 : node.rDepth + 1;
			if(ptr.getLeft() == node){ //Compare node addresses check if left or right address
					ptr.lDepth += depth;
			} else if(ptr.getRight() == node){//See above
					ptr.rDepth += depth;
			} else {//This shouldn't ever execute, but just for testing
				System.out.println(">>>>>ERROR: CHILDREN DO NOT MATCH<<<<<");
				System.exit(0);
			}
		}
		node.weight = node.lDepth - node.rDepth;//analyze weight
	}

	//Input is a node, check depth first searcH, then climb up, checking the weights
	private void rotations(Node<Integer> node){
		if(node.getLeft() != null){
			rotations(node.getLeft());//if a rotation did occur then con will be true
			if(con){//then exit and re-analyze
				return;
			}
		} if(node.getRight() != null){
			rotations(node.getRight());
			if(con){
				return;
			}
		}
		if (node.weight != 1 && node.weight != 0 && node.weight != -1){//if a node is imbalanced then it will have at least one child and one grandchild, garunteed
			top = node;//Unbalanced node
			mid = node.weight >= 0 ? node.getLeft() : node.getRight();//check for children weights to check rotation type later on (e.g. LL, RR, LR, RL rotations)
			bot = mid.weight >= 0 ? mid.getLeft() : mid.getRight();
			rotate(top,mid,bot);//You only rotate with three nodes.
			con = true;//a rotation did occur
		}
	}	

	private void rotate(Node<Integer> top, Node<Integer> mid, Node<Integer> bot){
		//Kinda spaghetti code right here
		if((top.weight >= 0 && mid.weight >= 0 && bot.weight >= 0) || (top.weight <= 0 && mid.weight <= 0 && bot.weight <= 0)){
			//A LL or a RR node insertion
			mid.setParent(top.getParent());//Mid will take the place of the previous top
			if(top.getLeft() == mid){//Since mid will be the new top, top will have to get rid of the child pointer, since mid is now above top. No longer a child
				top.setLeft(null);	//	*		*
			} else {			//	 \	       /
				top.setRight(null);	//        *	Or    *
			}				//	   \	     /
			if(top.getParent() == null){	//          *	    *
				root = mid;//If top was a root then set root to mid, since there's no parent you can't set it's left or right child
			} else if(top.getParent().getLeft() == top){//check previous top was a left or right child and update the parent's child pointers
				top.getParent().setLeft(mid);
			} else {
				top.getParent().setRight(mid);
			}
			tumble(mid, top);//tumble, insert top. Just like 'addData' but uses nodes. Could use cleanup
		} else {
			bot.setParent(top.getParent());//Else, this would be a LR or a RL node insertion
			if(top.getParent() == null){
				root = bot;//Bot would be root if top was root
			} else if(top.getParent().getLeft() == top){//else update top's parent's children
				top.getParent().setLeft(bot);
			} else {
				top.getParent().setRight(bot);
			}
			if(top.getLeft() == mid){//update children, set to null
				top.setLeft(null);	//	*		*
			} else {			//	 \	       /
				top.setRight(null);	//	  *	Or    *
			}				//	 /	       \
			if(mid.getLeft() == bot){	//	*		*
				mid.setLeft(null);
			} else {
				mid.setRight(null);
			}
			tumble(bot, top);//tumble top and mid from the bot node
			tumble(bot, mid);
		}
	}
	private void tumble(Node<Integer> mount, Node<Integer> fall){
		if(fall.getData() <= mount.getData()){
			if(mount.getLeft() != null){
				tumble(mount.getLeft(), fall);
			} else {
				mount.setLeft(fall);
				fall.setParent(mount);
			}
		} else {
			if(mount.getRight() != null){
				tumble(mount.getRight(), fall);
			} else {
				mount.setRight(fall);
				fall.setParent(mount);
			}
		}
	}//insertion, but with nodes, could incorporate this with 'addData' by creating the node object before initiating it
	
	//checking balance works
	public boolean isBalanced(){
		return balanced;
	}

	private void isBalanced(Node<Integer> node){//Depth first search, check if the tree is unbalanced
		if(node.getLeft() != null){
			isBalanced(node.getLeft());
		} if(node.getRight() != null){
			isBalanced(node.getRight());
		}
		if(node.weight == -1 || node.weight == 0 || node.weight == 1){
		} else {
			balanced = false;
		}
	}



	/////////////////////TESTING CODE///////////////////
	
	
	
	public void stats(){
		stats(root);
		System.out.printf("%n<><><><><><><><><><><><><>%n   Tree Balanced: %b%n<><><><><><><><><><><><><>", balanced);
		if(balanced){
			System.out.printf("%n%n%n<<<<<<<<<<<<<>>>>>>>>>>>>>%n%n%n");
		} else {
			System.out.print("\n\n\n%%%%%%%%%ROTATIONS%%%%%%%%%\n\n\n");
		}
	}

	//Breadth first search to test
	private void stats(Node<Integer> node){
		if(node.getLeft() != null){
			q.enqueue(node.getLeft());
		} if(node.getRight() != null){
			q.enqueue(node.getRight());
		} if(node.getLeft() == null && node.getRight() == null){	
		}
		printProps(node);
		if(q.isEmpty()){ return; }
		stats(q.dequeue());
		if(q.isEmpty()){ return; }
		stats(q.dequeue());
	}

	private void printProps(Node<Integer> node){
		System.out.println("---------------------------");
		System.out.printf("%-14s%s%n%-14s%d%n%-14s%s%n%-14s%s%n%-14s%s%n%-14s%d%n%-14s%d%n%-14s%d%n",
				"Node:",node,
				"Value:",node.getData(),
				"Parent:",node.getParent(),
				"Left Child:",node.getLeft(),
				"Right Child:",node.getRight(),
				"Left Depth:",node.lDepth,
				"Right Depth:",node.rDepth,
				"Weight:",node.weight);
		System.out.println("---------------------------");
		System.out.printf("Node Balanced: %b%nQueue Is Empty: %b%n",(node.weight==-1||node.weight==0||node.weight==1),q.isEmpty());
	}

}
