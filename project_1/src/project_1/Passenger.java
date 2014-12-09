package project_1;

public class Passenger extends Thread {
	private Thread t;
	private String threadName;
	private int currentLevel;
	private int destinationLevel;
	private boolean inside;
	private Elevator elevator;

	public Passenger(String threadName, int destinationLevel, int currentLevel,
			Elevator elevator) {
		this.threadName = threadName;
		this.destinationLevel = destinationLevel;
		this.currentLevel = currentLevel;
		this.elevator = elevator;
		this.inside = false;
		System.out.println("Thread is named " + threadName + " was created\n");
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public int getDestination() {
		return destinationLevel;
	}

	public void takeOn() {
		synchronized (this) {
			inside = true;
			notify();
		}

	}

	public void takeOff() {
		synchronized (this) {
			inside = false;
			System.out.println(threadName + " is moved out from elevator\n");
			notify();
		}
	}

	@Override
	public void run() {
		System.out.println(threadName + " waiting on the " + currentLevel
				+ "\n");
		elevator.stayToQueue(this);
		while (!inside)
			;
		while (inside)
			;
		System.out.println("I'm done\n");
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
