import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Passenger implements Runnable {
	private int currentLevel;
	private int destinationLevel;
	private boolean inside;
	private Elevator elevator;
	private static Lock lock = new ReentrantLock();

	public Passenger(Elevator elevator, int currentLevel, int destinationLevel) {
		this.elevator = elevator;
		this.currentLevel = currentLevel;
		this.destinationLevel = destinationLevel;
		this.inside = false;
	}

	@Override
	public void run() {
		System.out.println("Passenger was created on " + currentLevel
				+ " level");
		System.out.println("My destination level is " + destinationLevel + ".\n\n");
		elevator.callForEnter(currentLevel);

		while (!inside) {
			if (lock.tryLock()) {
				try {
					if (elevator.getCurrentLevel() != currentLevel) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						if (elevator.getInside()) {
							elevator.callForOut(destinationLevel);
							System.out.println("I'm inside!");
							System.out.println("I'll go out on " + destinationLevel + "level.");
							inside = true;
						}
					}
				} finally {
					lock.unlock();
				}
			}
		}
		while (inside) {
			if (lock.tryLock()) {
				try {
					if (elevator.getCurrentLevel() != destinationLevel) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						elevator.getOut();
						inside = false;
						System.out.println("I'm going out on this level. Buy!");
					}
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
