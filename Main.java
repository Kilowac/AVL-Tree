import java.util.Random;
public class Main{
	public static void main(String[] args){
		Random rand = new Random();
		AVLTree tr = new AVLTree();
		//BinaryTree bin =  new BinaryTree();

		tr.addData(11);
		tr.stats();
		tr.addData(13);
		tr.stats();
		tr.addData(12);
		tr.stats();
		for(int i = 1; i <= 7; i++)
			tr.addData(rand.nextInt(50));tr.stats();
		
	}
}
