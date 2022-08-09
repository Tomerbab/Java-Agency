import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Agency {
	public Vector <Agent> agentsList = new Vector <Agent>();
	public Vector <Detective> detectivesList = new Vector <Detective>();
	public Vector <Vehicle> vehicleList = new Vector <Vehicle>();
	public Vector <Investigator> investigatorsList = new Vector <Investigator>();
	public Vector <Operation> operationList = new Vector<Operation>();

	public Agency (String agents, String vehicles) { // agency constructor
		readFile(agents);
		readFile(vehicles);
	}

	public Vector<Detective> getDetectives(){
		return this.detectivesList;
	}
	public Vector<Vehicle> getVehicles(){
		return this.vehicleList;
	}
	public Vector<Investigator> getInvestigators(){
		return this.investigatorsList;
	}
	public Vector<Agent> getAgents(){
		return this.agentsList;
	}


	private void readFile (String Name) { // converting the data from the document to an array of fields 
		BufferedReader inFile=null;
		try {
			FileReader fr = new FileReader (Name);
			inFile = new BufferedReader (fr);
			String S= inFile.readLine();
			S= inFile.readLine();
			while (S!=null)
			{
				String arr [] = S.split("\t");
				if (Name.contains("agents.txt"))
					this.addAgent(arr);
				else 
					this.addVehicle(arr);
				S= inFile.readLine();			
			}
		}
		catch (FileNotFoundException exception)
		{
			System.out.println ("The file " + Name + " was not found.");
		}
		catch (IOException exception)
		{
			System.out.println (exception);
		}
		finally{
			try{
				inFile.close();
			} catch(IOException exception){
				exception.printStackTrace();
			}
		}
	}

	private void addAgent (String arr []) { // the operation adds an agents to the agency agents list
		String Name=arr[0];
		int Id=Integer.parseInt(arr[1]);
		int Experience=Integer.parseInt(arr[3]);
		boolean CanDrive=Boolean.parseBoolean(arr[4]);
		if (arr[2].equals("Detective")) {
			boolean weaponLicense=Boolean.parseBoolean(arr[5]);
			Detective d= new Detective(Name,Id,Experience,CanDrive,weaponLicense);
			this.detectivesList.add(d);
			this.agentsList.add(d);
		}
		if (arr[2].equals("Investigator")) {
			double contribution=Double.parseDouble(arr[6]);
			int rank=Integer.parseInt(arr[7]);
			Investigator I= new Investigator(Name,Id,Experience,CanDrive,contribution,rank);
			this.investigatorsList.add(I);
			this.agentsList.add(I);
		}
	}

	private void addVehicle (String arr []) { //the operation adds a vehicle to the agency vehicles list
		int Id=Integer.parseInt(arr[0]);
		int speed=Integer.parseInt(arr[2]);
		double usageCost=Double.parseDouble(arr[3]);
		if (arr[1].equals("Car")) {
			int capacity=Integer.parseInt(arr[4]);
			int fuelCap=Integer.parseInt(arr[5]);
			Car c= new Car(Id,speed,usageCost,capacity,fuelCap);
			this.vehicleList.add(c);
		}
		else {
			Motorcycle m= new Motorcycle(Id,speed,usageCost);
			this.vehicleList.add(m);
		}
	}

	private boolean initialOperation(Operation o1) { // adding the agents and the vehicles to the operation  
		this.operationList.add(o1);
		o1.addVehicle(this.vehicleList);
		if(o1.getLevel() == 5) {
			if(!o1.addDriversLevel5(o1.getVehicles().size(),this.agentsList)){// add drivers as many as vehicles and if it doesn't meet the conditions, returns false and clears the operations
				o1.ClearDetectives();
				o1.ClearInvestigators();
				o1.ClearVehiclesBeforeOp();
				return false;
			}
			o1.addDetectiveLevel5(this.detectivesList);
		}
		else {
			if(!o1.addDrivers(o1.getVehicles().size(),this.agentsList)){ // add drivers as many as vehicles and if it doesn't meet the conditions, returns false and clears the operations
				o1.ClearDetectives();
				o1.ClearInvestigators();
				o1.ClearVehiclesBeforeOp();
				return false;
			}
			o1.addDetective(this.detectivesList);
		}
		o1.addInvestigator(this.investigatorsList);
		return true; // means operation can start
	}

	public boolean openOperation (int level, String codeName) { //opening an operation
		Operation o1 = new Operation (level,codeName);
		if(!this.initialOperation(o1)) // doesn't meet the conditions needed for the operation
			return false;
		o1.calculateSalary();
		FixInDrivers(o1);
		fixInTheRest(o1);
		return true; // the operation can start
	}

	private void FixInDrivers (Operation o ){ // inserting the drivers to the vehicles
		sortVector(o.getDetectivesClone());
		sortVector(o.getInvestigatorsClone());
		int i =0; // pointing the relevant vehicle
		int j = 0; // pointing the relevant detective
		int c = 0; // pointing the relevant investigators
		while(j<o.getDetectivesClone().size()) { // the clone vector gives us the updated number of Detectives that are still not in a vehicle
			Detective d = o.getDetectivesClone().elementAt(j);
			if(i >= o.getVehicles().size())
				break;
			if(o.getVehicles().elementAt(i).isFull()) {  // if the vehicle is full point to the next vehicle
				i++;
				continue;
			}
			if(d.getCanDrive()) {
				o.getVehicles().elementAt(i).addDetective(d);
				o.getDetectivesClone().remove(d);
				i++;
			}
			else
				j++;
		}
		while(c<o.getInvestigatorsClone().size()) { // the clone vector gives us the updated number of investigators that are still not in a vehicle
			Investigator invest = o.getInvestigatorsClone().elementAt(c);
			if(i >= o.getVehicles().size())
				break;
			if(o.getVehicles().elementAt(i).isFull()) {
				i++;
				continue;
			}
			if(invest.getCanDrive()) {
				o.getVehicles().elementAt(i).addInvestigator(invest);
				o.getInvestigatorsClone().remove(invest);
				i++;
			}
			else
				c++;
		}
	}

	private void fixInTheRest(Operation o) { // inserts the rest of the agents to the vehicles
		sortVector(o.getDetectivesClone());
		sortVector(o.getInvestigatorsClone());
		int i=0; // pointing at the relevant car
		while(0<o.getInvestigatorsClone().size()) { // the clone vector gives us the updated number of investigators that are still not in a vehicle
			if(i >= o.getVehicles().size())
				break;
			if(o.getVehicles().elementAt(i).isFull()) { // if the vehicle is full point to the next vehicle
				i++;
				continue;
			}
			Investigator invest = o.getInvestigatorsClone().elementAt(0);
			o.getVehicles().elementAt(i).addInvestigator(invest);
			o.getInvestigatorsClone().remove(invest);
		}
		while(0<o.getDetectivesClone().size()) { // the clone vector gives us the updated number of Detectives that are still not in a vehicle
			if(i >= o.getVehicles().size())
				break;
			if(o.getVehicles().elementAt(i).isFull()) { // if the vehicle is full point to the next vehicle
				i++;
				continue;
			}
			Detective d = o.getDetectivesClone().elementAt(0);
			o.getVehicles().elementAt(i).addDetective(d);
			o.getDetectivesClone().remove(d);
		}
	}

	public static  <T extends Comparable> void sortVector(Vector<T> v1) { // sorts the vector according to the natural comparison
		Boolean flag = true;
		while(flag) {
			flag = false;
			for(int i =0; i<v1.size()-1;i++) {
				if(v1.elementAt(i).compareTo(v1.elementAt(i+1)) == -1){
					flag = true;
					T temp = v1.elementAt(i);
					v1.set(i, v1.elementAt(i+1));
					v1.set(i+1, temp);
				}
			}
		}
	}

	public static <T extends Comparable> Comparable getMax (Vector <T> v1) { // finds the max element according to the natural comparison
		sortVector(v1);
		return v1.elementAt(0);
	}

	public static<T extends Experienceable> double averageExperience (Vector <T> v1){ // finds the average of the vector elements experience  
		double average = 0;
		for(int i = 0; i < v1.size(); i++) {
			average = average + v1.elementAt(i).getExperience();
		}
		return average/v1.size();
	}

	public static <T extends Costable> double totalExpenses (Vector <T> v1) { // sums all of the expenses of the operation 
		double totalExpenses = 0;
		for(int i = 0; i<v1.size(); i++) {
			totalExpenses = totalExpenses + v1.elementAt(i).getCost();
		}
		return totalExpenses;
	}

	public void endOperation (String codeName) { // operation completion process
		boolean flag = false;
		Operation o1 =null;
		for(int i=0;i<this.operationList.size();i++) {
			if(this.operationList.elementAt(i).getCodeName().equals(codeName)) { // finds the operation with the codeName that was sent
				o1 = this.operationList.elementAt(i);
				flag = true;
				break;
			}
		}
		if(flag == false) { // there isn't an open operation with the codeName that was sent
			System.out.println("The code name you have entered has no open operation.");
			return;
		}
		System.out.println("Operation Ended:" + "\n"
				+ "Code Name: " + codeName + "\n"
				+ "Operation: " + o1.getType() + "\n" 
				+ "Number of agents in the event: " + (o1.getNumOfDetectives()+ o1.getNumOfInvestigators()) + "\n"
				+ "Agents average years of experience: " + averageExperience(agentsList) + "\n" 
				+ "Operation cost: " + o1.operationCost() + "\n"
				+ "Most expensive vehicle - carId: " + o1.getMaxCar(vehicleList).getCarId());
		o1.ClearVehiclesAfterOp();
		o1.ClearDetectives();
		o1.ClearInvestigators();
	}
}
