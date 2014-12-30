import java.util.LinkedList;
import java.util.Queue;

public class Elevator implements Runnable {
	private int maxLevel;
	private int currentLevel;
	private volatile int inside; // number of passengers inside
	private int capacity;
	private Queue<Integer> calledForEnter;
	private Queue<Integer> calledForOut;

	public Elevator(int maxLevel, int capacity) {
		this.maxLevel = maxLevel;
		this.capacity = capacity;
		this.inside = 0;
		this.currentLevel = 0;
		this.calledForEnter = new LinkedList<>();
		this.calledForOut = new LinkedList<>();
	}

	// call the elevator from outside
	public void callForEnter(int level) {
		calledForEnter.add(level);
	}

	// call the elevaor from inside
	public void callForOut(int level) {
		calledForOut.add(level);
	}

	// move up to one level
	private void moveUp() {
		if (currentLevel < maxLevel) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=========== " + ++currentLevel
					+ " level ============");
		}
	}

	// move down to one level
	private void moveDown() {
		if (currentLevel > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("=========== " + --currentLevel
					+ " level ============");
		}
	}

	// go to specified level
	public void moveTo(int level) {
		System.out.println("I'll go to " + level + "level");
		if (currentLevel > level) {
			while (currentLevel != level)
				moveDown();
		} else if (currentLevel < level) {
			while (currentLevel != level)
				moveUp();
		} else {
			System.out.println("Elevator is already here!");
		}
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public boolean getInside() {
		if (inside < capacity) {
			inside++;
			return true;
		}
		return false;
	}

	public void getOut() {
		inside--;
	}

	@Override
	public void run() {
		while (true) {
			// wait for calling
			while (calledForEnter.isEmpty() && calledForOut.isEmpty()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// go to called level
			if (!calledForEnter.isEmpty()) {
				moveTo(calledForEnter.poll());
			}
			if (!calledForOut.isEmpty()) {
				moveTo(calledForOut.poll());
			}

		}
	}

}
