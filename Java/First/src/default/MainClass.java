public class MainClass{
	public static void main(String[] args){
			String sep = args[0];
			String[] text = args[1].split(sep);
			int size = text.length;
			if(size > 0){
				for(int i = size - 1; i >= 0; i--){
					System.out.println(text[i]);
				}
			}
			else System.out.println("text doesnt exist");
		}
}