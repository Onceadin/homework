import java.util.Random;


public class MainClass {

	public static void main(String[] args) throws InterruptedException {
		Elevator elevator = new Elevator(10, 10);
		new Thread(elevator).start();
		Random r = new Random();
		for(int i = 0; i < 20; i++){
			new Thread(new Passenger(elevator, r.nextInt(10), r.nextInt(10))).start();
			Thread.sleep(r.nextInt(5000));
		}

	}

}
