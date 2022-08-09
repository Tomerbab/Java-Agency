import java.util.Vector;

public class Motorcycle extends Vehicle {
	
	public Motorcycle (int motorId, int speed, double usageCost) { // Motorcycle constructor
		this.carId = motorId;
		this.speed = speed;
		this.usageCost = usageCost;
		int f = (int)((Math.random()*100)); // ballots a number for the motorcycle capacity
		if(f > 50)
			this.capacity = 1;
		else
			this.capacity = 2;
		this.setIsAvailable();
		this.detectives = new Vector <Detective>();
		this.investigators = new Vector <Investigator>();
		this.agents = new Vector <Agent>();
	}
}
