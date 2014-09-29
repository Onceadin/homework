
public class MainClass {

	public static void main(String[] args) {
		RBTree<String> firstTest = new RBTree<String>();
		firstTest.add("01");
		firstTest.add("no");
		firstTest.add("yes");
		System.out.println(firstTest.remove("no"));
		System.out.println(firstTest.remove("01"));
		System.out.println(firstTest.remove("yes"));
		System.out.println(firstTest.remove("no"));
	}

}
