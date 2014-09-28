
public class MainClass {

	public static void main(String[] args) {
		RBTree<Double> firstTest = new RBTree<Double>();
		firstTest.insert(12.2);
		firstTest.insert(0.2);
		firstTest.insert(0.0);
		System.out.println(firstTest.find(11.2));
	}

}
