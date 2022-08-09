import java.util.Vector;

public class Operation implements Comparable {
	private int level;
	private String codeName;
	private String type;
	private Vector <Detective> detectives;
	private Vector <Vehicle> vehicles;
	private Vector<Investigator> investigators;
	private Vector<Investigator> investigatorsClone; // dynamic vector that will help us track the investigators condition during the fix in 
	private Vector<Detective> detectivesClone; // dynamic vector that will help us track the detectives condition during the fix in 
	private int NumberOfDetectives;
	private int NumberOfInvestigators;


	public Operation (int level, String codeName) { // operation constructor
		if (level < 1 || level > 5) { // level conditions
			throw new OperationLevelNotOptionalException ("the level you entered is " + level + ", please enter a level between 1-5"); //RunTime error
		}
		this.level = level;
		this.codeName = codeName;
		this.detectives = new Vector <Detective> (); 
		this.investigators = new Vector <Investigator>();
		this.vehicles = new Vector<Vehicle>();
		this.detectivesClone =new Vector<Detective> ();
		this.investigatorsClone = new Vector <Investigator> ();
		if(level == 1) {
			NumberOfDetectives = 0;
			NumberOfInvestigators = 2;
			this.type = "inquiry";
		}
		if(level == 2) {
			NumberOfDetectives = 2;
			NumberOfInvestigators = 3;
			this.type = "Background check";
		}
		if(level == 3) {
			NumberOfDetectives = 5;
			NumberOfInvestigators = 1;
			this.type = "surveillance";
		}
		if(level == 4) {
			NumberOfDetectives = 6;
			NumberOfInvestigators = 4;
			this.type = "fraud and illegal activity";
		}
		if(level == 5) {
			NumberOfDetectives = 8;
			NumberOfInvestigators = 7;
			this.type = "missing people";
		}
	}

	public int getLevel() {
		return this.level;
	}
	public String getCodeName() {
		return this.codeName;
	}
	public String getType() {
		return this.type;
	}
	public Vector<Detective> getDetectives(){
		return this.detectives;
	}
	public Vector<Vehicle> getVehicles(){
		return this.vehicles;
	}
	public Vector<Investigator> getInvestigators(){
		return this.investigators;
	}
	public Vector<Investigator> getInvestigatorsClone(){
		return this.investigatorsClone;
	}
	public Vector<Detective> getDetectivesClone(){
		return this.detectivesClone;
	}
	public int getNumOfInvestigators () { // returns the desired amount of investigators for the operation 
		return this.NumberOfDetectives;
	}
	public int getNumOfDetectives () { // returns the desired amount of detectives for the operation
		return this.NumberOfInvestigators;
	}

	public boolean addDrivers(int numberOfDrivers, Vector<Agent> v1) { // add agents with driver license
		Agency.sortVector(v1);
		int driver = 0;
		int i =0;
		while(driver != numberOfDrivers) { //while the drivers amount in the operation isn't equal to the number of vehicles 
			if(i == v1.size()) // not enough drivers in the agency for initializing the operation
				return false;
			if(v1.elementAt(i).getCanDrive() && v1.elementAt(i).getIsAvailable()) {
				if(v1.elementAt(i) instanceof Detective) {
					Detective d = ((Detective)v1.elementAt(i));
					this.detectives.add(d);
					this.detectivesClone.add(d);
					d.setUnAvailable();
				}
				else {
					Investigator invest = ((Investigator)v1.elementAt(i));
					this.investigators.add(invest);
					this.investigatorsClone.add(invest);
					invest.setUnAvailable();
				}
				driver++;
			}
			i++;
		}
		return true;
	}

	public boolean addDriversLevel5(int numberOfDrivers, Vector<Agent> v1) { // add agents with driver license in addition to weapon license for each detective
		Agency.sortVector(v1);
		int driver = 0;
		int i =0;
		while(driver != numberOfDrivers) { // while the drivers amount in the operation isn't equal to the number of vehicles 
			if(i == v1.size()) // not enough drivers in the agency for initializing the operation
				return false;
			if(v1.elementAt(i).getCanDrive() && v1.elementAt(i).getIsAvailable()) {
				if(v1.elementAt(i) instanceof Detective) {
					Detective d = ((Detective)v1.elementAt(i));
					if(d.getWeaponLicense()) {
						this.detectives.add(d);
						this.detectivesClone.add(d);
						d.setUnAvailable();
						driver++;
					}
				}
				else {
					Investigator invest = ((Investigator)v1.elementAt(i));
					this.investigators.add(invest);
					this.investigatorsClone.add(invest);
					invest.setUnAvailable();
					driver++;
				}
			}
			i++;
		}
		return true;
	}

	public void addDetective(Vector<Detective> v1) { // adds detectives to the operation
		Agency.sortVector(v1);
		int i =0;
		int j=0;
		int size = this.getDetectives().size(); // the amount of detectives already added
		while (i<(this.NumberOfDetectives-size)) {
			Detective d = v1.elementAt(j);
			if(d.getIsAvailable()) {
				this.detectives.add(d);
				this.detectivesClone.add(d);
				d.setUnAvailable();
				i++;
			}
			j++;
		}
	}

	public void addDetectiveLevel5(Vector<Detective> v1) { // adds detectives to the operation only if they have in addition weapon license
		Agency.sortVector(v1);
		int i =0;
		int j=0;
		int size = this.getDetectives().size(); // the amount of detectives already added
		while (i<(this.NumberOfDetectives-size)) {
			Detective d = v1.elementAt(j);
			if(d.getIsAvailable()&&d.getWeaponLicense()) {
				this.detectives.add(d);
				this.detectivesClone.add(d);
				d.setUnAvailable();
				i++;
			}
			j++;
		}
	}

	public void addInvestigator(Vector<Investigator> v1) { // adds investigators to the operation
		Agency.sortVector(v1);
		int i =0;
		int j = 0;
		int size = this.getInvestigators().size();  // the amount of investigators already added
		while (i<(this.NumberOfInvestigators-size)) {
			Investigator invest = v1.elementAt(j);
			if(invest.getIsAvailable()) {
				this.investigators.add(invest);
				this.investigatorsClone.add(invest);
				invest.setUnAvailable();
				i++;
			}
			j++;
		}
	}

	public void addVehicle(Vector<Vehicle> c1) { // adds vehicles to the operation
		Agency.sortVector(c1);
		int numberOfAgents = this.NumberOfInvestigators + this.NumberOfDetectives;
		int vehicleCap =0;
		int i =0;
		while(!((numberOfAgents - vehicleCap )<= 0)) {
			Vehicle v1 = c1.elementAt(i);
			if(v1.getIsAvailable()) {
				vehicleCap = vehicleCap + v1.getCapacity();
				this.vehicles.add(v1);
				v1.setUnAvailable();
			}
			i++;
		}
		if(this.getVehicles().size()==1 && this.getLevel()==3) { // there is a necessity of 2 vehicles in level 3
			Vehicle v2 = c1.elementAt(i);
			if(v2.getIsAvailable()) {
				this.vehicles.add(v2);
				v2.setUnAvailable();
				i++;
			}
		}
		return;
	}

	public void calculateSalary() { // calculates every agents salary based on there terms
		for(int i=0;i<this.detectives.size();i++) {
			Detective d = this.detectives.elementAt(i) ;
			d.setSalary(this.getLevel(), d.getExperience());
		}
		for(int j=0;j<this.investigators.size();j++) {
			Investigator invest = this.investigators.elementAt(j);
			invest.setSalary(invest.getContribution(), invest.getRank());
		}
	}

	public double operationCost() { // returns the total cost of the operation 
		double totalCost = 0;
		for(int i = 0;i<this.detectives.size();i++) {
			totalCost = totalCost + this.detectives.elementAt(i).getSalaryRaise();
		}
		for(int i = 0;i<this.investigators.size();i++) {
			totalCost = totalCost + this.investigators.elementAt(i).getSalaryRaise();
		}
		totalCost = totalCost + Agency.totalExpenses(this.vehicles);
		return totalCost;
	}

	public Vehicle getMaxCar (Vector <Vehicle> v1) { // returns the most expensive car
		double maxUsage = v1.elementAt(0).usageCost;
		Vehicle maxCar = v1.elementAt(0);
		for(int i = 1; i< v1.size(); i++) {
			if(v1.elementAt(i).usageCost > maxUsage) {
				maxCar = v1.elementAt(i);
				maxUsage = v1.elementAt(i).usageCost;
				continue;
			}
		}
		return maxCar;
	}

	public void ClearInvestigators() { // clears the investigators from the operation 
		for(int i=0;i<this.getInvestigators().size();i++) {
			Investigator invest = this.getInvestigators().elementAt(i);
			invest.setIsAvailable();
		}
		this.getInvestigators().clear();
		this.getInvestigatorsClone().clear();
	}

	public void ClearDetectives() { // clears the detectives from the operation 
		for(int i=0;i<this.getDetectives().size();i++) {
			Detective d =this.getDetectives().elementAt(i);
			d.setIsAvailable();
		}
		this.getDetectives().clear();
		this.getDetectivesClone().clear();
	}

	public void ClearVehiclesAfterOp() { // clears the vehicles from the operation if the initialization of the operation wasn't successful
		for(int i=0;i<this.getVehicles().size();i++) {
			Vehicle v1 = this.getVehicles().elementAt(i);
			v1.getDetectives().clear();
			v1.getInvestigators().clear();
			v1.setIsAvailable();
		}
		this.getVehicles().clear();
	}

	public void ClearVehiclesBeforeOp() { // clears the vehicles from the operation in the end of the operation
		for(int i=0;i<this.getVehicles().size();i++) {
			Vehicle v1 = this.getVehicles().elementAt(i);
			v1.setIsAvailable();
		}
		this.getVehicles().clear();
	}

	public int compareTo(Object other){  // compares two operations by there natural comparison
		if(getLevel() > ((Operation)other).getLevel())
			return 1;
		else if(getLevel() < ((Operation)other).getLevel())
			return -1;
		else
			return 0;
	}
}