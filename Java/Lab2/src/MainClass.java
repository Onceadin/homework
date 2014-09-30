
public class MainClass {

	public static void main(String[] args) {
		RBTree<String> firstTest = new RBTree<String>();
		firstTest.add("to");
		firstTest.add("be");
		firstTest.add("or");
		firstTest.add("not");
		firstTest.add("to");
		firstTest.add("be");
		firstTest.add("yes");
		firstTest.add("no");
		firstTest.add("maybe");
		int i = 1;
		for(String x : firstTest){
			System.out.println(x + " " + i++);
			int j = 1;
			for(String y : firstTest){
				System.out.println(y + " " + j++);
			}
		}

	}

}
