package project_1;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Elevator extends Thread {
	private int currentLevel;
	private int capacity;
	private List<Passenger> passengersInside;
	private List<Passenger> passengersWaited;
	private Queue<Integer> calledFloors;
	private Thread t;

	public Elevator(int maxLevel, int capacity) {
		this.currentLevel = 0;
		this.capacity = capacity;
		passengersInside = new ArrayList<Passenger>(capacity);
		passengersWaited = new ArrayList<Passenger>();
		calledFloors = new PriorityQueue<Integer>(maxLevel - 1);
	}

	public void stayToQueue(Passenger pas) {
		passengersWaited.add(pas);
	}

	public void callTheElevator(int floor) {
		synchronized (this) {
			if (!calledFloors.contains(floor)) {
				calledFloors.add(floor);
			}
			notify();
		}
	}

	public boolean isFull() {
		return passengersInside.size() == capacity;
	}

	public boolean getInside(Passenger someone) {
		synchronized (this) {
			if (!isFull()) {
				someone.takeOn();
				passengersInside.add(someone);
				notify();
				return true;
			}
			notify();
			return false;
		}
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	private void move() {
		synchronized (this) {
			int tmp = currentLevel - calledFloors.poll();
			boolean direction = false;
			if (tmp < 0) {
				direction = true;
				tmp *= -1;
			}
			for (int i = 0; i < tmp; i++) {
				try {
					wait(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("\n\n" + currentLevel + " FLOOR\n");
				if (direction)
					currentLevel++;
				else
					currentLevel--;

			}
		}
	}

	public void run() {
		for (;;) {
			while (!passengersWaited.isEmpty()) {
				synchronized (this) {
					Passenger tmp = passengersWaited.remove(0);
					calledFloors.add(tmp.getCurrentLevel());
					move();
					tmp.takeOn();
					calledFloors.add(tmp.getDestination());
					move();
					tmp.takeOff();
					notify();
				}
			}
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "ELEVATOR");
			t.setDaemon(true);
			t.start();
		}
	}

	public void moveTo(int destinationLevel) {
		synchronized (this) {
			int tmp = currentLevel - destinationLevel;
			boolean direction = false;
			if (tmp < 0) {
				direction = true;
				tmp *= -1;
			}
			for (int i = 0; i < tmp; i++) {
				try {
					wait(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (direction)
					System.out.println("\n\n" + ++currentLevel + " FLOOR\n");
				else
					System.out.println("\n\n" + --currentLevel + " FLOOR\n");

			}
			notify();
		}
	}
}
