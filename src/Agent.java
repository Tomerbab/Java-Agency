
abstract class Agent implements Comparable,Costable,Experienceable{
	protected int ID;
	protected String name;
	protected int experience;
	protected double salary;
	protected boolean canDrive;
	protected boolean isAvailable; // availability of an agent
	protected double salaryRaise; // the partial salary for the last operation
	
	public void setSalary(double a, double b) { // sets the salary according to the terms
		this.salary = salary + (a * b);
		this.salaryRaise = a*b;
	}
	public void setIsAvailable() { // sets the agent to be available
		this.isAvailable = true;
	}
	public void setUnAvailable() { // sets the agent to be unavailable
		this.isAvailable = false;
	}
	public boolean getIsAvailable() {
		return this.isAvailable;
	}
	public int getExperience() { // Experienceable method
		return this.experience;
	}
	public double getSalary() {
		return this.getCost();
	}
	public String getName() {
		return this.name;
	}
	public int getId() {
		return this.ID;
	}
	public boolean getCanDrive() {
		return this.canDrive;
	}
	public double getCost() { // Costable method
		return this.salary;
	}
	public double getSalaryRaise() { 
		return this.salaryRaise;
	}
	
	public int compareTo(Object other){ // compares two agents by there natural comparison 
		if(getExperience() > ((Agent)other).getExperience())
			return 1;
		else if(getExperience() < ((Agent)other).getExperience())
			return -1;
		else
			return 0;
	}
}