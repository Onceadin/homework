package project_1;


public class MainClass {

	public static void main(String[] args) {
		int countOfPassengers = 9;
		int countOfFloors = 10;
		Passenger[] a = new Passenger[countOfPassengers];
		Elevator elevator = new Elevator(countOfFloors, 10);
		elevator.start();
		new String();
		for (int i = 0; i < countOfPassengers; i++) {
			a[i] = new Passenger(String.format("Passenger %d", i), countOfFloors, i ,
					elevator);
			a[i].start();
		}
		System.out.println("/nMAIN ENDED/n");
	}
}
