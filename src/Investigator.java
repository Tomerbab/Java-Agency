public class Investigator extends Agent {
	private int rank;
	private double contribution;

	public Investigator (String name, int ID, int experience, boolean canDrive, double contribution, int rank) { // investigator constuctor
		this.canDrive = canDrive;
		this.name = name;
		this.experience = experience;
		if(ID < 9999) { // ID condition
			throw new IDNotValidLengthException ("your ID is "+ ID + "please enter an ID bigger than 9999."); // RunTime error
		}
		this.ID = ID;
		if(rank < 0) { // rank condition
			throw new InvestigatorRankNotVeryPositiveException ("your rank is " + rank + " ,please enter a rank bigger than 0."); // RunTime error
		}
		this.rank = rank;
		if(contribution <= 0) // contribution condition
			throw new InvestigatorContributionNotVeryPositiveException ("your contribution is"+ contribution + "please enter a contribution bigger than 0.");// RunTime error
		this.contribution = contribution;
		this.setIsAvailable();
	}

	public int getRank() {
		return this.rank;
	}
	public double getContribution() {
		return this.contribution;
	}
	
	public String toString () {
		return "name: " +name+ ", ID: "+ID+", experience: "+experience+ ", salary: "+salary+", CanDrive: " +canDrive+", contribution: " + contribution + ", rank: " +rank +"\n";
	}
}