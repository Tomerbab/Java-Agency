import java.util.Vector;

public class Car extends Vehicle {

	public Car(int carId, int speed, double usageCost, int capacity, int fuelCap) { // Car constructor
		this.carId = carId;
		this.speed = speed;
		this.usageCost = usageCost;
		if(capacity < 3 || capacity > 6 ) //capacity conditions
			throw new CapacityNotInRangeException ("The capacity you enterd (" + capacity + ") is not valid, please enter an number between 3-6."); // RunTime error
		this.capacity = capacity;
		this.fuelCap = fuelCap;
		this.setIsAvailable();
		this.detectives = new Vector <Detective>();
		this.investigators = new Vector <Investigator>();
		this.agents = new Vector <Agent>();
	}
}
