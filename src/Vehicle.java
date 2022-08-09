import java.util.Vector;

abstract class Vehicle implements Comparable,Costable{
	protected int carId;
	protected int speed;
	protected double usageCost;
	protected int capacity;
	protected int fuelCap;
	protected Vector <Detective> detectives; // vector of detectives within the vehicle
	protected Vector<Investigator> investigators; // vector of investigators within the vehicle
	protected Vector <Agent> agents;
	protected boolean isAvailable; // availability of the vehicle

	public void setIsAvailable() { // sets the vehicle to be available
		this.isAvailable = true;
	}
	public void setUnAvailable() { // sets the vehicle to be unavailable
		this.isAvailable = false;
	}
	public boolean getIsAvailable() {
		return this.isAvailable;
	}
	public int getSpeed() {
		return this.speed;
	}
	public int getCarId() {
		return this.carId;
	}
	public double getUsageCost() {
		return this.usageCost;
	}
	public int getCapacity() {
		return this.capacity;
	}
	public int getFuelCap() {
		return this.fuelCap;
	}
	public Vector<Detective> getDetectives(){
		return this.detectives;
	}
	public Vector<Investigator> getInvestigators(){
		return this.investigators;
	}
	public Vector<Agent> getAgents(){
		return this.agents;
	}
	public double getCost() { // Costable method
		return this.usageCost;
	}

	public void addDetective (Detective d) { // adds a detective to the vehicle
		if(this.isFull()) {
			throw new VehicleIsFullException("Cannot add anthor agnet, the vehicle is already full"); // RunTime error
		}
		this.detectives.add(d); 
		this.agents.add(d);
	}

	private int OccupiedChairs() { // returns the number of agents in the car
		return this.getAgents().size();
	}

	public void addInvestigator (Investigator in) { // adds an investigator to the vehicle
		if(this.isFull()) { 
			if(countDrivers() > 1) {
				for(int i=0;i<this.agents.size();i++) { //replacing a detective with driver license with investigator with driver license
					if(this.agents.elementAt(i) instanceof Detective && this.agents.elementAt(i).getCanDrive() ) {
						this.agents.remove(i);
						this.detectives.remove(agents.elementAt(i));
						this.getInvestigators().add(in);
						this.agents.add(in);
					}
				}
			}
			else if (countDrivers() == 0){
				for(int i=0;i<this.agents.size();i++) { //replacing a detective with investigator
					if(this.agents.elementAt(i) instanceof Detective) {
						this.agents.remove(i);
						this.detectives.remove(agents.elementAt(i));
						this.getInvestigators().add(in);
						this.agents.add(in);
						break;
					}
				}
			}
		}
		else {
			this.getInvestigators().add(in);
			this.agents.add(in);
		}
	}

	public boolean isFull() { // checks if the vehicle is full
		if(this.getCapacity() == this.OccupiedChairs() )
			return true;
		return false;
	}

	private int countDrivers() { // checks if there are 2 agents with driver license 
		int drivers = 0;
		for(int i = 0; i <this.getAgents().size();i++) {
			if(this.getAgents().elementAt(i).getCanDrive() == true)
				drivers++;
		}
		return drivers;
	}

	public int compareTo(Object other) { // compares two vehicles by there natural comparison
		if(getSpeed() > ((Vehicle)other).getSpeed())
			return 1;
		else if(getSpeed() < ((Vehicle)other).getSpeed())
			return -1;
		else
			return 0;
	}

	public String toString () {
		return "carId: " +carId+ ", speed: "+speed+", usageCost: "+usageCost+ ", capacity: "+capacity+", fuelCap: " +fuelCap+".\n";
	}
}
